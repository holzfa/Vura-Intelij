import com.intellij.ide.fileTemplates.FileTemplateManager
import com.intellij.ide.wizard.AbstractNewProjectWizardStep
import com.intellij.ide.wizard.NewProjectWizardBaseData.Companion.baseData
import com.intellij.ide.wizard.NewProjectWizardStep
import com.intellij.ide.wizard.language.LanguageGeneratorNewProjectWizard
import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.dsl.builder.Panel
import com.intellij.ui.dsl.builder.bindItem
import com.intellij.ui.dsl.builder.bindText
import icons.VuraIcons
import org.jetbrains.annotations.Nls
import java.io.IOException
import java.nio.file.Path
import java.util.*
import javax.swing.Icon

class VuraNewProjectWizard: LanguageGeneratorNewProjectWizard {
    override val name: @Nls(capitalization = Nls.Capitalization.Title) String get() = VURA
    override val icon: Icon get() = VuraIcons.VuraFile
    override val ordinal: Int get() = 101 // Should be just under Kotlin

    override fun createStep(parent: NewProjectWizardStep): NewProjectWizardStep {
        return Step(parent)
    }

    class Step(parent: NewProjectWizardStep): AbstractNewProjectWizardStep(parent) {
        val binaryTypeProperty = propertyGraph.property(BinaryType.Executable)
        var binaryType by binaryTypeProperty

        val versions = listOf(
            VuraVersion(0,0,1),
            VuraVersion(0,1,0)
        )
        val langVersionProperty = propertyGraph.property(versions[0])
        var langVersion by langVersionProperty

        val authorProperty = propertyGraph.property("")
        var author by authorProperty

        val baseModuleProperty = propertyGraph.property("")
        var baseModule by baseModuleProperty


        override fun setupUI(builder: Panel) {
            builder.group {
                twoColumnsRow( {
                    label("Type: ")
                    comboBox(BinaryType.entries).bindItem(binaryTypeProperty)
                }, {
                    label("Language version: ")
                    comboBox(versions)
                        .bindItem(langVersionProperty)
                }
                )
            }

            builder.collapsibleGroup("Advanced") {
                row("Author: ") {
                    textField().bindText(authorProperty)
                }

                row("Base module: ") {
                    textField().bindText(baseModuleProperty)
                }
            }
        }


        override fun setupProject(project: Project) {
            val base = baseData ?: return
            val root = Path.of(base.path, base.name)

            WriteAction.runAndWait<IOException> {
                val root = VfsUtil.createDirectoryIfMissing(root.toString()) ?: return@runAndWait
                val src = VfsUtil.createDirectoryIfMissing(root, "src")
                VfsUtil.createDirectoryIfMissing(root, "assets")

                val manager = FileTemplateManager.getInstance(project)
                val settings = projectProperties(base.name, binaryType, langVersion, author, baseModule)

                val main = createFile("${base.name}.vu", "Main.vu", src, manager, settings)
                createFile("README.md", "Readme.md", root, manager, settings)
                createFile("${base.name.replace(' ', '_').lowercase()}.vura.toml", "Project.toml", root, manager, settings)

                FileEditorManager.getInstance(project).openFile(main, true)
            }
        }

        private fun createFile(fileName: String, templateName: String, dir: VirtualFile, manager: FileTemplateManager, settings: Properties): VirtualFile {
            val file = dir.findOrCreateChildData(this, fileName)

            val template = manager.getInternalTemplate(templateName)

            val properties = Properties().apply {
                putAll(manager.defaultProperties)
                putAll(settings)
            }

            val content = template.getText(properties)
            VfsUtil.saveText(file, content)

            return file
        }
    }
}