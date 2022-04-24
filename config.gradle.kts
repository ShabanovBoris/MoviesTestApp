mapOf(
    Pair("kotlinVer", libs.versions.kotlin.version.get()),
    Pair("daggerVer", libs.versions.dagger.version.get()),
).entries.forEach {
    project.extra.set(it.key, it.value)
}