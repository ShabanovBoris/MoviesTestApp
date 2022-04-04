plugins {
    id("my-android-library")
}

dependencies {
    implementation(project(":uikit"))
    //navigation
    api(Dependencies.navFragment)
    api(Dependencies.navUi)
    //github.com/square/logcat
    api(Dependencies.logcat)

    //lifecycle
    implementation(Dependencies.lifecycleRuntimektx)
    //coroutines
    implementation(Dependencies.coroutinesAndroid)

    //for ScreenView
    implementation(Dependencies.lifecycleViewModelktx)
    implementation(Dependencies.lifecycleRuntimektx)
    implementation(Dependencies.appcompat)
    implementation(Dependencies.logcat)
    implementation(Dependencies.reflect)
    implementation(Dependencies.androidxCorektx)

    testImplementation(Dependencies.junit)
}