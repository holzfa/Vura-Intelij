package icons

import com.intellij.openapi.util.IconLoader

object VuraIcons {
    @JvmField val VuraFile = IconLoader.findIcon("assets/icons/vura.svg", javaClass)
    @JvmField val VuraStruct = IconLoader.findIcon("assets/icons/structVura.svg", javaClass)
    @JvmField val VuraClass = IconLoader.findIcon("assets/icons/classVura.svg", javaClass)
    @JvmField val VuraFunction = IconLoader.findIcon("assets/icons/functionVura.svg", javaClass)
    @JvmField val VuraTrait = IconLoader.findIcon("assets/icons/traitVura.svg", javaClass)
    @JvmField val VuraEnum = IconLoader.findIcon("assets/icons/enumVura.svg", javaClass)
    @JvmField val VuraExtensions = IconLoader.findIcon("assets/icons/extensionsVura.svg", javaClass)

    @JvmField val VuraAbstractStruct = IconLoader.findIcon("assets/icons/abstractStructVura.svg", javaClass)
    @JvmField val VuraAbstractClass = IconLoader.findIcon("assets/icons/abstractClassVura.svg", javaClass)
}