package editor

enum class VuraKeyword(val keyword: String) {
    Null("null"),
    It("it"),
    Self("self"),
    Base("base"),

    Val("val"),
    Var("var"),
    Const("const"),

    Public("public"),
    Internal("internal"),
    Protected("protected"),
    Private("private"),

    Weak("weak"),
    Extern("extern"),

    Open("open"),
    Abstract("abstract"),
    Impl("impl"),

    Struct("struct"),
    Class("class"),
    Object("object"),
    Trait("trait"),
    Enum("enum"),
    Extensions("extensions"),
    Function("fun"),

    If("if"),
    Else("else"),
    Match("match"),
    While("while"),
    For("for"),
    In("in"),
    Break("break"),
    Continue("continue"),
    Return("return"),

    Module("module"),
    Import("import"),
    From("from"),

    Get("get"),
    Set("set"),
    Prop("prop"),

    Is("is"),
    True("true"),
    False("false"),
}