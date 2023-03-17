plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.example.kmmweather.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.example.kmmweather.android"
        minSdk = 27
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(project(mapOf("path" to ":shared:data")))
    implementation(project(mapOf("path" to ":shared:domain")))

    implementation("androidx.compose.ui:ui:1.3.3")
    implementation("androidx.compose.ui:ui-tooling:1.3.3")
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.3")
    implementation("androidx.compose.foundation:foundation:1.3.1")
    implementation("androidx.compose.material:material:1.3.1")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.navigation:navigation-compose:2.6.0-alpha07")

    implementation("io.insert-koin:koin-core:3.3.3")
    implementation("io.insert-koin:koin-android:3.3.3")

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.29.2-rc")

}