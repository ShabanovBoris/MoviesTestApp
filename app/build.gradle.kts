plugins {
    id("name.remal.check-dependency-updates") version "1.5.0"
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.bosha.moviesdemo"
        minSdk = 24
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
    buildFeatures {
        viewBinding = true
    }
}
dependencies {
    projects.apply {
        implementation(featureMain)
        implementation(featureSearch)
    }
    implementation(project(":feature_detail"))
    implementation(project(":tasks"))
    implementation(project(":core"))
    implementation(project(":core_data"))
    implementation(project(":core_domain"))
    implementation(project(":uikit"))
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
    Dependencies.apply {
        implementation(kotlinStdLib)
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