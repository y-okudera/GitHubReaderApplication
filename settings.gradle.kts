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

rootProject.name = "GitHub_Reader_Application"
include(":androidApp")
include(":shared")
include(":domain")
include(":data")
include(":application")

enableFeaturePreview("VERSION_CATALOGS")
include(":feature")
