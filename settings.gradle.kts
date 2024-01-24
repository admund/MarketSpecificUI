pluginManagement {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MarketSpecificUi"
include(":marketspecificui")
include(":plugin")
include(":sample")
include(":testAndroidModule")
include(":testJavaModule")
include(":testJavaModule:testAndroidModuleInsideJavaModule")
include(":testAndroidModule:testAndroidModuleInsideAndroidModule")
