import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.NlsContexts
import com.intellij.openapi.util.NlsSafe
import icons.VuraIcons
import org.jetbrains.annotations.NonNls
import javax.swing.Icon

class VuraFileType: LanguageFileType(VuraLanguage.Instance) {
    override fun getName(): @NonNls String = "Vura File"
    override fun getDefaultExtension(): @NlsSafe String = "vu"

    override fun getDescription(): @NlsContexts.Label String = """
        A Vura file.
    """.trimIndent()

    // ToDo: Implement ast based icons
    override fun getIcon(): Icon? = VuraIcons.VuraFile
}