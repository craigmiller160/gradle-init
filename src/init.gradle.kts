import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import io.craigmiller160.craigbuild.gradle.plugin.CraigBuildGradlePlugin

initscript {
    val craigBuildGradlePluginVersion = "1.0.0"

    repositories {
        mavenCentral()
        maven {
            url = uri("https://repo.gradle.org/gradle/libs-releases")
        }
        maven {
            url = uri("https://nexus.craigmiller160.us/repository/maven-public")
        }
    }

    dependencies {
        classpath("io.craigmiller160:craig-build-gradle-plugin:$craigBuildGradlePluginVersion")
    }
}

allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
        maven {
            url = uri("https://nexus.craigmiller160.us/repository/maven-public")
        }
    }

    apply<CraigBuildGradlePlugin>()
}

settingsEvaluated {
    pluginManagement {
        repositories {
            mavenLocal()
            mavenCentral()
            gradlePluginPortal()
            maven {
                url = uri("https://nexus.craigmiller160.us/repository/maven-public")
            }
        }
    }
}
