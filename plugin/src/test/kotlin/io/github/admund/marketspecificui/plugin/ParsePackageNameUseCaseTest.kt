package io.github.admund.marketspecificui.plugin

import io.github.admund.marketspecificui.plugin.usecases.ParsePackageNameUseCase
import org.junit.Assert.assertEquals
import org.junit.Test

class ParsePackageNameUseCaseTest {

    private val tested = ParsePackageNameUseCase()

    @Test
    fun `parse file content should return proper package name`() {
        listOf(
            "android {\n\t\"namespace = 'me.admund.terefere'\n",
            "android {\n\t\"namespace  =  'me.admund.terefere'\n",
            "android {\n\t\"namespace 'me.admund.terefere'\n",
            "android {\n\t\"namespace = \"me.admund.terefere\"\n",
            "android {\n\t\"namespace  =  \"me.admund.terefere\"\n",
            "android {\n\t\"namespace \"me.admund.terefere\"\n",
        ).forEach {
            assertEquals(tested.parseFileContent(it), "me.admund.terefere")
        }
    }
}