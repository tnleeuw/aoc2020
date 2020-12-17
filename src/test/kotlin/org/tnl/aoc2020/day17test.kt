package org.tnl.aoc2020

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class Day17Test {

    @Test
    fun testReadInputState() {
        // Act
        val state = readInputState("day17-test.txt")

        // Assert
        assertEquals(5, state.size)
        assertTrue(state.contains(Coord(1, 0, 0)))
        assertTrue(state.contains(Coord(2, 1, 0)))
        assertTrue(state.contains(Coord(0, 2, 0)))
        assertTrue(state.contains(Coord(1, 2, 0)))
        assertTrue(state.contains(Coord(2, 2, 0)))
    }

    @Test
    fun testMinMax() {
        // Arrange
        val state = readInputState("day17-test.txt")

        // Act / Assert
        assertEquals(0, state.minX())
        assertEquals(2, state.maxX())
        assertEquals(0, state.minY())
        assertEquals(2, state.maxY())
        assertEquals(0, state.minZ())
        assertEquals(0, state.maxZ())
    }

    @Test
    fun testGetSurroundingCoordinates() {
        // Arrange
        val coord = Coord(0, 0, 0)

        // Act
        val neighbours = coord.getSurroundingCoordinates().toSet()

        // Assert
        assertEquals(26, neighbours.size)
        assertFalse(coord in neighbours)
        assertTrue(Coord(-1, -1, -1) in neighbours)
        assertTrue(Coord(1, 1, 1) in neighbours)
        assertTrue(Coord(0, 1, 1) in neighbours)
        assertTrue(Coord(0, 1, 0) in neighbours)
        assertTrue(Coord(0, 1, -1) in neighbours)
        assertTrue(Coord(0, 0, -1) in neighbours)
    }

    @ParameterizedTest
    @CsvSource(
        "0, 0, 0, 1",
        "1, 3, 0, 3",
        "2, 2, 0, 2",
        "2, 0, 0, 2",
        "0, 2, 0, 1",
        "1, 3, -1, 3",
        "1, 3, 1, 3",
        "1, 2, 1, 4",
    )
    fun testCountNeighbours(x: Int, y: Int, z: Int, expected: Int) {
        // Arrange
        val state = readInputState("day17-test.txt")
        val coord = Coord(x, y, z)

        // Act
        val result = state.countNeighbours(coord)

        // Assert
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @CsvSource(
        "0, 0, 0, false",
        "1, 3, 0, true",
        "2, 2, 0, true",
        "2, 0, 0, false",
        "0, 2, 0, false",
        "1, 3, -1, true",
        "1, 3, 1, true",
        "1, 2, 1, false",
    )
    fun testCanLive(x: Int, y: Int, z: Int, expected: Boolean) {
        // Arrange
        val state = readInputState("day17-test.txt")
        val coord = Coord(x, y, z)

        // Act
        val result = state.canLiveInNextGeneration(coord)

        // Assert
        assertEquals(expected, result)
    }

    @Test
    fun testNextGeneration() {
        // Arrange
        val state = readInputState("day17-test.txt")

        // Act
        val result = state.nextGeneration()

        // Assert
        assertEquals(11, result.size)
    }

    @Test
    fun testRunLife() {
        // Arrange
        val state = readInputState("day17-test.txt")

        // Act
        val result = runLife(state, 6)

        // Assert
        assertEquals(112, result.size)
    }
}
