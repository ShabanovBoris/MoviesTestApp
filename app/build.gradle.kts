@Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")
plugins {
    id(androidApplicationConvention)
    id("name.remal.check-dependency-updates") version "1.5.0"
    id(libs.plugins.kotlinKaptPlugin.get().pluginId)
    id(libs.plugins.hiltAndroidPlugin.get().pluginId)
}

dependencies {
    projects.apply {
        implementation(featureMain)
        implementation(featureSearch)
        implementation(featureDetail)
        implementation(core)
        implementation(data)
        implementation(domain)
        implementation(tasks)
        implementation(uikit)
    }
//    projects{
//        implementation(this)
//    }
//    implementation(project(":uikit"))
//    add("implementation",project(":uikit"))

//    implementation(platform())
}
dependencies.constraints {

}
dependencies {
//    "demoImplementation"("com.google.firebase:firebase-ads:9.8.0")


    libs.apply {
        implementation(androidxCorektx)
        implementation(appcompat)
        implementation(material)
        implementation(constraint)
        //coroutines
        implementation(coroutinesAndroid)
        //lifecycle
        implementation(lifecycleRuntimektx)
        implementation(lifecycleViewModelktx)
        //hilt
        implementation(hilt)
        kapt(hiltCompiler)
        kapt(androidxHiltCompiler)
        //splash
        implementation(splashScreen)
        //room
        implementation(room)
        implementation(roomKtx)
        kapt(roomCompiler)
        //serialization
        implementation(kotlinSerial)
        implementation(kotlinSerialConverter)
        //network
        implementation(okHttp)
        implementation(retrofit)
        implementation(logInterceptor)
    }

    //app start up
    implementation("androidx.startup:startup-runtime:1.1.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}