@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    `kotlin-dsl`
    `groovy-gradle-plugin`
    id("com.gradle.plugin-publish") version "1.2.0"
}

group = "me.admund"
version = "0.1.0"

gradlePlugin {
    website = "https://github.com/admund/MarketSpecificUI"
    vcsUrl = "https://github.com/admund/MarketSpecificUI.git"

    plugins {
        create("MarketSpecificUiPlugin") {
            id = "me.admund.marketspecificui"
            displayName = "MarketSpecificUiPlugin"
            description = "Plugin for creating ResData for MarketSpecificUiLib"
            tags = listOf("")
            implementationClass = "me.admund.marketspecificui.plugin.MarketSpecificUiPlugin"
        }
    }
}

dependencies {
}
