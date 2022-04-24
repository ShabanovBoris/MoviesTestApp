import org.gradle.api.Project

fun Project.impl(vararg impl: Any) {
    impl.forEach {
        dependencies.add("implementation", it)
    }
}

fun Project.api(vararg impl: Any) {
    impl.forEach {
        dependencies.add("api", it)
    }
}