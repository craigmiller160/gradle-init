import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import kotlin.streams.asSequence

plugins {
    kotlin("jvm") version "1.6.20"
}

group = "io.craigmiller160"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

task("deploy") {
    val gradleHome = Paths.get(System.getProperty("user.home"), ".gradle")
    val sourceDir = Paths.get(rootProject.projectDir.absolutePath, "src")
    runCatching {
        Files.walk(sourceDir)
            .asSequence()
            .filter { sourcePath -> sourceDir.relativize(sourcePath).toString().isNotBlank() }
            .forEach { sourcePath ->
                val relativePath = sourceDir.relativize(sourcePath)
                val targetPath = gradleHome.resolve(relativePath)
                if (Files.isDirectory(sourcePath)) {
                    Files.createDirectories(targetPath)
                } else {
                    Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES)
                }
            }
    }
        .onFailure { it.printStackTrace() }
        .getOrThrow()
}