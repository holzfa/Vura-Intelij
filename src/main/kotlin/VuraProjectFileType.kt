import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.NlsContexts
import com.intellij.openapi.util.NlsSafe
import icons.VuraIcons
import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.NonNls
import org.toml.lang.TomlLanguage
import javax.swing.Icon


object VuraProjectFileType: LanguageFileType(TomlLanguage) {
    override fun getName(): @NonNls String = "vura.project"
    override fun getDisplayName(): @Nls String = "Vura Project File"
    override fun getDescription(): @NlsContexts.Label String = "A project configuration for a Vura project."
    override fun getIcon(): Icon = VuraIcons.VuraProject
    override fun getDefaultExtension(): @NlsSafe String = "toml"

    internal val Instance = this
}