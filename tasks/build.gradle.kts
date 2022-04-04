plugins {
    id("my-android-library")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core_data"))
    implementation(project(":core_domain"))
    implementation(Dependencies.kotlinStdLib)
    implementation(Dependencies.androidxCorektx)
    implementation(Dependencies.appcompat)
    implementation(Dependencies.material)
    implementation(Dependencies.constraint)
    //coroutines
    implementation(Dependencies.coroutinesAndroid)
    //hilt
    implementation(Dependencies.hilt)
    kapt(Dependencies.hiltCompiler)
    //work manager
    api(Dependencies.workManager)
    api(Dependencies.hiltWork)
    kapt(Dependencies.androidxHiltCompiler)
    //coil
    implementation(Dependencies.coil)

    testImplementation(Dependencies.junit)
}