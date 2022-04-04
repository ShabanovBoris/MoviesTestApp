@Deprecated("All dependencies declared in Version catalog")
object Dependencies {
     val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${VersionsKt.kotlin_version}"
     val kotlinStdLibLatest = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:latest.release"
     val androidxCorektx = "androidx.core:core-ktx:${VersionsKt.coreKtx_version}"
     val appcompat = "androidx.appcompat:appcompat:1.4.1"
     val material = "com.google.android.material:material:1.5.0"
     val constraint = "androidx.constraintlayout:constraintlayout:2.1.3"
    //navigation
     val navFragment = "androidx.navigation:navigation-fragment-ktx:${VersionsKt.navigation_version}"
     val navUi = "androidx.navigation:navigation-ui-ktx:${VersionsKt.navigation_version}"
    //coroutines
     val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${VersionsKt.coroutines_version}"
     val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${VersionsKt.coroutines_version}"
    //ktx
     val lifecycleRuntimektx = "androidx.lifecycle:lifecycle-runtime-ktx:2.4.0"
     val lifecycleViewModelktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0"
     val fragmentktx = "androidx.fragment:fragment-ktx:1.3.6"
     val activityktx = "androidx.activity:activity-ktx:1.4.0"
    //test
     val junit = "junit:junit:4.13.2"
    //reflect
     val reflect = "org.jetbrains.kotlin:kotlin-reflect:${VersionsKt.kotlin_version}"
    //github.com/square/logcat
     val logcat = "com.squareup.logcat:logcat:0.1"
    //splash
     val splashScreen = "androidx.core:core-splashscreen:1.0.0-alpha02"
    //dagger
     val dagger = "com.google.dagger:dagger:${VersionsKt.dagger_version}"
     val daggerCompiler = "com.google.dagger:dagger-compiler:${VersionsKt.dagger_version}"
    //hilt
     val hilt = "com.google.dagger:hilt-android:${VersionsKt.dagger_version}"
     val hiltCompiler = "com.google.dagger:hilt-compiler:${VersionsKt.dagger_version}"
    //room
     val room = "androidx.room:room-runtime:${VersionsKt.room_version}"
     val roomKtx = "androidx.room:room-ktx:${VersionsKt.room_version}"
     val roomCompiler = "androidx.room:room-compiler:${VersionsKt.room_version}"
    //java serialization
     val kotlinSerial = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2"
     val kotlinSerialConverter = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"
    //network
     val okHttp = "com.squareup.okhttp3:okhttp:${VersionsKt.okhttp_version}"
     val retrofit = "com.squareup.retrofit2:retrofit:${VersionsKt.retrofit_version}"
     val logInterceptor = "com.squareup.okhttp3:logging-interceptor:${VersionsKt.okhttp_version}"
    //coil
     val coil = "io.coil-kt:coil:1.4.0"
    //WorkManager
     val workManager = "androidx.work:work-runtime-ktx:2.7.0"
     val hiltWork = "androidx.hilt:hilt-work:1.0.0"
     val androidxHiltCompiler = "androidx.hilt:hilt-compiler:1.0.0"
    //Paging
     val paging = "androidx.paging:paging-runtime-ktx:3.1.0-beta01"
     val pagingRoom = "androidx.room:room-paging:${VersionsKt.room_version}"
}