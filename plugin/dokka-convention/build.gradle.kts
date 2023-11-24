plugins {
    id("convention.plugins")
}

group = "io.github.john-tuesday"
version = "0.0.0-SNAPSHOT"
version = pluginLibs.versions.dokkaConvention.get()

kotlin {
    sourceSets {
        val main by getting {
            dependencies {
                implementation(libs.dokka.gradlePlugin)
            }
        }
    }
}

gradlePlugin {
    plugins {
        val dokkaConventionPlugin by registering {
            id = pluginLibs.plugins.dokka.convention.base.get().pluginId
            displayName = "Dokka base convention plugin"
            description = "Simplify and streamline generating documentation"
            implementationClass = "io.github.john.tuesday.plugins.DokkaBaseConventionPlugin"
        }
        val dokkaHtmlMultiModuleConventionPlugin by registering {
            id = pluginLibs.plugins.dokka.convention.html.get().pluginId
            displayName = "Dokka html convention plugin"
            description = "Simplify and streamline generating multi-module html documentation"
            implementationClass = "io.github.john.tuesday.plugins.DokkaHtmlMultiModuleConventionPlugin"
        }
    }
}
