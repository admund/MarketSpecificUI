import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    id("com.vanniktech.maven.publish") version "0.27.0"
}

android {
    namespace = "io.github.admund.marketspecificui"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
    }
}

publishing {
    repositories {
        maven {
            name = "Repo"
            url = uri(layout.buildDirectory.dir("repo"))
        }
    }
}


mavenPublishing {
//    publishToMavenCentral(SonatypeHost.DEFAULT)
    publishToMavenCentral(SonatypeHost.S01)

    signAllPublications()

//    coordinates("io.github.admund", "marketspecificui", "0.1.0-SNAPSHOT")
    coordinates("io.github.admund", "marketspecificui", "0.1.0")

    pom {
        name.set("MarketSpecificUI")
        description.set("The library is for helping manage market-specific resources for Android apps.")
        inceptionYear.set("2024")
        url.set("https://github.com/admund/MarketSpecificUI")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("admund")
                name.set("Adrian Gardian")
                url.set("https://github.com/admund")
            }
        }
        scm {
            url.set("https://github.com/admund/MarketSpecificUI")
            connection.set("scm:git:git://github.com/admund/MarketSpecificUI.git")
            developerConnection.set("scm:git:ssh://git@github.com/admund/MarketSpecificUI.git")
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

    implementation(libs.activity.compose)
    implementation(libs.ui)
    implementation(libs.ui.graphics)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}