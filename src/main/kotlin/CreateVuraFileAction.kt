import com.intellij.ide.actions.CreateFileFromTemplateAction
import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.NlsContexts
import com.intellij.psi.PsiDirectory
import icons.VuraIcons
import org.jetbrains.annotations.NonNls

class CreateVuraFileAction: CreateFileFromTemplateAction() {
    override fun getActionName(directory: PsiDirectory?, newName: @NonNls String, templateName: @NonNls String?): @NlsContexts.Command String? {
        return "New Vura File: $newName"
    }

    override fun buildDialog(project: Project, directory: PsiDirectory, builder: CreateFileFromTemplateDialog.Builder) {
        builder.setTitle("New Vura File")
            .addKind("Struct", VuraIcons.VuraStruct, "Struct")
            .addKind("Class", VuraIcons.VuraClass, "Class")
            .addKind("Function", VuraIcons.VuraFunction, "Function")
            .addKind("Trait", VuraIcons.VuraTrait, "Trait")
            .addKind("Enum", VuraIcons.VuraEnum, "Enum")
            .addKind("Extensions", VuraIcons.VuraExtensions, "Extensions")
    }
}