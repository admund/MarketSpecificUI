package me.admund.marketspecificui.plugin

import groovy.util.Node
import groovy.xml.XmlParser
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.util.PatternFilterable
import org.gradle.api.file.ConfigurableFileTree
import org.gradle.api.provider.ListProperty
import java.io.File

interface MarketSpecificUiPluginExtension {
    val suffixList: ListProperty<String>
}

class MarketSpecificUiPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val extension = project.extensions.create(
            "marketspecific_extension",
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

            printClass(project = project, result = result)

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

        // TODO handle Groovy "build.gradle"
        val buildGradleFile = subProject.file("build.gradle.kts")
        if (buildGradleFile.exists()) {
            val packageName = parsePackageName(buildGradleFile)
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

    private fun parsePackageName(projectBuildGradle: File): String {
        val text = projectBuildGradle.readText()
        // TODO handle groovy version without "="
        return "namespace = \"[a-zA-Z.]*".toRegex()
            .find(text)?.value?.replace("namespace = \"", "") ?: ""
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

    private fun printClass(project: Project, result: Map<String, Map<String, String>>) {
        var resulString = //"package ${project.name}\n\n" +
            "import me.admund.marketspecificui.ResData\n\n" +
                    "val ${project.name.replace(" ", "")}ResData: ResData = mapOf(\n"
        result.entries.onEach { entry ->
            resulString += "\t${entry.key} to mapOf(\n"
            entry.value.onEach { entry2 ->
                resulString += "\t\t\"${entry2.key}\" to ${entry2.value},\n"
            }
            resulString += "\t),\n"
        }
        resulString += ")\n"
        println("\n\nResult class:\n\n$resulString")
    }
}
