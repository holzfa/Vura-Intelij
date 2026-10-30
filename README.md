<div align="center">
<img src="src/main/resources/META-INF/pluginIcon.svg" alt="icons logo" width="128" height="128">

# icons

**A statically typed, natively compiled language with Kotlin-style syntax and native speed.**
No virtual machine. No garbage collector. What you write is what runs.

![Version](https://img.shields.io/badge/version-0.1.0-5980a6?style=flat-square)
![Status](https://img.shields.io/badge/status-work%20in%20progress-d14a08?style=flat-square)
![License](https://img.shields.io/badge/license-Unlicense-lightgrey?style=flat-square)

[Documentation](https://holzfa.github.io/Vura-Docs/#language) · [Bloom compiler](https://github.com/holzfa/Bloom)

</div>

---

## Overview

icons combines the readable syntax of a modern high-level language with the speed and predictable memory behavior of a native language. Source code is compiled to LLVM IR and from there to native machine code. There is no bytecode and no interpreter.

- **Clarity**: code reads the way it behaves
- **Simplicity**: few concepts, each with one clear job
- **Predictable**: no hidden allocations, no hidden control flow
- **Native performance**: no VM, no GC, no pauses
- **Safe by default**: low level control is explicit and only available through `unsafe` use
- **Small but expressive**: structs, classes, traits, enums and expression based control flow

> [!WARNING]
> icons is in early development. The syntax is still changing, and the compiler cannot build programs yet.

## A taste of icons

```icons
from Core.System import println

fun main() {
    println("Hello, icons!")
}
```

Variables, with inference where the value makes the type obvious:

```icons
val maxSpeed: Int = 200   // assigned once
var speed = 0             // reassignable
const MAX_PLAYERS = 4     // compile time constant
```

`if` is an expression, so there is no ternary operator:

```icons
val label = if (count == 1) "item" else "items"
```

Structs are value types, classes are reference types:

```icons
struct Point {
    var x: Int
    var y: Int

    Point(x: Int, y: Int) {
        self.x = x
        self.y = y
    }
}

class Player {
    val name: String
    private var health: Int = 100

    Player(name: String) {
        self.name = name
    }

    fun takeDamage(amount: Int) {
        health -= amount
    }
}

var a = Point(1, 2)
var b = a                  // b is a copy

val hero = Player("icons")  // lives on the heap
```

Traits and inheritance:

```icons
trait TPrintable {
    fun toText() -> String
}

class Dog : TPrintable {
    implement fun toText() -> String => "Dog"
}
```

More in [`examples/`](examples/).

## Memory model

icons has no garbage collector and no pointers.

|                           Kind                           | Lives on | Copied on assignment |    Equality    |
| -------------------------------------------------------- | -------- | -------------------- | -------------- |
| ![Struct](assets/file/dark/structVura_dark.svg) `struct` | stack    | yes                  | by value       |
| ![Class](assets/file/dark/classVura_dark.svg) `class`    | heap     | no                   | by identity    |
| ![Enum](assets/file/dark/enumVura_dark.svg) `enum`       | stack    | yes                  | by value       |

Class instances are reference counted. The compiler inserts the counting at predictable places, and the destructor runs the moment the count reaches zero. Reference cycles are broken with `weak` references.

## Status

icons is built together with its reference compiler **Bloom**, written in Kotlin.

|          Component            |       State      |
| ----------------------------- | ---------------- |
| Lexer                         | Work in progress |
| Parser                        | Work in progress |
| Type checker                  | Planned          |
| Language server               | Planned          |
| Editor Plugins(Jebrains, Zed) | Planned          |
| Code generation (LLVM)        | Planned          |
| Runtime (reference counting)  | Planned          |

## Repository layout

```
.
├── examples/     # small programs, one topic per file
├── assets/       # logo and file icons
└── README.md
```

## Building and running

Once Bloom can compile programs:

```sh
bloom build
bloom run
```

## Editor support

A plugin for JetBrains IDEs is planned. It adds syntax highlighting, semantic colors, completion, hover information, go to definition and live error reporting for `.vu` files, backed by a language server that shares its lexer, parser and type checker with the compiler.

## Contributing

Issues and discussion are welcome. The language is small on purpose: every feature has to justify its complexity.

## License

Released into the public domain under the [Unlicense](LICENSE).