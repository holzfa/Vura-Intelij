import com.intellij.openapi.application.ReadAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.isFile
import java.io.IOException

val Project.isVuraProject: Boolean get() {
    var hasConfig = false

    ReadAction.run<IOException> {
        val project = VfsUtil.createDirectoryIfMissing(this.basePath!!)!!

        for (file in VfsUtil.getChildren(project).filter{it.isFile}) {
            hasConfig = file.name.endsWith(VuraProjectFileType.defaultExtension)
            if (hasConfig) return@run
        }
    }

    return hasConfig
}