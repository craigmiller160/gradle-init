import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import io.craigmiller160.craigbuild.gradle.plugin.CraigBuildGradlePlugin

initscript {
    val craigBuildGradlePluginVersion = "1.0.0-SNAPSHOT"

    repositories {
        mavenCentral()
        maven {
            url = uri("https://repo.gradle.org/gradle/libs-releases")
        }
        maven {
            url = uri("https://craigmiller160.ddns.net:30003/repository/maven-public")
        }
    }

    dependencies {
        classpath("io.craigmiller160:craig-build-gradle-plugin:$craigBuildGradlePluginVersion")
    }
}

allprojects {
    tasks.register("installDefaultGitHooks") {
        val hooksSourceDir = Paths.get(System.getProperty("user.home"), ".gradle", "gitHooks")
        val hooksTargetDir = Paths.get(rootProject.rootDir.absolutePath, ".git", "hooks")
        runCatching {
            Files.list(hooksSourceDir)
                .forEach { hookPath ->
                    val hookGitPath = hooksTargetDir.resolve(hookPath.fileName)
                    Files.copy(hookPath, hookGitPath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES)
                }
        }
    }

    apply<CraigBuildGradlePlugin>()
}