import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

allprojects {
    task("installDefaultGitHooks") {
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
}