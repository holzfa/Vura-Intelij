package editor

import VuraLanguage
import com.intellij.psi.tree.IElementType

enum class VuraTokenType(val element: IElementType) {
    Whitespace(IElementType("vura.whitespace", VuraLanguage.Instance)),
    Keyword(IElementType("vura.keyword", VuraLanguage.Instance)),
    Identifier(IElementType("vura.identifier", VuraLanguage.Instance)),
    StringLiteral(IElementType("vura.string", VuraLanguage.Instance)),
    NumberLiteral(IElementType("vura.number", VuraLanguage.Instance)),
    LineComment(IElementType("vura.comment.line", VuraLanguage.Instance)),
    BlockComment(IElementType("vura.comment.block", VuraLanguage.Instance)),
    DocComment(IElementType("vura.comment.doc", VuraLanguage.Instance)),

    Function(IElementType("vura.function", VuraLanguage.Instance)),
    Type(IElementType("vura.type", VuraLanguage.Instance)),

    Unknown(IElementType("vura.unknown", VuraLanguage.Instance))
}