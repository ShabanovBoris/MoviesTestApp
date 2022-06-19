@file:Suppress("DSL_SCOPE_VIOLATION","UnstableApiUsage")
plugins {
    id(androidLibraryConvention)
    id(libs.plugins.kotlinKaptPlugin.get().pluginId)
    id(libs.plugins.hiltAndroidPlugin.get().pluginId)
}

dependencies {
    implementation(projects.core)
    implementation(projects.data)
    implementation(projects.domain)
    implementation(projects.uikit)
}
dependencies {
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
    //coil
    implementation(libs.coil)
//    implementation("dev.icerock.moko:fields:0.9.0")

    testImplementation(libs.junit)
}