plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    with(Ktlint) {
        id(ktlint) version version
    }
    with(Moko) {
        id(kswift) version kswiftVersion
    }
    with(MultiplatformSwiftpackage) {
        id(multiplatformSwiftpackage) version version
    }
    with(Serialization) {
        id(serialization) version version
    }
}

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
            isStatic = true //or false
            baseName = "shared"
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                with(Coroutines) {
                    implementation(coroutinesCore)
                }
                with(Koin) {
                    implementation(koinShared)
                }
                with(Ktor) {
                    implementation(ktorCore)
                    implementation(ktorJson)
                    implementation(ktorLogging)
                    implementation(ktorNegotiation)
                    implementation(ktorSerialization)
                }
                with(Moko) {
                    api(mvvmCore)
                    api(mvvmFlow)
                }
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                with(Ktor) {
                    implementation(ktorOkhttp)
                }
                with(Moko) {
                    api(mvvmFlowCompose)
                }
            }
        }
        // Workaround for:
        //
        // The Kotlin source set androidAndroidTestRelease was configured but not added to any
        // Kotlin compilation. You can add a source set to a target's compilation by connecting it
        // with the compilation's default source set using 'dependsOn'.
        // See https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#connecting-source-sets
        //
        // This workaround includes `dependsOn(androidAndroidTestRelease)` in the `androidTest` sourceSet.
        val androidAndroidTestRelease by getting
        val androidTestFixtures by getting
        val androidTestFixturesDebug by getting
        val androidTestFixturesRelease by getting
        val androidTest by getting {
            dependsOn(androidAndroidTestRelease)
            dependsOn(androidTestFixtures)
            dependsOn(androidTestFixturesDebug)
            dependsOn(androidTestFixturesRelease)
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                with(Ktor) {
                    implementation(ktorDarwin)
                }
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
