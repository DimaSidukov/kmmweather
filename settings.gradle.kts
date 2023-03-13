pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "kmmweather"
include(":androidApp")
include(":shared")
include(":data")
include(":data")
include(":domain")
