package org.tnl.aoc2020

import countTreesInPath
import multiplyTreesForAllSlopes
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import readMap
import steps

internal class MapGridTest {

    @Test
    internal fun testParseGridLines() {
        // Act
        val map = readMap("day3-test.txt")

        // Assert
        assertEquals(11, map.height)
        assertEquals(11, map.width)
    }

    @ParameterizedTest
    @CsvSource(
        "0, 0, false",
        "10, 10, true",
        "0, 11, false"
    )
    internal fun testPositionChecks(line: Int, pos: Int, expected: Boolean) {
        // Arrange
        val map = readMap("day3-test.txt")

        // Act / Assert
        assertEquals(expected, map.positionHasTree(line, pos))
    }

    @ParameterizedTest
    @CsvSource(
        "0, true",
        "10, true",
        "11, false"
    )
    internal fun testIsOnMap(line: Int, expected: Boolean) {
        // Arrange
        val map = readMap("day3-test.txt")

        // Act / Assert
        assertEquals(expected, map.isLineOnMap(line))
    }
}

internal class StepsTest {

    @Test
    internal fun testSteps() {
        // Arrange
        val seq = steps().iterator()

        // Act
        val v1 = seq.next()

        // Assert
        assertEquals(0, v1.first)
        assertEquals(0, v1.second)

        // Act
        val v2 = seq.next()

        // Assert
        assertEquals(1, v2.first)
        assertEquals(3, v2.second)

        // Act
        val v3 = seq.next()

        // Assert
        assertEquals(2, v3.first)
        assertEquals(6, v3.second)
    }
}

internal class CountTreesTest {

    @Test
    internal fun testCountTreesDefault() {
        // Arrange
        val map = readMap("day3-test.txt")

        // Act
        val count = countTreesInPath(map)

        // Assert
        assertEquals(7, count)
    }

    @ParameterizedTest
    @CsvSource(
        "1, 3, 7",
        "1, 1, 2",
        "1, 5, 3",
        "1, 7, 4",
        "2, 1, 2"
    )
    internal fun testCountTrees(stepDown: Int, stepRight: Int, expected: Int) {
        // Arrange
        val map = readMap("day3-test.txt")

        // Act
        val count = countTreesInPath(map, stepDown = stepDown, stepRight = stepRight)

        // Assert
        assertEquals(expected, count)
    }

    @Test
    internal fun testMultiplySlopes() {
        // Act
        val result = multiplyTreesForAllSlopes("day3-test.txt")

        // Assert
        assertEquals(336L, result)
    }
}
