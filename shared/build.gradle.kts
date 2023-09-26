plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization")
    id("com.squareup.sqldelight")
    id("org.jetbrains.compose")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = extra["jvmTarget"] as String
        }
    }

    sourceSets {
        val ktorVersion = "2.3.4"
        val sqlDelightVersion = "1.5.5"
        val koinVersion = "3.1.4"
        val lifecycleVersion = "2.6.2"

        val commonMain by getting {
            dependencies {
                api("io.insert-koin:koin-core:$koinVersion")
                // Ktor
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-json:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

                // Serialization
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.5.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")

                // Sql Delight
                implementation("com.squareup.sqldelight:runtime:$sqlDelightVersion")
                implementation("com.squareup.sqldelight:coroutines-extensions:$sqlDelightVersion")


                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
                api("io.insert-koin:koin-android:$koinVersion")
                api("androidx.activity:activity-compose:1.7.2")
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.12.0")
                api("org.kodein.di:kodein-di-framework-compose:7.10.0")
                implementation("io.ktor:ktor-client-android:$ktorVersion")
                implementation("com.squareup.sqldelight:android-driver:$sqlDelightVersion")
            }
        }

        val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:$ktorVersion")
                implementation("com.squareup.sqldelight:native-driver:$sqlDelightVersion")
            }
        }

        val desktopMain by getting {
            dependencies {
                api("org.kodein.di:kodein-di-framework-compose:7.10.0")
                implementation(compose.desktop.common)
                implementation("io.ktor:ktor-client-cio:$ktorVersion")
                implementation("com.squareup.sqldelight:sqlite-driver:$sqlDelightVersion")
            }
        }
    }
}

android {
    namespace = "com.example.dogify"
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
dependencies {
    implementation("com.google.firebase:firebase-crashlytics-buildtools:2.9.8")
}

sqldelight {
    database("DogifyDatabase") {
        packageName = "com.example.dogify.db"
        sourceFolders = listOf("sqldelight")
    }
}
