plugins {
    id(androidLibraryConvention)
}


dependencies {
    implementation(projects.uikit)
    //navigation
    api(libs.navFragment)
    api(libs.navUi)
    //github.com/square/logcat
    api(libs.logcat)

    //lifecycle
    implementation(libs.lifecycleRuntimektx)
    //coroutines
    implementation(libs.coroutinesAndroid)

    //for ScreenView
    implementation(libs.lifecycleViewModelktx)
    implementation(libs.lifecycleRuntimektx)
    implementation(libs.appcompat)
    implementation(libs.androidxCorektx)
    implementation(libs.reflect)

    testImplementation(libs.junit)
}