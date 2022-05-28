import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

allprojects {
    task("installDefaultGitHooks") {
        val hooksDevDir = Paths.get(rootProject.rootDir.absolutePath, "hooks")
        val hooksGitDir = Paths.get(rootProject.rootDir.absolutePath, ".git", "hooks")
        runCatching {
            Files.list(hooksDevDir)
                .forEach { hookPath ->
                    val hookGitPath = hooksGitDir.resolve(hookPath.fileName)
                    Files.copy(hookPath, hookGitPath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES)
                }
        }
    }
}