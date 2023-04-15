@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("org.jlleitschuh.gradle.ktlint")
}

val mokoMvvmVersion = "0.15.0"

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Feature Module"
        homepage = "Link to the Feature Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.0"
        framework {
            baseName = "feature"
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":shared:application"))
                implementation(project(":shared:domain"))
                implementation(libs.coroutinesCore)
                implementation(libs.koinCore)
                implementation("dev.icerock.moko:mvvm-core:$mokoMvvmVersion")
                implementation("dev.icerock.moko:mvvm-flow:$mokoMvvmVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
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
    namespace = "jp.yuoku.github_reader.feature"
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

