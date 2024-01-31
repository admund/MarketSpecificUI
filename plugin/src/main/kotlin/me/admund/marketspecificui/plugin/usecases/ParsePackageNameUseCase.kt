package me.admund.marketspecificui.plugin.usecases

import org.jetbrains.annotations.VisibleForTesting
import java.io.File

class ParsePackageNameUseCase {

    operator fun invoke(file: File): String {
        val text = file.readText()
        return parseFileContent(text)
    }

    @VisibleForTesting
    fun parseFileContent(text: String): String =
        REGEX.toRegex().find(text)?.groupValues?.get(1) ?: ""

    companion object {
        private const val REGEX = "namespace[ =\"']*([a-zA-Z.]*)"
    }
}