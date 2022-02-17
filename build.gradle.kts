buildscript {
    apply(from = "config.gradle.kts")
    val daggerVer: String by extra
    val kotlinVer: String by extra
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVer")
        classpath("com.google.dagger:hilt-android-gradle-plugin:$daggerVer")
    }
    repositories {
        google()
        mavenCentral()
    }
}


//plugins {
//    id 'com.android.application' version '7.1.0-rc01' apply false
//    id 'com.android.library' version '7.1.0-rc01' apply false
//    id 'org.jetbrains.kotlin.android' version '1.6.10' apply false
//}

//task clean (type: Delete) {
//    delete rootProject . buildDir
//}