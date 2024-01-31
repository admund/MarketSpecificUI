plugins {
    `kotlin-dsl`
    `groovy-gradle-plugin`
    id("com.gradle.plugin-publish") version "1.2.1"
}

group = "io.github.admund"
version = "0.2.0"

gradlePlugin {
    website = "https://github.com/admund/MarketSpecificUI"
    vcsUrl = "https://github.com/admund/MarketSpecificUI.git"

    plugins {
        create("MarketSpecificUiPlugin") {
            id = "io.github.admund.marketspecificui"
            displayName = "MarketSpecificUiPlugin"
            description = "Plugin for creating ResData for MarketSpecificUiLib"
            tags = listOf("")
            implementationClass = "io.github.admund.marketspecificui.plugin.MarketSpecificUiPlugin"
        }
    }
}

publishing {
    repositories {
        mavenLocal()
    }
}

dependencies {
    testImplementation(libs.junit)
}
