import org.gradle.api.Plugin

class InitialSubprojectsSettingsPlugin: Plugin<org.gradle.api.initialization.Settings> {
    override fun apply(target: org.gradle.api.initialization.Settings) {
        target.rootDir.listFiles().filter {
            it.isDirectory && !it.isHidden && it.name.first() != '.' && it.name != "buildSrc"
        }.forEach {
            target.include(it.name)
        }
    }
}