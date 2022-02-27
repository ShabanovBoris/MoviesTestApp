pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "MoviesDemo"

rootDir.listFiles().filter {
    it.isDirectory && !it.isHidden && it.name.first() != '.' && it.name != "buildSrc"
}.forEach {
    include(it.name)
}
//include(":app")
//include(":feature_search")
//include(":feature_main")
//include(":core")
//include(":core_data")
//include(":core_domain")
//include(":feature_detail")
//include(":tasks")
//include(":uikit")

