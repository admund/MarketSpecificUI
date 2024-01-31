package me.admund.marketspecificui.plugin

import groovy.util.Node
import groovy.xml.XmlParser
import me.admund.marketspecificui.plugin.usecases.ParsePackageNameUseCase
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.ConfigurableFileTree
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import java.io.File

interface MarketSpecificUiPluginExtension {
    val suffixList: ListProperty<String>
    val resultResDataClassDestinationPath: Property<String>
}

class MarketSpecificUiPlugin : Plugin<Project> {

    private val parsePackageNameUseCase = ParsePackageNameUseCase()

    override fun apply(project: Project) {
        val extension = project.extensions.create(
            "market_specific_ui_extension",
            MarketSpecificUiPluginExtension::class.java
        )

        project.tasks.register("buildMarketSpecificUiResData") {
            println("Project name: ${project.name}\n")
            val result = mutableMapOf<String, MutableMap<String, String>>()

            project.childProjects.values.onEach { subProject ->
                parseSubProject(
                    subProject = subProject,
                    suffixList = extension.suffixList.get(),
                    result = result
                )
            }

            printClass(
                project = project,
                destinationPath = extension.resultResDataClassDestinationPath.get(),
                result = result
            )

            println("Building MarketSpecificResData was a Great Success!")
        }
    }

    private fun parseSubProject(
        subProject: Project,
        suffixList: List<String>,
        result: MutableMap<String, MutableMap<String, String>>
    ) {
        subProject.childProjects.values.onEach {
            parseSubProject(
                subProject = it,
                suffixList = suffixList,
                result = result
            )
        }

        val buildGradleFile = findBuildGradleFile(subProject)
        if (buildGradleFile == null) {
            println("Can't find gradle.build/kts in sub project: ${subProject.name}")
        } else {
            val packageName = parsePackageNameUseCase(buildGradleFile)
            if (packageName.isEmpty()) {
                println("Can't read packageName from sub project: ${subProject.name}")
            } else {
                println("Sub project name: ${subProject.name} and package name: $packageName")

                // PARSE strings.xml
                val projectSrcPath = subProject.projectDir.path + "/src"
                subProject.fileTree(projectSrcPath).matching {
                    this.include(listOf("**/*strings.xml"))
                }.onEach { file ->
                    val fileResult = parseStringsFile(
                        packageName = packageName,
                        stringsFile = file,
                        suffixList = suffixList
                    )
                    result.putAll(fileResult)
                }

                // PARSE drawable dir
                val drawableDirResult = parseDrawableDir(
                    packageName = packageName,
                    drawableDir = subProject.fileTree("src/main/res/drawable"),
                    suffixList = suffixList
                )
                result.putAll(drawableDirResult)
            }
        }
    }

    private fun findBuildGradleFile(project: Project): File? {
        var buildGradleFile = project.file("build.gradle.kts")
        if (buildGradleFile.exists().not()) {
            buildGradleFile = project.file("build.gradle")
            if (buildGradleFile.exists().not()) {
                return null
            }
        }
        return buildGradleFile
    }

    private fun parseStringsFile(
        packageName: String,
        stringsFile: File,
        suffixList: List<String>
    ): Map<String, MutableMap<String, String>> {
        val result = mutableMapOf<String, MutableMap<String, String>>()
        val node = XmlParser().parse(stringsFile)
        node.children().onEach { children ->
            if (children is Node && children.name().equals("string")) {
                val stringName = children.attributes()["name"] as String
                suffixList.onEach { suffix ->
                    if (stringName.endsWith("_$suffix")) {
                        val defaultStringName = stringName.replace("_$suffix", "")
                        val map = result["$packageName.R.string.$defaultStringName"] ?: run {
                            val newMap = mutableMapOf<String, String>()
                            result["$packageName.R.string.$defaultStringName"] = newMap
                            newMap
                        }
                        map[suffix] = "$packageName.R.string.$stringName"
                    }
                }
            }
        }
        return result
    }

    private fun parseDrawableDir(
        packageName: String,
        drawableDir: ConfigurableFileTree,
        suffixList: List<String>
    ): Map<String, MutableMap<String, String>> {
        val result = mutableMapOf<String, MutableMap<String, String>>()

        suffixList.onEach { suffix ->
            drawableDir.matching {
                this.include(listOf("*_$suffix*"))
            }.onEach { file ->
                val rawFileName = file.name.split(".").first()
                if (rawFileName.endsWith("_$suffix")) {
                    val defaultStringName = rawFileName.replace("_$suffix", "")
                    val map = result["$packageName.R.drawable.$defaultStringName"] ?: run {
                        val newMap = mutableMapOf<String, String>()
                        result["$packageName.R.drawable.$defaultStringName"] = newMap
                        newMap
                    }
                    map[suffix] = "$packageName.R.drawable.$rawFileName"
                }
            }
        }

        return result
    }

    private fun printClass(
        project: Project,
        destinationPath: String,
        result: Map<String, Map<String, String>>
    ) {
        val trimmedProjectName = project.name.trim().replace(" ", "")
        var resultString = "val ${trimmedProjectName}ResData: Map<Int, Map<String, Int>> = mapOf(\n"
        result.entries.onEach { entry ->
            resultString += "\t${entry.key} to mapOf(\n"
            entry.value.onEach { entry2 ->
                resultString += "\t\t\"${entry2.key}\" to ${entry2.value},\n"
            }
            resultString += "\t),\n"
        }
        resultString += ")\n"

        createClassFile(
            project = project,
            destinationPath = destinationPath,
            trimmedProjectName = trimmedProjectName,
            resultString = resultString
        )
    }

    private fun createClassFile(
        project: Project,
        destinationPath: String,
        trimmedProjectName: String,
        resultString: String
    ) {
        val resDataFileName = "${trimmedProjectName}ResData.kt"
        val checkedDestinationPath = destinationPath +
                if (!destinationPath.endsWith("/")) "/" else ""
        val resDataFile = project.file("$checkedDestinationPath$resDataFileName")
        if (resDataFile.exists()) {
            println("Removing $resDataFileName result: ${resDataFile.delete()}")
        }
        println("Creating $resDataFileName result: ${resDataFile.createNewFile()}")
        resDataFile.writeText(resultString)
    }
}
