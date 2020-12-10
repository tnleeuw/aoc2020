package org.tnl.aoc2020

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals

class Day10Test {

    @ParameterizedTest
    @CsvSource(
        "0, 1, true",
        "0, 2, true",
        "0, 3, true",
        "0, 4, false",
        "1, 4, true"
    )
    fun testAdapterFits(availableJoltage: Joltage, adapterJoltage: Joltage, expected: Boolean) {
        // Act
        val result = adapterFits(availableJoltage, adapterJoltage)

        // Assert
        assertEquals(expected, result)
    }

    @Test
    fun testFindNextFittingAdapter() {
        // Arrange
        val availableAdapters = listOf<Joltage>(3, 1, 10)

        // Act
        val (newAvailable, used) = findNextFittingAdapter(availableAdapters, listOf())

        // Assert
        assertEquals(listOf<Joltage>(1), used)
        assertEquals(listOf<Joltage>(3, 10), newAvailable)
    }

    @Test
    fun testCalculateDeviceJoltage() {
        // Arrange
        val adapters = listOf<Joltage>(3, 10, 1)

        // Act
        val result = calculateDeviceJoltage(adapters)

        // Assert
        assertEquals(13, result)
    }

    @Test
    fun testCalculateDifferences() {
        // Arrange
        val  adapters = listOf<Joltage>(0, 5, 4, 1, 8)

        // Act
        val result = calculateDifferences(adapters)

        // Assert
        assertEquals(listOf<Joltage>(1, 3, 1, 3), result)
    }

    @ParameterizedTest
    @CsvSource(
        "day10-test1.txt, 35",
        "day10-test2.txt, 220",
    )
    fun testCalulateDifferenceDistribution(fileName: String, expected: Int) {
        // Arrange
        val joltages = readAllJoltages(fileName)
        val differences = calculateDifferences(joltages)

        // Act
        val result = calculateDifferenceDistribution(differences)

        // Assert
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @CsvSource(
        "day10-test1.txt, 35",
        "day10-test2.txt, 220",
    )
    fun testPuzzle1Answer(fileName: String, expected: Int) {
        // Act
        val result = Day10Puzzle1.calculateAnswer(fileName)

        // Assert
        assertEquals(expected, result)
    }
}
