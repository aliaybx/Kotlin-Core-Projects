package calculator

import java.util.*
import java.math.BigInteger

fun main() {
    val scanner = Scanner(System.`in`)
    val variables = mutableMapOf<String, BigInteger>()

    while (scanner.hasNextLine()) {
        val input = scanner.nextLine().trim()

        if (input.isEmpty()) continue

        if (input.startsWith("/")) {
            if (handleCommand(input)) break
            continue
        }

        if (input.contains("=")) {
            handleAssignment(input, variables)
        } else {
            handleExpression(input, variables)
        }
    }
}

/**
 * Handles program commands.
 * Returns true if the program should exit.
 */
fun handleCommand(input: String): Boolean {
    when (input) {
        "/exit" -> {
            println("Bye!")
            return true
        }
        "/help" -> {
            println("Smart Calculator")
            println("Supports variables and large numbers.")
            println("Available operators: +, -, *, /, ^ and parentheses ().")
            println("Multiple + and - are collapsed (e.g., ++ is +, -- is +).")
        }
        else -> println("Unknown command")
    }
    return false
}

fun isValidIdentifier(s: String): Boolean = s.matches(Regex("[a-zA-Z]+"))

/**
 * Handles variable assignment (e.g., a = 5 or b = a).
 */
fun handleAssignment(input: String, vars: MutableMap<String, BigInteger>) {
    val parts = input.split("=")
    if (parts.size != 2) {
        println("Invalid assignment")
        return
    }

    val left = parts[0].trim()
    val right = parts[1].trim()

    if (!isValidIdentifier(left)) {
        println("Invalid identifier")
        return
    }

    // Per requirement: value can be an integer or another variable.
    val value = when {
        right.matches(Regex("-?\\d+")) -> BigInteger(right)
        isValidIdentifier(right) -> {
            vars[right] ?: run {
                println("Unknown variable")
                return
            }
        }
        else -> {
            println("Invalid assignment")
            return
        }
    }

    vars[left] = value
}

/**
 * Handles expression evaluation.
 */
fun handleExpression(input: String, vars: Map<String, BigInteger>) {
    val trimmed = input.trim()

    // 1. Check for standalone invalid "words" (identifiers with digits etc.)
    if (!trimmed.contains(" ") && !trimmed.contains(Regex("[+\\-*/^()]"))) {
        if (!trimmed.matches(Regex("-?\\d+")) && !isValidIdentifier(trimmed)) {
            println("Invalid identifier")
            return
        }
    }

    // 2. Check for invalid operator sequences (e.g., **, //, etc.)
    // Note: + and - sequences are allowed and will be collapsed.
    if (trimmed.contains(Regex("[*/^]{2,}"))) {
        println("Invalid expression")
        return
    }

    // 3. Tokenize and collapse + / -
    val tokens = tokenize(trimmed) ?: run {
        println("Invalid expression")
        return
    }

    // 4. Convert Infix to Postfix (RPN)
    val postfix = infixToPostfix(tokens) ?: run {
        println("Invalid expression")
        return
    }

    // 5. Evaluate Postfix expression
    try {
        val result = evaluatePostfix(postfix, vars)
        println(result)
    } catch (e: Exception) {
        println(e.message ?: "Invalid expression")
    }
}

/**
 * Splits input into tokens and collapses consecutive + and - operators.
 */
fun tokenize(input: String): List<String>? {
    // Basic illegal character check
    if (!input.matches(Regex("[a-zA-Z0-9\\s+\\-*/^()]*"))) return null

    val regex = Regex("[a-zA-Z]+|\\d+|[+-]+|[*/^()]")
    val rawTokens = regex.findAll(input).map { it.value }.toList()

    val collapsed = mutableListOf<String>()
    var pendingOps = ""
    for (token in rawTokens) {
        if (token.matches(Regex("[+-]+"))) {
            pendingOps += token
        } else {
            if (pendingOps.isNotEmpty()) {
                val minusCount = pendingOps.count { it == '-' }
                collapsed.add(if (minusCount % 2 == 0) "+" else "-")
                pendingOps = ""
            }
            collapsed.add(token)
        }
    }
    if (pendingOps.isNotEmpty()) {
        val minusCount = pendingOps.count { it == '-' }
        collapsed.add(if (minusCount % 2 == 0) "+" else "-")
    }

    // Handle unary + and - by inserting '0' before them
    val result = mutableListOf<String>()
    for (i in collapsed.indices) {
        val curr = collapsed[i]
        if ((curr == "-" || curr == "+") && (i == 0 || collapsed[i - 1] == "(")) {
            result.add("0")
        }
        result.add(curr)
    }

    return result
}

fun getPrecedence(op: String): Int = when (op) {
    "+", "-" -> 1
    "*", "/" -> 2
    "^" -> 3
    else -> 0
}

/**
 * Converts Infix tokens to Postfix notation using Shunting-Yard algorithm.
 */
fun infixToPostfix(tokens: List<String>): List<String>? {
    val postfix = mutableListOf<String>()
    val stack = Stack<String>()
    var lastWasOperand = false

    for (token in tokens) {
        when {
            token.matches(Regex("\\d+|[a-zA-Z]+")) -> {
                if (lastWasOperand) return null // Consecutive operands
                postfix.add(token)
                lastWasOperand = true
            }
            token == "(" -> {
                if (lastWasOperand) return null // e.g., "5 (..." without operator
                stack.push(token)
                lastWasOperand = false
            }
            token == ")" -> {
                if (!lastWasOperand) return null // e.g., "(+)" or "()"
                while (stack.isNotEmpty() && stack.peek() != "(") {
                    postfix.add(stack.pop())
                }
                if (stack.isEmpty()) return null // Unbalanced
                stack.pop() // Remove "("
                lastWasOperand = true
            }
            else -> { // Binary Operator
                if (!lastWasOperand) return null // Operator without left operand
                while (stack.isNotEmpty() && stack.peek() != "(" && getPrecedence(stack.peek()) >= getPrecedence(token)) {
                    postfix.add(stack.pop())
                }
                stack.push(token)
                lastWasOperand = false
            }
        }
    }

    while (stack.isNotEmpty()) {
        val top = stack.pop()
        if (top == "(") return null // Unbalanced
        postfix.add(top)
    }

    return if (lastWasOperand) postfix else null
}

/**
 * Evaluates a Postfix (RPN) expression.
 */
fun evaluatePostfix(postfix: List<String>, vars: Map<String, BigInteger>): BigInteger {
    val stack = Stack<BigInteger>()

    for (token in postfix) {
        when {
            token.matches(Regex("\\d+")) -> {
                stack.push(BigInteger(token))
            }
            isValidIdentifier(token) -> {
                stack.push(vars[token] ?: throw Exception("Unknown variable"))
            }
            else -> {
                if (stack.size < 2) throw Exception("Invalid expression")
                val b = stack.pop()
                val a = stack.pop()
                val result = when (token) {
                    "+" -> a.add(b)
                    "-" -> a.subtract(b)
                    "*" -> a.multiply(b)
                    "/" -> {
                        if (b == BigInteger.ZERO) throw Exception("Division by zero")
                        a.divide(b)
                    }
                    "^" -> {
                        try {
                            a.pow(b.toInt())
                        } catch (e: Exception) {
                            throw Exception("Invalid expression")
                        }
                    }
                    else -> throw Exception("Invalid expression")
                }
                stack.push(result)
            }
        }
    }

    if (stack.size != 1) throw Exception("Invalid expression")
    return stack.pop()
}
