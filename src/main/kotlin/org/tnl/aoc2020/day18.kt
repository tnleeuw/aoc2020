package org.tnl.aoc2020

import java.lang.IllegalArgumentException

object Day18Puzzle1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val total = sumOfAllAnswers("day18-data.txt")

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

fun evaluateExpression(symbols: Sequence<Symbol>): Long =
    symbols.fold(Pair<Long, Symbol.Operator?>(0L, Symbol.Operator.Addition)) { acc, symbol ->
        val (value, operator) = acc
        when {
            operator == null -> {
                if (symbol !is Symbol.Operator) throw IllegalArgumentException("No current operator, but next symbol is not an operator: $symbol")
                Pair(value, symbol)
            }
            symbol is Symbol.Term.Number -> Pair(operator.apply(value, symbol.value), null)
            symbol is Symbol.Term.Expression -> Pair(operator.apply(value, evaluateExpression(symbol.symbols)), null)
            else -> throw IllegalArgumentException("Cannot evaluate, value: $value, operator: $operator, symbol: $symbol")
        }
    }.first

fun sumOfAllAnswers(fileName: String): Long =
    getDataInputStream(fileName).bufferedReader()
        .lineSequence()
        .map { line -> parseSymbols(line.iterator()) }
        .map { expression -> evaluateExpression(expression) }
        .sum()
