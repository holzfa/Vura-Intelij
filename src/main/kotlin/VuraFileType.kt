import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.NlsContexts
import com.intellij.openapi.util.NlsSafe
import icons.VuraIcons
import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.NonNls
import javax.swing.Icon

object VuraFileType: LanguageFileType(VuraLanguage.Instance) {
    override fun getName(): @NonNls String = "vura"
    override fun getDisplayName(): @Nls String = "Vura File"
    override fun getDescription(): @NlsContexts.Label String = "A Vura file."
    override fun getIcon(): Icon = VuraIcons.VuraFile // ToDo: Implement ast based icons
    override fun getDefaultExtension(): @NlsSafe String = "vu"

    internal val Instance = this
}

