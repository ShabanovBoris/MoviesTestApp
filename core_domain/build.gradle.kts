plugins {
    id ("org.jetbrains.kotlin.jvm")
    id ("kotlin")
    id("my-java-tasks")
}

//tasks.generateStartScript {
//    mainClass.set("myproject.MyApplication")
//}

myApp {
    mainClass.set("com.bosha.MainActivity")
}

dependencies {
    implementation(Dependencies.kotlinStdLib)
    implementation(Dependencies.coroutinesCore)
}