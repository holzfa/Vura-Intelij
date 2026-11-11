package editor

import com.intellij.lexer.LexerBase
import com.intellij.psi.tree.IElementType

class VuraLexer: LexerBase() {
    private val keywords: HashSet<String> = HashSet(VuraKeyword.entries.map { it.keyword })

    private var buffer: CharSequence = ""
    private var endOffset = 0

    private var tokenStart = 0
    private var tokenEnd = 0
    private var tokenType: IElementType? = null
    private var state = 0

    override fun start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {
        this.buffer = buffer
        this.tokenStart = startOffset
        this.tokenEnd = startOffset
        this.endOffset = endOffset
        this.state = initialState

        advance()
    }

    private val hexChars = charArrayOf('a', 'b', 'c', 'd', 'e', 'f', 'A', 'B', 'C', 'D', 'E', 'F')

    override fun advance() {
        if (tokenEnd >= endOffset) {
            tokenType = null
            tokenStart = endOffset
            tokenEnd = endOffset
            return
        }

        // Start of the new token is the end of the old one
        tokenStart = tokenEnd

        if (buffer[tokenStart].isWhitespace()) {
            tokenEnd = tokenStart + 1
            tokenType = VuraTokenType.Whitespace.element
        } else if (buffer[tokenStart].isDigit()) {
            val first = buffer[tokenStart]
            var curr = tokenStart + 1

            var isFloat = false
            val isHex = curr < endOffset && first == '0' && buffer[curr] == 'x'
            val isBin = curr < endOffset && first == '0' && buffer[curr] == 'b'

            if (isHex || isBin) curr++

            while (curr < endOffset) {
                val c = buffer[curr]

                if (curr + 1 < endOffset && c == '_' && buffer[curr + 1].isDigit()) { // Handle '_' separation for big numbers
                    curr++
                } else if (c.isDigit() && !isHex && !isBin) { // Handle normal digits
                    curr++
                } else if (curr + 1 < endOffset && !isFloat && c == '.' && buffer[curr + 1].isDigit()) { // Handle the single '.' for float/double
                    isFloat = true
                    curr++
                } else if (isHex && (hexChars.contains(c) || c.isDigit())) { // Handle hexadecimal
                    curr++
                } else if (isBin && c.isDigit() && c.digitToInt() >= 0 && c.digitToInt() < 2) { // Handle binary
                    curr++
                } else {
                    break
                }
            }

            val endChar = buffer[curr]
            when (endChar) {
                'd', 'D' -> {
                    curr++
                }

                'l', 'L' -> {
                    curr++
                }
            }

            println("Hex: $isHex, Bin: $isBin, Length: ${curr - tokenStart}")

            tokenEnd = curr
            tokenType = VuraTokenType.NumberLiteral.element
        } else if (buffer[tokenStart] == '"') {
            var curr = tokenStart + 1
            while (curr < endOffset && buffer[curr] != '"') {
                curr++
            }
            tokenEnd = curr + 1

            tokenType = VuraTokenType.StringLiteral.element
        } else if (buffer[tokenStart] == '/' && buffer[tokenStart + 1] == '/') { // Lex single line comments only
            var curr = tokenStart + 2
            while (curr < endOffset && buffer[curr] != '\n') { // Keep going until a new line is hit or the file ends
                curr++
            }
            tokenEnd = curr

            tokenType = VuraTokenType.LineComment.element
        } else if (tokenStart + 1 < endOffset && buffer[tokenStart] == '/' && buffer[tokenStart + 1] == '*') {
            val isDoc = tokenStart + 2 < endOffset && buffer[tokenStart + 2] == '/'
            val offset = if (isDoc) 3 else 2

            var curr = tokenStart + offset // Ignore the starting delimiter
            while (curr + offset < endOffset && !buffer.subSequence(curr, curr + offset).contentEquals(if (isDoc) "/*/" else "*/")) {
                curr++
            }

            tokenEnd = curr + if (curr + offset < endOffset) offset else 0 // Go past the closing delimiter

            tokenType = if (isDoc) VuraTokenType.DocComment.element else VuraTokenType.BlockComment.element
        } else if (buffer[tokenStart].isLetter() || buffer[tokenStart] == '_') {
            var curr = tokenStart
            while (curr < endOffset && (buffer[curr].isLetterOrDigit() || buffer[curr] == '_')) {
                curr++
            }
            tokenEnd = curr

            tokenType = if (buffer[tokenStart].isUpperCase()) { // Supposedly a Type
                VuraTokenType.Type.element
            } else if (tokenEnd < endOffset && buffer[tokenEnd] == '(') { // A function call or declaration
                VuraTokenType.Function.element
            } else if (keywords.contains(buffer.subSequence(tokenStart, tokenEnd))) { // A keyword
                VuraTokenType.Keyword.element
            } else { // A normal identifier
                VuraTokenType.Identifier.element
            }
        } else {
            tokenEnd++
            tokenType = VuraTokenType.Unknown.element
        }
    }

    override fun getTokenType(): IElementType? = tokenType
    override fun getTokenStart(): Int = tokenStart
    override fun getTokenEnd(): Int = tokenEnd
    override fun getState(): Int = state
    override fun getBufferSequence(): CharSequence = buffer
    override fun getBufferEnd(): Int = endOffset
}