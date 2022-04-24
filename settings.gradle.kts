pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    includeBuild("../buildConventions")
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}
includeBuild("./buildExt")

rootProject.name = "MoviesDemo"

enableFeaturePreview("VERSION_CATALOGS")
//The project accessors are mapped from the project path. For example, if a project path is :commons:utils:some:lib then the project accessor will be projects.commons.utils.some.lib (which is the short-hand notation for projects.getCommons().getUtils().getSome().getLib()).
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootDir
    .listFiles()!!
    .filter {
        it.isDirectory &&
                !it.isHidden &&
                it.listFiles()?.any { it.name == "build.gradle.kts" } ?: false &&
                !it.name.equals("buildSrc") &&
                !it.name.equals("buildExt")
    }.map { it.name }
    .apply(::include)