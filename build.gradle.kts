allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        mavenLocal()
    }
}

buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.squareup.sqldelight:gradle-plugin:1.5.5")
    }
}

plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application") apply (false)
    id("com.android.library") apply (false)
    kotlin("android") apply (false)
    kotlin("multiplatform") apply (false)
    id("org.jetbrains.compose") apply false
    kotlin("jvm") apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
