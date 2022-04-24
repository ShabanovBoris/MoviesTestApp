import org.gradle.api.Plugin

class InitialSubprojectsSettingsPlugin: Plugin<org.gradle.api.initialization.Settings> {
    override fun apply(target: org.gradle.api.initialization.Settings) {
        target.rootDir.listFiles().filter {
            it.isDirectory && !it.isHidden
                    && it.listFiles()
                .any { it.name == "build.gradle.kts" } && !it.name.equals("buildSrc") && !it.name.equals("buildExt")

        }.forEach {
            target.include(it.name)
        }
    }
}