mapOf(
    Pair("kotlinVer", VersionsKt.kotlin_version),
    Pair("daggerVer", VersionsKt.dagger_version),
).entries.forEach {
    project.extra.set(it.key, it.value)
}