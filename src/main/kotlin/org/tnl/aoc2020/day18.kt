package org.tnl.aoc2020

import java.util.*
import kotlin.IllegalArgumentException
import kotlin.IllegalStateException

object Day18Puzzle1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val total = sumOfAllAnswers("day18-data.txt", ::evaluateExpressionNoPrecedence)

        println("Total of all answers: ${total}")
    }

}

object Day18Puzzle2 {
    @JvmStatic
    fun main(args: Array<String>) {
        val total = sumOfAllAnswers("day18-data.txt", ::evaluateExpressionWithPrecedenceRules)

        println("Total of all answers: ${total}")
    }

}

sealed class Symbol {

    sealed class Term: Symbol() {
        class Number(val value: Long): Term()
        class Expression(val symbols: Sequence<Symbol>): Term()
    }
    sealed class Operator: Symbol() {
        abstract fun apply(n1: Long, n2: Long): Long

        object Addition: Operator() {
            override fun apply(n1: Long, n2: Long): Long = n1 + n2
        }
        object Multiplication: Operator() {
            override fun apply(n1: Long, n2: Long): Long = n1 * n2
        }
    }
}

fun parseSymbols(input: Iterator<Char>): Sequence<Symbol> {
    return sequence {
        while (input.hasNext()) {
            when(val chr = input.next()) {
                ' ' -> continue
                '+' -> yield(Symbol.Operator.Addition)
                '*' -> yield(Symbol.Operator.Multiplication)
                '(' -> yield(Symbol.Term.Expression(parseSymbols(input)))
                ')' -> return@sequence
                in '0'..'9' -> {
                    val (value, nextChar) = parseNumber(chr, input)
                    yield(value)
                    // If it's not a closing brace, it's a space and we can ignore it
                    if (nextChar == ')') return@sequence
                }
                else -> throw IllegalArgumentException("Cannot parse character '$chr'")

            }
        }
    }
}

tailrec fun parseNumber(chr: Char, input: Iterator<Char>, accumulator: String = "0"): Pair<Symbol.Term.Number, Char> = when {
    chr !in '0'..'9' -> Pair(Symbol.Term.Number(accumulator.toLong()), chr)
    !input.hasNext() -> Pair(Symbol.Term.Number((accumulator + chr).toLong()), ' ')
    else -> parseNumber(input.next(), input, accumulator + chr)
}

fun evaluateExpressionNoPrecedence(symbols: Sequence<Symbol>): Long =
    symbols.fold(Pair<Long, Symbol.Operator?>(0L, Symbol.Operator.Addition)) { acc, symbol ->
        val (value, operator) = acc
        when {
            operator == null -> {
                if (symbol !is Symbol.Operator) throw IllegalArgumentException("No current operator, but next symbol is not an operator: $symbol")
                Pair(value, symbol)
            }
            symbol is Symbol.Term.Number -> Pair(operator.apply(value, symbol.value), null)
            symbol is Symbol.Term.Expression -> Pair(operator.apply(value, evaluateExpressionNoPrecedence(symbol.symbols)), null)
            else -> throw IllegalArgumentException("Cannot evaluate, value: $value, operator: $operator, symbol: $symbol")
        }
    }.first


fun nextValue(symbols: Iterator<Symbol>): Long =
    when (val s = symbols.next()) {
        is Symbol.Operator -> throw IllegalStateException("Value term expected, got operator: ${symbols.next()}")
        is Symbol.Term.Number -> s.value
        is Symbol.Term.Expression -> evaluateExpression(s.symbols.iterator())
    }

fun evaluateExpression(symbols: Iterator<Symbol>): Long {
    val s = nextValue(symbols)
    return if (symbols.hasNext()) {
        val o = symbols.next()
        if (o !is Symbol.Operator) throw IllegalStateException("Operator expected, got $0")
        evaluateOperator(s, o, symbols)
    } else {
        s
    }
}

fun evaluateOperator(acc: Long, operator: Symbol.Operator, symbols: Iterator<Symbol>): Long {
    return when (operator) {
        Symbol.Operator.Multiplication -> {
            val operand2 = evaluateExpression(symbols)
            acc * operand2
        }
        Symbol.Operator.Addition -> {
            val operand2 = nextValue(symbols)
            if (symbols.hasNext()) {
                val nextOperator = symbols.next()
                if (nextOperator !is Symbol.Operator) throw IllegalStateException("Expected next operator, got $nextOperator")
                evaluateOperator(acc + operand2, nextOperator, symbols)
            } else {
                acc + operand2
            }
        }
    }
}

fun evaluateExpressionWithPrecedenceRules(symbols: Sequence<Symbol>): Long {
    return evaluateExpression(symbols.iterator())
}

fun sumOfAllAnswers(fileName: String, evaluate: (Sequence<Symbol>) -> Long): Long =
    getDataInputStream(fileName).bufferedReader()
        .lineSequence()
        .map { line -> parseSymbols(line.iterator()) }
        .map { expression -> evaluate(expression) }
        .sum()
