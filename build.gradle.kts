@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

import com.android.build.gradle.BaseExtension
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

buildscript {
    apply(from = "config.gradle.kts")
    val daggerVer: String by extra
    val kotlinVer: String by extra
    dependencies {
//        classpath("com.android.tools.build:gradle:7.3.0-alpha07")
//        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.20")
        classpath(libs.hiltClasspath)
        classpath("externalComposite:buildExt")
    }
//    repositories { todo moved in dependency resolution management
//        google()
//        mavenCentral()
//        gradlePluginPortal()
//    }
}

allprojects {
    plugins.withType(com.android.build.gradle.BasePlugin::class.java) {
        extensions.configure(BaseExtension::class.java) {
            compileSdkVersion(31)
            defaultConfig {
                minSdk = 24
                targetSdk = 31
            }
            buildFeatures.viewBinding = true
        }
    }
    plugins.withId("kotlin-kapt") {
        extensions.configure(KaptExtension::class.java) {
            correctErrorTypes = true
        }
    }
}

plugins {
//    id("com.android.application") version "7.3.0-alpha07" apply false
    alias(libs.plugins.androidApplicationGradlePlugin) apply false
//    id("org.jetbrains.kotlin.android") version "1.6.20" apply false
    alias(libs.plugins.androidLibraryGradlePlugon) apply false
//    kotlin("android") version "1.6.20" apply false
    alias(libs.plugins.kotlinGradlePlugon) apply false
}

tasks.register("clean", Delete::class).configure {
    group = "build"
    rootProject.buildDir.delete()
}
