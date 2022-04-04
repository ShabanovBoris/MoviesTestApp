pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
//        mavenCentral()
    }
//    includeBuild("../my-build-logic")
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
//    includeBuild("../my-other-project")
//    includeBuild("../my-platform")
}

rootDir
    .listFiles()
    .filter {
        it.isDirectory && !it.isHidden && !it.name.startsWith('.') && !it.name.equals("buildSrc")
    }
    .forEach {
        include(it.name)
    }

rootProject.name = "MoviesDemo"
includeBuild("../my-build-logic")

enableFeaturePreview("VERSION_CATALOGS")
//The project accessors are mapped from the project path. For example, if a project path is :commons:utils:some:lib then the project accessor will be projects.commons.utils.some.lib (which is the short-hand notation for projects.getCommons().getUtils().getSome().getLib()).
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")