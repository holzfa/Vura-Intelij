import java.util.*

fun projectProperties(name: String, binaryType: BinaryType, vuraVersion: VuraVersion = VuraVersion(0, 0, 1), author: String, baseModule: String): Properties {
    val props = Properties()
    props["BASE"] = baseModule
    props["PROJECT_NAME"] = name
    props["AUTHOR"] = author
    props["TYPE"] = binaryType.value
    props["LANG_VERSION"] = vuraVersion.toString()

    return props
}

data class VuraVersion(val major: Int, val minor: Int, val patch: Int) {
    override fun toString(): String {
        return if (patch > 0) {
            "$major.$minor.$patch"
        } else if (minor > 0) {
            "$major.$minor"
        } else {
            "$major"
        }
    }
}

enum class BinaryType(val value: String) {
    Executable("bin"),
    Library("lib")
}
