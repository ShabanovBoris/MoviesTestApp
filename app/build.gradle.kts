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
    implementation(project(":feature_main"))
    implementation(project(":feature_search"))
    implementation(project(":feature_detail"))

    implementation(project(":tasks"))

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
    kapt(Dependencies.androidxHiltCompiler)
    //splash
    implementation(Dependencies.splashScreen)
    //room
    implementation(Dependencies.room)
    implementation(Dependencies.roomKtx)
    kapt(Dependencies.roomCompiler)
    //serialization
    implementation(Dependencies.kotlinSerial)
    implementation(Dependencies.kotlinSerialConverter)
    //network
    implementation(Dependencies.okHttp)
    implementation(Dependencies.retrofit)
    implementation(Dependencies.logInterceptor)

    //app start up
    implementation("androidx.startup:startup-runtime:1.1.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}