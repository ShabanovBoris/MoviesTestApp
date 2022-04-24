@Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")
plugins {
    id(androidLibraryConvention)
    id(libs.plugins.kotlinKaptPlugin.get().pluginId)
    id(libs.plugins.hiltAndroidPlugin.get().pluginId)
}

impl(
    projects.uikit,
    projects.tasks,
    projects.core,
    projects.data,
    projects.domain
)

impl(
    libs.androidxCorektx,
    libs.appcompat,
    libs.material,
    libs.constraint,
    libs.coroutinesAndroid,
    libs.lifecycleRuntimektx,
    libs.lifecycleViewModelktx,
    libs.hilt,
    libs.coil,
)

dependencies {
    kapt(libs.hiltCompiler)
    testImplementation(libs.junit)
}