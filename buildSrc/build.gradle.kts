repositories {
    mavenCentral()
    gradlePluginPortal()
    google()
}

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
            // TODO description = ""
            // TODO tags = listOf("")
            implementationClass = "me.admund.marketspecificui.plugin.MarketSpecificUiPlugin"
        }
    }
}

dependencies {
}
