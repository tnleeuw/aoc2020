package org.tnl.aoc2020

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals

class Day9Test {

    @Test
    fun scanInvalidNumberInFile() {
        // Act
        val result = scanInvalidNumberInFile("day9-test.txt", 5)

        // Assert
        assertEquals(127, result)
    }

    @ParameterizedTest
    @CsvSource(
        "35,20,15,25,47|40|true",
        "182,150,117,102,95|127|false",
        delimiter = '|'
    )
    fun hasTargetSum(numbers: String, targetSum: Int, expected: Boolean) {
        // Arrange
        val numberList = numbers.split(",").map { it.toInt() }

        // Act
        val result = hasTargetSum(numberList, targetSum)

        // Assert
        assertEquals(expected, result)
    }
}
