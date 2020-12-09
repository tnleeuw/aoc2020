package org.tnl.aoc2020

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day1Puzzle1Test {

    @Test
    fun calculateResultFromInputFile() {
        // Act
        val result = Day1Puzzle1.calculateResultFromInputFile("day1-test.txt")

        // Assert
        assertEquals(514579L, result)
    }

    @Test
    fun findPair1() {
        // Arrange
        val testData = listOf<Long>(1721, 979, 366, 299, 675, 1456)

        // Act
        val result = Day1Puzzle1.findPair(testData)

        // Assert
        assertEquals(1721, result.first)
        assertEquals(299, result.second)
    }

    @Test
    fun findPair2() {
        // Arrange
        val testData = listOf<Long>(979, 366, 299, 675, 1456, 1721)

        // Act
        val result = Day1Puzzle1.findPair(testData)

        // Assert
        assertEquals(299, result.first)
        assertEquals(1721, result.second)
    }
}

internal class Day1Puzzle2Test {

    @Test
    fun calculateResultFromInputFile() {
        // Act
        val result = Day1Puzzle2.calculateResultFromInputFile("day1-test.txt")

        // Assert
        assertEquals(241861950L, result)
    }

    @Test
    fun findTriplet1() {
        // Arrange
        val testData = listOf<Long>(1721, 979, 366, 299, 675, 1456)

        // Act
        val result = Day1Puzzle2.findTriplet(testData)

        // Assert
        assertEquals(979, result.first)
        assertEquals(366, result.second)
        assertEquals(675, result.third)
    }

    @Test
    fun findTriplet2() {
        // Arrange
        val testData = listOf<Long>(366, 299, 675, 1456, 979, 1721)

        // Act
        val result = Day1Puzzle2.findTriplet(testData)

        // Assert
        assertEquals(366, result.first)
        assertEquals(675, result.second)
        assertEquals(979, result.third)
    }
}
