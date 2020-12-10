package org.tnl.aoc2020

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals
import kotlin.test.assertTrue

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
        assertEquals(listOf<Long>(1, 3, 1, 3), result)
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

    @Test
    fun testConsecutive1JoltageDifferences() {
        // Arrange
        val joltages = readAllJoltages("day10-test1.txt")
        val differences = calculateDifferences(joltages)

        println("Differences: $differences")
        // Act
        val result = calculateConsecutive1JoltageDifferences(differences)

        // Assert
        assertEquals(3, result)

    }

    @Test
    fun testConsecutive1JoltageDifferencesInBigFile() {
        // Arrange
        val joltages = readAllJoltages("day10-test2.txt")
        val differences = calculateDifferences(joltages)

        println("Differences: $differences")
        // Act
        val result = calculateConsecutive1JoltageDifferences(differences)

        // Assert
        assertEquals(15, result)

    }

    @ParameterizedTest
    @CsvSource(
        "day10-test1.txt|1,3,2,1",
        "day10-test2.txt|4,4,3,2,4,1,4",
//        "day10-data.txt|0",
        delimiter = '|'
    )
    fun testFindConsecutive1Ranges(fileName: String, expectedStr: String) {
        // Arrange
        val joltages = readAllJoltages(fileName)
        val differences = calculateDifferences(joltages)

        println("Differences: $differences")

        val expected = expectedStr.split(",")
            .map { it.toInt() }

        // Act
        val result = findConsecutiveRangesOf1s(differences)
        println("Result: $result")

        // Assert
        assertEquals(expected, result)
    }

    @Test
    fun testGroupRanges() {
        // Arrange
        val joltages = readAllJoltages("day10-test2.txt")
        val differences = calculateDifferences(joltages)
        val ranges = findConsecutiveRangesOf1s(differences)

        println("Differences: $differences")
        println("Ranges: $ranges")
        // Act
        val result = groupRanges(ranges)
        println("Map of groupped ranges: $result")

        assertTrue(result.containsKey(4))
        assertEquals(4, result[4])
        assertTrue(result.containsKey(3))
        assertEquals(1, result[3])
        assertTrue(result.containsKey(2))
        assertEquals(1, result[2])
    }


    @ParameterizedTest
    @CsvSource(
        "day10-test1.txt|8",
        "day10-test2.txt|19208",
//        "day10-data.txt|0",
        delimiter = '|'
    )
    fun testCalculateTotalCombinations(fileName: String, expected: Long) {
        // Arrange
        val joltages = readAllJoltages(fileName)
        val differences = calculateDifferences(joltages)
        val ranges = findConsecutiveRangesOf1s(differences)
        val groupedRanges = groupRanges(ranges)

        println("Differences: $differences")
        println("Ranges: $ranges")

        // Act
        val result = calculateTotalPossibleCombinations(groupedRanges)
        println("Result: $result")

        // Assert
        assertEquals(expected, result)
    }


    @ParameterizedTest
    @CsvSource(
        "day10-test1.txt|8",
        "day10-test2.txt|19208",
//        "day10-data.txt|0",
        delimiter = '|'
    )
    fun testCalculateTotalCombinationsFromFile(fileName: String, expected: Long) {
        // Act
        val result = calculatePossibleCombinationsFromFile(fileName)

        // Assert
        assertEquals(expected, result)
    }
}
