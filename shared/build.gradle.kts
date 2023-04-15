@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("org.jlleitschuh.gradle.ktlint")
    id("com.chromaticnoise.multiplatform-swiftpackage")
    id("dev.icerock.moko.kswift")
    alias(libs.plugins.serialization)
}

val mokoMvvmVersion = "0.15.0"

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.0"
        framework {
            baseName = "shared"
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.coroutinesCore)
                implementation(libs.koinCore)
                implementation(libs.ktorCore)
                implementation(libs.ktorNegotiation)
                implementation(libs.ktorJson)
                implementation(libs.ktorLogging)
                implementation(libs.ktorSerialization)
                api("dev.icerock.moko:mvvm-core:$mokoMvvmVersion")
                api("dev.icerock.moko:mvvm-flow:$mokoMvvmVersion")
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
                api("dev.icerock.moko:mvvm-flow-compose:$mokoMvvmVersion")
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
            packageName("Shared")
            swiftToolsVersion("5.7")
            targetPlatforms {
                iOS { v("14") }
            }
        }
    }
}

android {
    namespace = "jp.yuoku.github_reader"
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

kswift {
    install(dev.icerock.moko.kswift.plugin.feature.PlatformExtensionFunctionsFeature)
    install(dev.icerock.moko.kswift.plugin.feature.SealedToSwiftEnumFeature)
}

kotlin.targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java) {
    binaries.all {
        binaryOptions["memoryModel"] = "experimental"
        binaryOptions["freezing"] = "disabled"
    }
}
