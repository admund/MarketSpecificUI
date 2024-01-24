// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
    id("me.admund.marketspecificui") version ("0.1.2")
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
}

configure<me.admund.marketspecificui.plugin.MarketSpecificUiPluginExtension> {
    suffixList.set(listOf("us", "it", "mx", "pl", "tr"))
}

true // Needed to make the Suppress annotation work for the plugins block