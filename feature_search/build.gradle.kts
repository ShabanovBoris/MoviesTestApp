plugins {
    id("my-android-library")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core_data"))
    implementation(project(":core_domain"))
    implementation(project(":uikit"))
}
dependencies {
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
    //coil
    implementation(Dependencies.coil)

    testImplementation(Dependencies.junit)
}