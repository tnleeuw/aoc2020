package org.tnl.aoc2020

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals

class Day15Test {

    @ParameterizedTest
    @CsvSource(
        "0,3,6|436",
        "1,3,2|1",
        "2,1,3|10",
        "1,2,3|27",
        "2,3,1|78",
        "3,2,1|438",
        "3,1,2|1836",
        delimiter = '|'
    )
    fun testPlayGame(startingLine: String, expected: Int) {
        // Arrange
        val startingNumbers = lineToNumbers(startingLine)

        // Act
        val result = playGame(startingNumbers)

        // Assert
        assertEquals(expected, result)
    }
}
