import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.nio.file.Files
import java.nio.file.Paths

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
    val source = Paths.get(rootProject.projectDir.absolutePath, "src")
    Files.walk(source)
}