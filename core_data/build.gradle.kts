import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.serialization") version VersionsKt.kotlin_version
}


// Reading the apikey from file
val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))
android {
    compileSdk = 31

    defaultConfig {
        minSdk = 24
        targetSdk = 31

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        // Put the apikey and base url in BuildConfig
        buildConfigField("String", "TMDB_APIKEY", keystoreProperties.getProperty("TMDB_APIKEY"))
        buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core_domain"))
    implementation(Dependencies.kotlinStdLib)
    implementation(Dependencies.androidxCorektx)
    implementation(Dependencies.appcompat)
    implementation(Dependencies.material)
    implementation(Dependencies.constraint)
    //coroutines
    implementation(Dependencies.coroutinesAndroid)
    //lifecycle
    implementation(Dependencies.lifecycleRuntimektx)
    implementation(Dependencies.lifecycleViewModelktx)
    //hilt
    implementation(Dependencies.hilt)
    kapt(Dependencies.hiltCompiler)
    //room
    implementation(Dependencies.room)
    implementation(Dependencies.roomKtx)
    kapt(Dependencies.roomCompiler)
    //serialization
    implementation(Dependencies.kotlinSerial)
    implementation(Dependencies.kotlinSerialConverter)
    //network
    implementation(Dependencies.okHttp)
    implementation(Dependencies.retrofit)
    implementation(Dependencies.logInterceptor)
    //paging
    testImplementation(Dependencies.junit)

    api(Dependencies.paging)
    api(Dependencies.pagingRoom)
}