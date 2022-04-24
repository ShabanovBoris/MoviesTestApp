@Suppress("DSL_SCOPE_VIOLATION","UnstableApiUsage")

plugins {
    id(androidLibraryConvention)
    id(libs.plugins.kotlinKaptPlugin.get().pluginId)
    id(libs.plugins.hiltAndroidPlugin.get().pluginId)
}

dependencies {
    implementation(projects.core)
    implementation(projects.data)
    implementation(projects.domain)

    implementation(libs.androidxCorektx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraint)
    //coroutines
    implementation(libs.coroutinesAndroid)
    //hilt
    implementation(libs.hilt)
    kapt(libs.hiltCompiler)
    //work manager
    api(libs.workManager)
    api(libs.hiltWork)
    kapt(libs.androidxHiltCompiler)
    //coil
    implementation(libs.coil)

    testImplementation(libs.junit)
}