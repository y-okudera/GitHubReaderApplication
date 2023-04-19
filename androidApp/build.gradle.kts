@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.android.application")
    kotlin("android")
    with(Ksp) {
        id(ksp) version version
    }
    with(Ktlint) {
        id(ktlint) version version
    }
}

android {
    namespace = "jp.yuoku.github_reader.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "jp.yuoku.github_reader.android"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.1"
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
    applicationVariants.all {
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
        }
    }
}

dependencies {
    implementation(project(":shared"))
    with(Accompanist) {
        implementation(coil)
        implementation(webview)
    }
    with(Compose) {
        implementation(composeActivity) {
            because("We are not using  xml its better to use compose activity ")
        }
        implementation(composeFoundation) {
            because("Supports compose ")
        }
        implementation(composeMaterial) {
            because("Supports compose ")
        }
        debugImplementation(composeToolingDebug) {
            because("Supports previews and other tooling stuff." )
        }
        implementation(composeToolingPreview) {
            because("Supports preview of composables")
        }
        implementation(composeUI) {
            because("Supports compose ")
        }
        implementation(util) {
        }
    }
    with(ComposeDestination) {
        implementation(composeDestination)
        ksp(composeDestinationPlugin)
    }
    with(Koin) {
        implementation(koin)
        implementation(koinAndroid)
    }
    with(Material3) {
        implementation(material3)
        implementation(window)
    }
}
