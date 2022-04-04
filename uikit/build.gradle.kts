plugins {
    id("my-android-library")
}

dependencies {
    //navigation
    api(Dependencies.navFragment)
    api(Dependencies.navUi)
    //github.com/square/logcat
    api(Dependencies.logcat)
}