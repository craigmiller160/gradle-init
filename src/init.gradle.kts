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
            url = uri("https://nexus-craigmiller160.ddns.net/repository/maven-public")
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
            url = uri("https://nexus-craigmiller160.ddns.net/repository/maven-public")
        }
    }

    tasks.register("installDefaultGitHooks") {
        println("Installing Default Git Hooks")
        val hooksSourceDir = Paths.get(System.getProperty("user.home"), ".gradle", "gitHooks")
        val hooksTargetDir = Paths.get(rootProject.rootDir.absolutePath, ".git", "hooks")
        runCatching {
            Files.list(hooksSourceDir)
                .forEach { hookPath ->
                    val hookGitPath = hooksTargetDir.resolve(hookPath.fileName)
                    Files.copy(hookPath, hookGitPath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES)
                }
        }
            .onFailure { it.printStackTrace() }
    }

    apply<CraigBuildGradlePlugin>()
}