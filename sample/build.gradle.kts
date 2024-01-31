plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "me.admund.marketspecificui.sample"
    compileSdk = 34

    defaultConfig {
        applicationId = "me.admund.marketspecificui.sample"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)

    implementation(project(":marketspecificui"))
    implementation(project(":testAndroidModule"))
    implementation(project(":testAndroidModule:testAndroidModuleInsideAndroidModule"))
    implementation(project(":testJavaModule"))
    implementation(project(":testJavaModule:testAndroidModuleInsideJavaModule"))

    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}

// TODO think to which task will be best to append it
project.tasks["build"].dependsOn(":buildMarketSpecificUiResData")
