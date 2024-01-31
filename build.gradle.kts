// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
    id("io.github.admund.marketspecificui") version ("0.2.0")
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
}

configure<io.github.admund.marketspecificui.plugin.MarketSpecificUiPluginExtension> {
    suffixList.set(listOf("us", "it", "mx", "pl", "tr"))
    resultResDataClassDestinationPath.set("sample/src/main/java")
}

true // Needed to make the Suppress annotation work for the plugins block