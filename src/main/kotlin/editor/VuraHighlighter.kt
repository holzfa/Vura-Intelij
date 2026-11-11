package editor

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType

class VuraHighlighter: SyntaxHighlighterBase() {
    override fun getHighlightingLexer(): Lexer {
        return VuraLexer()
    }

    override fun getTokenHighlights(tokenType: IElementType?): Array<out TextAttributesKey?> = when (tokenType) {
        VuraTokenType.Keyword.element -> arrayOf(
            TextAttributesKey.createTextAttributesKey("Vura_Keyword", DefaultLanguageHighlighterColors.KEYWORD)
        )

        VuraTokenType.StringLiteral.element -> arrayOf(
            TextAttributesKey.createTextAttributesKey("Vura_String", DefaultLanguageHighlighterColors.STRING)
        )
        VuraTokenType.NumberLiteral.element -> arrayOf(
            TextAttributesKey.createTextAttributesKey("Vura_Number", DefaultLanguageHighlighterColors.NUMBER),
        )

        VuraTokenType.LineComment.element -> arrayOf(
            TextAttributesKey.createTextAttributesKey("Vura_Line_Comment", DefaultLanguageHighlighterColors.LINE_COMMENT)
        )
        VuraTokenType.BlockComment.element -> arrayOf(
            TextAttributesKey.createTextAttributesKey("Vura_Block_Comment", DefaultLanguageHighlighterColors.BLOCK_COMMENT)
        )
        VuraTokenType.DocComment.element -> arrayOf(
            TextAttributesKey.createTextAttributesKey("Vura_Doc_Comment", DefaultLanguageHighlighterColors.DOC_COMMENT)
        )

        VuraTokenType.Function.element -> arrayOf(
            TextAttributesKey.createTextAttributesKey("Vura_Function", DefaultLanguageHighlighterColors.FUNCTION_DECLARATION)
        )
        VuraTokenType.Type.element -> arrayOf(
            TextAttributesKey.createTextAttributesKey("Vura_Type", DefaultLanguageHighlighterColors.CLASS_NAME)
        )

        else -> arrayOf()
    }
}