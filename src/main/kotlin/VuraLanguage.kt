import com.intellij.lang.Language

class VuraLanguage private constructor(): Language("io.holzfa.intellij.vura.lang") {
    companion object {
        val Instance: VuraLanguage = VuraLanguage()
    }
}