package org.tnl.aoc2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource


class Day18Test {

    @ParameterizedTest
    @CsvSource(
        "1 + 2 * 3 + 4 * 5 + 6, 71",
        "(1 + 2) + (3 + 4), 10",
        "(1 + 2) * (2 + 1), 9",
        "((2 + 2) + 1) * 2, 10",
        "((2 + 1) + 1) + 1 * 2, 10",
        "((1 + 1) + 1) + 1 + 1 * 2, 10",
        "((1 + 1) + 1) + (1 + 1) * 2, 10",
        "2 * 3 + (4 * 5), 26",
        "1 + (2 * 3) + (4 * (5 + 6)), 51",
        "5 + (8 * 3 + 9 + 3 * 4 * 3), 437",
        "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4)), 12240",
        "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2, 13632",
    )
    fun testEvaluateExpressionPuzzle1(input: String, expected: Long) {
        // Arrange
        val expression = parseSymbols(input.iterator())

        // Act
        val result = evaluateExpressionNoPrecedence(expression)

        // Assert
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @CsvSource(
        "1 + 2 * 3 + 4 * 5 + 6, 231",
        "(1 + 2) + (3 + 4), 10",
        "(1 + 2) * (2 + 1), 9",
        "((2 + 2) + 1) * 2, 10",
        "((2 + 1) + 1) + 1 * 2, 10",
        "((1 + 1) + 1) + 1 + 1 * 2, 10",
        "((1 + 1) + 1) + (1 + 1) * 2, 10",
        "2 * 3 + (4 * 5), 46",
        "1 + (2 * 3) + (4 * (5 + 6)), 51",
        "5 + (8 * 3 + 9 + 3 * 4 * 3), 1445",
        "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4)), 669060",
        "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2, 23340",
    )
    fun testEvaluateExpressionPuzzle2(input: String, expected: Long) {
        // Arrange
        val expression = parseSymbols(input.iterator())

        // Act
        val result = evaluateExpressionWithPrecedenceRules(expression)

        // Assert
        assertEquals(expected, result)
    }

    @Test
    fun testSumAllAnswersPuzzle1() {
        // Act
        val total = sumOfAllAnswers("day18-test.txt", ::evaluateExpressionNoPrecedence)

        // Assert
        assertEquals(26457L, total)
    }

    @Test
    fun testSumAllAnswersPuzzle2() {
        // Act
        val total = sumOfAllAnswers("day18-test.txt", ::evaluateExpressionWithPrecedenceRules)

        // Assert
        assertEquals(694173L, total)
    }
}
