plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 24
        targetSdk = 31

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
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