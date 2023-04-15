pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
        id("com.chromaticnoise.multiplatform-swiftpackage") version "2.0.3"
        id("dev.icerock.moko.kswift") version "0.6.1"
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "GitHub_Reader_Application"
include(":androidApp")
include(":shared")
include(":shared:application")
include(":shared:data")
include(":shared:domain")
include(":shared:feature")

enableFeaturePreview("VERSION_CATALOGS")
