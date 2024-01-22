package me.admund.marketspecifcui.plugin

import groovy.util.Node
import groovy.xml.XmlParser
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

class MarketSpecificUiPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.task("buildResData") {
            // TODO add as params
            val suffixList = listOf("us", "it", "mx", "pl")

            println("Project name: ${project.name}")
            val result = mutableMapOf<String, MutableMap<String, String>>()

            project.childProjects.values.onEach { subProject ->
                parseSubProject(
                    subProject = subProject,
                    suffixList = suffixList,
                    result = result
                )
            }

            printClass(project = project, result = result)

            println("DONE!!!")
        }
    }

    private fun parseSubProject(
        subProject: Project,
        suffixList: List<String>,
        result: MutableMap<String, MutableMap<String, String>>
    ) {
        println("\nSub project name: ${subProject.name}")
        // TODO handle Groovy "build.gradle"
        val buildGradleFile = subProject.file("build.gradle.kts")
        if (buildGradleFile.exists()) {
            val packageName = parsePackageName(buildGradleFile)
            println("Sub project package name: $packageName")

            // PARSE strings.xml
            subProject.fileTree(subProject.projectDir).matching {
                include("**/*strings.xml")
            }.onEach { file ->
                val fileResult = parseStringsFile(
                    packageName = packageName,
                    stringsFile = file,
                    suffixList = suffixList
                )
                result.putAll(fileResult)
            }
        }
    }

    private fun parsePackageName(projectBuildGradle: File): String {
        val text = projectBuildGradle.readText()
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
                    if (stringName.contains("_$suffix")) {
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

    private fun printClass(project: Project, result: Map<String, Map<String, String>>) {
        var resulString = //"package ${project.name}\n\n" +
            "import me.admund.marketspecificui.ResData\n\n" +
                    "val ${project.name}ResData: ResData = mapOf(\n"
        result.entries.onEach { entry ->
            resulString += "\t${entry.key} to mapOf(\n"
            entry.value.onEach { entry2 ->
                resulString += "\t\t\"${entry2.key}\" to ${entry2.value},\n"
            }
            resulString += "\t),\n"
        }
        resulString += ")\n\n"
        println("Result class:\n\n$resulString")
    }
}
