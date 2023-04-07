plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("com.squareup.sqldelight")
    id("org.jetbrains.kotlin.plugin.serialization")
}

kotlin {
    android()
    ios()

    val ktorVersion = "2.2.1"
    val sqlDelightVersion = "1.5.5"

    sourceSets["commonMain"].dependencies {
        implementation(project(mapOf("path" to ":shared:domain")))

        // Ktor
        implementation("io.ktor:ktor-client-core:$ktorVersion")
        implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
        implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

        // Settings
        implementation("com.russhwolf:multiplatform-settings-no-arg:1.0.0")

        // SqlDelight
        implementation("com.squareup.sqldelight:runtime:$sqlDelightVersion")
    }

    sourceSets["androidMain"].dependencies {
        // SqlDelight
        implementation("com.squareup.sqldelight:android-driver:$sqlDelightVersion")
    }

    sourceSets["iosMain"].dependencies {
        implementation("com.squareup.sqldelight:native-driver:$sqlDelightVersion")
    }
}

android {
    namespace = "com.example.data"
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

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.core:core-ktx:1.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

sqldelight {
    database("AppDatabase") {
        packageName = "com.example.weather.cache"
        sourceFolders = listOf("sqldelight")
    }
}