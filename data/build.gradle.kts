import java.io.FileInputStream
import java.util.*

@Suppress("DSL_SCOPE_VIOLATION","UnstableApiUsage")
plugins {
    id(androidLibraryConvention)
    id(libs.plugins.kotlinKaptPlugin.get().pluginId)
    id(libs.plugins.hiltAndroidPlugin.get().pluginId)
    alias(libs.plugins.kotlinSerializationPlugin)
}

// Reading the apikey from file
val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))
android {
    defaultConfig {
        // Put the apikey and base url in BuildConfig
        buildConfigField("String", "TMDB_APIKEY", keystoreProperties.getProperty("TMDB_APIKEY"))
        buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
    }
}

dependencies {
    implementation(projects.core)
    implementation(projects.domain)
    implementation(libs.androidxCorektx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraint)
    //coroutines
    implementation(libs.coroutinesAndroid)
    //lifecycle
    implementation(libs.lifecycleRuntimektx)
    implementation(libs.lifecycleViewModelktx)
    //hilt
    implementation(libs.hilt)
    kapt(libs.hiltCompiler)
    //room
    implementation(libs.room)
    implementation(libs.roomKtx)
    kapt(libs.roomCompiler)
    //serialization
    implementation(libs.kotlinSerial)
    implementation(libs.kotlinSerialConverter)
    //network
    implementation(libs.okHttp)
    implementation(libs.retrofit)
    implementation(libs.logInterceptor)
    //paging
    testImplementation(libs.junit)

    api(libs.paging)
    api(libs.pagingRoom)
}