plugins {
    id("com.android.library")
    id("com.squareup.sqldelight")
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.8.10"
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            export(project(":shared:domain"))
            export(project(":shared:data"))
        }
    }

    val koinVersion = "3.2.0"
    val ktorVersion = "2.2.1"
    val sqlDelightVersion = "1.5.5"

    sourceSets {
        val commonMain by getting {
            dependencies {

                api(project(":shared:domain"))
                api(project(":shared:data"))

                // Koin
                implementation("io.insert-koin:koin-core:$koinVersion")

                // Ktor
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

                // Settings
                implementation("com.russhwolf:multiplatform-settings-no-arg:1.0.0")

                // SqlDelight
                implementation("com.squareup.sqldelight:runtime:$sqlDelightVersion")

                // KotlinX datetime
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
            }
        }
        val commonTest by getting {
            dependencies {
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.insert-koin:koin-android:$koinVersion")
                implementation("io.ktor:ktor-client-android:$ktorVersion")
                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
                implementation("com.squareup.sqldelight:android-driver:$sqlDelightVersion")
            }
        }
        val androidUnitTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation("io.ktor:ktor-client-darwin:$ktorVersion")
                implementation("com.squareup.sqldelight:native-driver:$sqlDelightVersion")
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.example.kmmweather"
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    compileSdk = 33
    defaultConfig {
        minSdk = 27
        targetSdk = 33
    }
}

dependencies {
    implementation(project(":shared:domain"))
    implementation(project(":shared:data"))
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
}

sqldelight {
    database("AppDatabase") {
        packageName = "com.example.weather.shared.local"
    }
}