@Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")
plugins {
    id(androidLibraryConvention)
    id(libs.plugins.kotlinKaptPlugin.get().pluginId)
    id(libs.plugins.hiltAndroidPlugin.get().pluginId)
}
impl(
    projects.uikit,
    projects.core,
    projects.data,
    projects.domain
)

impl(
    libs.coil,
    libs.kotlinStdLib,
    libs.androidxCorektx,
    libs.appcompat,
    libs.material,
    libs.constraint,
    //coroutines
    libs.coroutinesAndroid,
    //lifecycle
    libs.lifecycleRuntimektx,
    libs.lifecycleViewModelktx,
    //hilt
    libs.hilt
)

dependencies {
    kapt(libs.hiltCompiler)

    testImplementation(libs.junit)
}