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
    fun hasTargetSum(numbers: String, targetSum: Long, expected: Boolean) {
        // Arrange
        val numberList = numbers.split(",").map { it.toLong() }

        // Act
        val result = hasTargetSum(numberList, targetSum)

        // Assert
        assertEquals(expected, result)
    }

    @Test
    fun findRangeThatSumsTo() {
        // Arrange
        val numbers = getNumbersFromStream(getDataInputStream("day9-test.txt"))

        // Act
        val range = findRangeThatSumsTo(numbers, 127)

        // Assert
        assertEquals(4, range.size)
        assertEquals(15, range[0])
        assertEquals(25, range[1])
        assertEquals(47, range[2])
        assertEquals(40, range[3])
    }

    @Test
    fun findEncryptionWeaknessInList() {
        // Arrange
        val numbers = getNumbersFromStream(getDataInputStream("day9-test.txt"))

        // Act
        val result = findEncryptionWeakness(numbers, 5)

        // Assert
        assertEquals(62, result)
    }

    @Test
    fun findEncryptionWeaknessInFile() {
        // Act
        val result = findEncryptionWeaknessInFile("day9-test.txt", 5)

        // Assert
        assertEquals(62, result)
    }
}
