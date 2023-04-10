@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    alias(libs.plugins.ktlint)
    alias(libs.plugins.serialization)
    alias(libs.plugins.multiplatformSwiftpackage)
}

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Data Module"
        version = "1.0"
        ios.deploymentTarget = "14.0"
        framework {
            baseName = "data"
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":domain"))
                implementation(libs.coroutinesCore)
                implementation(libs.koinCore)
                implementation(libs.ktorNegotiation)
                implementation(libs.ktorJson)
                implementation(libs.ktorLogging)
                implementation(libs.ktorSerialization)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.ktorOkhttp)
            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(libs.ktorDarwin)
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
        multiplatformSwiftPackage {
            packageName("Data")
            swiftToolsVersion("5.7")
            targetPlatforms {
                iOS { v("14") }
            }
        }
    }
}

android {
    namespace = "jp.yuoku.github_reader.data"
    compileSdk = 32
    defaultConfig {
        minSdk = 26
        targetSdk = 32
    }
}

ktlint {
    verbose.set(true)
    outputToConsole.set(true)
}
