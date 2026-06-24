# Smart Calculator

![Kotlin](https://img.shields.io/badge/Kotlin-2.0-blueviolet?logo=kotlin)
![Console](https://img.shields.io/badge/Platform-Console-lightgrey)
![Difficulty](https://img.shields.io/badge/Difficulty-Intermediate-yellow)
![Status](https://img.shields.io/badge/Status-Completed-brightgreen)

A feature-rich command-line calculator built entirely in Kotlin that supports **variable assignment**, **big integer arithmetic**, **operator precedence**, and **parenthesized expressions**. This project demonstrates core computer science concepts including infix-to-postfix conversion (Shunting-Yard algorithm), postfix evaluation, tokenization, and input validation.

---

## Overview

The Smart Calculator is a REPL (Read-Eval-Print-Loop) application that processes mathematical expressions entered by the user. It goes beyond basic calculators by supporting:

- Arbitrary-precision integer arithmetic using `BigInteger`
- Variable storage and retrieval
- Consecutive `+`/`-` operator collapsing (e.g., `--` becomes `+`)
- Unary plus/minus handling
- Full parenthesized expression evaluation
- Exponentiation with `^`

---

## Features

- **Variable Assignment** — Store values as named variables (`a = 5`, `b = a + 3`)
- **Big Integer Support** — Handles arbitrarily large numbers via `java.math.BigInteger`
- **Operator Precedence** — Correctly evaluates `+`, `-`, `*`, `/`, `^` with proper precedence
- **Parentheses** — Supports nested parentheses for complex expressions
- **Operator Collapsing** — Converts `++`, `--`, `+-`, `-+` into the correct single operator
- **Unary Handling** — Automatically inserts `0` for unary plus/minus (e.g., `-5` → `0 - 5`)
- **Input Validation** — Detects and reports invalid identifiers, expressions, and unknown variables
- **Interactive Commands** — `/help` and `/exit` for user guidance

---

## Technical Concepts Used

| Concept | Implementation |
|---|---|
| **Shunting-Yard Algorithm** | Converts infix expression to Reverse Polish Notation (postfix) |
| **Postfix Evaluation** | Evaluates RPN using a stack |
| **Tokenization** | Regex-based lexical analysis with operator collapsing |
| **BigInteger Arithmetic** | Arbitrary-precision integer math |
| **Recursive Descent / Stack-Based Parsing** | Operator precedence parsing |
| **Input Validation** | Regex matching, identifier checking, sequence validation |
| **`Map` Data Structure** | Variable storage and lookup |

---

## Challenges Solved

1. **Operator Collapsing** — Handling arbitrary sequences of `+` and `-` (e.g., `5 +--+ 3`) required carefully counting minus signs modulo 2 to collapse correctly.

2. **Unary Operator Detection** — Distinguishing unary minus from binary minus (e.g., `-5` vs `5 - 3`). Solved by checking if `-` appears at the start or after `(`, then inserting `0` as the left operand.

3. **Consecutive Operand Prevention** — The parser must reject invalid expressions like `5 3` while accepting `5 + 3`. Tracked via a `lastWasOperand` flag in the infix-to-postfix converter.

4. **Exponentiation with `BigInteger`** — The `^` operator uses `BigInteger.pow()`, which requires an `Int` exponent. Converting safely and catching overflow.

5. **Robust Error Handling** — Every invalid input path (unknown variables, division by zero, unbalanced parentheses, invalid identifiers) returns a clear error message.

---

## What I Learned

- Deepened understanding of **infix, postfix, and prefix notation**
- Implemented **Dijkstra's Shunting-Yard algorithm** from scratch
- Gained proficiency with **Kotlin's `BigInteger` API**
- Practiced **defensive programming** — validating every input before evaluation
- Learned to handle **edge cases in expression parsing** (unary operators, operator sequences, whitespace handling)

---

## How to Run

### Prerequisites
- [Java JDK 11+](https://adoptium.net/)
- [Kotlin Compiler](https://kotlinlang.org/docs/command-line.html)

### Compile and Run

```bash
# Compile
kotlinc calculator.kt -include-runtime -d calculator.jar

# Run
java -jar calculator.jar
```

### Usage Example

```
> a = 10
> b = 5
> a + b * 2
20
> (a + b) * 2
30
> 2 ^ 10
1024
> /help
Smart Calculator
Supports variables and large numbers.
Available operators: +, -, *, /, ^ and parentheses ().
Multiple + and - are collapsed (e.g., ++ is +, -- is +).
> /exit
Bye!
```

---

## Future Improvements

- [ ] Add floating-point arithmetic support
- [ ] Implement trigonometric and mathematical functions (`sin`, `cos`, `sqrt`)
- [ ] Add a GUI or web interface
- [ ] Support multi-character function names
- [ ] Add expression history with `↑`/`↓` arrow keys
- [ ] Implement unit tests with JUnit

---

## Author

**Ali Aybx** — Junior Kotlin Developer  
This project was built as part of a portfolio to demonstrate proficiency in Kotlin, algorithms, and data structures.
[GitHub](https://github.com/aliaybx) • [LinkedIn](https://www.linkedin.com/in/ali-ayoub-233632369/)
