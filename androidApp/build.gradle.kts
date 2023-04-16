@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.android.application")
    kotlin("android")
    alias(libs.plugins.ktlint)
    id("com.google.devtools.ksp") version "1.7.10-1.0.6"
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
    implementation("androidx.compose.ui:ui:1.2.1")
    implementation("androidx.compose.ui:ui-tooling:1.2.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.2.1")
    implementation("androidx.compose.foundation:foundation:1.2.1")
    implementation("androidx.compose.material:material:1.2.1")
    implementation("androidx.activity:activity-compose:1.5.1")
    implementation(libs.koinAndroid)
    implementation(libs.koinCore)

    with(Accompanist) {
        implementation(coil)
        implementation(webview)
    }
    with(Compose) {
        implementation(util){

        }
        implementation(composeActivity) {
            because("We are not using  xml its better to use compose activity ")
        }


        implementation(composeToolingDebug){
            because("Supports preview of composables")
        }

        debugImplementation(composeToolingDebug) {

            because("Supports previews and other tooling stuff." )
        }
        implementation(composeUI) {
            because("Supports compose ")
        }
    }
    with(ComposeDestination) {

        implementation(composeDestination)
        ksp(composeDestinationPlugin)
    }
    with(Material3) {
        implementation(material3)
        implementation(window)
    }
    with(Koin){
        implementation(koinAndroid)
    }
}
