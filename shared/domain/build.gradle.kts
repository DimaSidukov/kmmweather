plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

kotlin {
    android()
    ios()
}

android {
    namespace = "com.example.domain"
    compileSdk = 33

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
}