import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose") version "1.2.0"
    kotlin("plugin.serialization") version "1.7.20"
}

group = "dev.baseio.slackclone"
version = "1.0"

val ktor_version = "2.1.0"

object Jvm {
    val target = JavaVersion.VERSION_1_8
}

object Versions {
    const val koin = "3.1.4"
}


object Deps {

    object Kotlinx {
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4"
        const val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:0.4.0"

        object JVM {
            const val coroutinesSwing = "org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.6.4"
        }

        object IOS {
            const val coroutinesX64 = "org.jetbrains.kotlinx:kotlinx-coroutines-core-iosx64:1.6.4"
            const val coroutinesArm64 = "org.jetbrains.kotlinx:kotlinx-coroutines-core-iosarm64:1.6.4"
        }

        object Android {
            const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4"
        }
    }

    object SqlDelight {
        const val androidDriver = "com.squareup.sqldelight:android-driver:1.5.3"
        const val jvmDriver = "com.squareup.sqldelight:sqlite-driver:1.5.3"
        const val nativeDriver = "com.squareup.sqldelight:native-driver:1.5.3"
        const val core = "com.squareup.sqldelight:runtime:1.5.3"
    }


    object Koin {
        const val core = "io.insert-koin:koin-core:${Versions.koin}"
        const val core_jvm = "io.insert-koin:koin-core-jvm:${Versions.koin}"
        const val test = "io.insert-koin:koin-test:${Versions.koin}"
        const val android = "io.insert-koin:koin-android:${Versions.koin}"
    }

    object AndroidX {
        const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1"
    }


}

repositories {
    mavenCentral()
    mavenLocal()
}
val slackDataVersion: String by project


kotlin {
    android()
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    iosArm64()
    iosSimulatorArm64()
    iosX64()
    sourceSets {

        val commonMain by getting {
            dependencies {
                implementation("dev.baseio.slackdatalib:slack_multiplatform_client_data_lib:1.0")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                implementation("com.squareup.sqldelight:runtime:1.5.3")
                implementation(Deps.Koin.core)
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0")
                implementation(Deps.Kotlinx.datetime)
                implementation(Deps.SqlDelight.core)
                implementation(Deps.Kotlinx.coroutines)
                implementation(Deps.Koin.core)
                implementation(kotlin("stdlib-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(Deps.Koin.test)
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("dev.baseio.slackdatalib:slack_multiplatform_client_data_lib-android:1.0")
                implementation(Deps.Koin.android)
                implementation(Deps.Kotlinx.coroutines)
                implementation(Deps.AndroidX.lifecycleViewModelKtx)
                implementation("dev.baseio.slackdatalib:slack-multiplatform-generate-protos:1.0.0")
                implementation("androidx.security:security-crypto-ktx:1.1.0-alpha03")
                implementation("com.google.accompanist:accompanist-systemuicontroller:0.26.3-beta")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
                implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
                implementation("io.coil-kt:coil-compose:2.2.0")
                implementation("io.ktor:ktor-client-android:$ktor_version")
                api("androidx.constraintlayout:constraintlayout-compose:1.0.1")
                api("androidx.appcompat:appcompat:1.5.1")
                api("androidx.core:core-ktx:1.9.0")
            }
        }
        val iosArm64Main by getting {
            dependencies {
                implementation("dev.baseio.slackdatalib:slack_multiplatform_client_data_lib-iosarm64:1.0")
                implementation("io.ktor:ktor-client-darwin:$ktor_version")
            }
        }
        val iosSimulatorArm64Main by getting {
            dependencies {
                implementation("dev.baseio.slackdatalib:slack_multiplatform_client_data_lib-iossimulatorarm64:1.0")
                implementation("io.ktor:ktor-client-darwin:$ktor_version")
            }
        }
        val iosX64Main by getting {
            dependencies {
                implementation("dev.baseio.slackdatalib:slack_multiplatform_client_data_lib-iosx64:1.0")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-iosx64:1.6.4")
                implementation("io.ktor:ktor-client-darwin:$ktor_version")
            }
        }


        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("dev.baseio.slackdatalib:slack_multiplatform_client_data_lib-jvm:1.0")
                implementation(Deps.Kotlinx.coroutines)
                implementation("dev.baseio.slackdatalib:slack-multiplatform-generate-protos:1.0.0")
                implementation(Deps.Kotlinx.JVM.coroutinesSwing)
                implementation("io.ktor:ktor-client-java:$ktor_version")
                implementation("com.alialbaali.kamel:kamel-image:0.4.0")
                api(compose.preview)
                implementation(Deps.Koin.core_jvm)
            }
        }
    }
}
kotlin {
    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        binaries.all {
            // TODO: the current compose binary surprises LLVM, so disable checks for now.
            freeCompilerArgs += "-Xdisable-phases=VerifyBitcode"
        }
    }
}


android {
    compileSdk = 33
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
    packagingOptions {
        resources.excludes.add("google/protobuf/*.proto")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

