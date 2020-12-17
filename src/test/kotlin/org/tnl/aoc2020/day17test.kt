package org.tnl.aoc2020

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class Day17Test {

    @Test
    fun testReadInputState3D() {
        // Act
        val state = readInputState("day17-test.txt", 3)

        // Assert
        assertEquals(5, state.size)
        assertTrue(state.contains(listOf(1, 0, 0)))
        assertTrue(state.contains(listOf(2, 1, 0)))
        assertTrue(state.contains(listOf(0, 2, 0)))
        assertTrue(state.contains(listOf(1, 2, 0)))
        assertTrue(state.contains(listOf(2, 2, 0)))
    }

    @Test
    fun testReadInputState4D() {
        // Act
        val state = readInputState("day17-test.txt", 4)

        // Assert
        assertEquals(5, state.size)
        assertTrue(state.contains(listOf(1, 0, 0, 0)))
        assertTrue(state.contains(listOf(2, 1, 0, 0)))
        assertTrue(state.contains(listOf(0, 2, 0, 0)))
        assertTrue(state.contains(listOf(1, 2, 0, 0)))
        assertTrue(state.contains(listOf(2, 2, 0, 0)))
    }

    @Test
    fun testAllNeighbours3D() {
        // Arrange
        val coord = listOf(0, 0, 0)

        // Act
        val neighbours = coord.allNeighbours().toSet()

        // Assert
        assertEquals(26, neighbours.size)
        assertFalse(coord in neighbours)
        assertTrue(listOf(-1, -1, -1) in neighbours)
        assertTrue(listOf(1, 1, 1) in neighbours)
        assertTrue(listOf(0, 1, 1) in neighbours)
        assertTrue(listOf(0, 1, 0) in neighbours)
        assertTrue(listOf(0, 1, -1) in neighbours)
        assertTrue(listOf(0, 0, -1) in neighbours)
    }

    @Test
    fun testAllNeighbours4D() {
        // Arrange
        val coord = listOf(0, 0, 0, 0)

        // Act
        val neighbours = coord.allNeighbours().toSet()

        // Assert
        assertEquals(80, neighbours.size)
        assertFalse(coord in neighbours)
        assertTrue(listOf(-1, -1, -1, -1) in neighbours)
        assertTrue(listOf(1, 1, 1, 1) in neighbours)
        assertTrue(listOf(0, 1, 1, 0) in neighbours)
        assertTrue(listOf(0, 1, 0, 1) in neighbours)
        assertTrue(listOf(0, 1, -1, -1) in neighbours)
        assertTrue(listOf(0, 0, -1, -1) in neighbours)
    }

    @Test
    fun testFullGridWithEdges2D() {
        // Arrange
        val grid = setOf(listOf(0, 0))

        // Act
        val result = grid.fullGridWithEdges().toSet()

        // Arrange
        assertEquals(9, result.size)
        assertTrue(listOf(-1, -1) in result)
        assertTrue(listOf(1, 1) in result)
        assertTrue(listOf(0, 1) in result)
        assertTrue(listOf(0, -1) in result)
        assertTrue(listOf(0, 0) in result)
    }

    @Test
    fun testFullGridWithEdges3D() {
        // Arrange
        val grid = setOf(listOf(0, 0, 0))

        // Act
        val result = grid.fullGridWithEdges().toSet()

        // Arrange
        assertEquals(27, result.size)
        assertTrue(listOf(-1, -1, -1) in result)
        assertTrue(listOf(1, 1, 1) in result)
        assertTrue(listOf(0, 1, 1) in result)
        assertTrue(listOf(0, 1, 0) in result)
        assertTrue(listOf(0, 1, -1) in result)
        assertTrue(listOf(0, 0, -1) in result)
        assertTrue(listOf(0, 0, 0) in result)
    }


    @Test
    fun testFullGridWithEdges4D() {
        // Arrange
        val grid = setOf(listOf(0, 0, 0, 0))

        // Act
        val result = grid.fullGridWithEdges().toSet()

        // Arrange
        assertEquals(81, result.size)
        assertTrue(listOf(-1, -1, -1, -1) in result)
        assertTrue(listOf(1, 1, 1, 1) in result)
        assertTrue(listOf(0, 1, 1, 0) in result)
        assertTrue(listOf(0, 1, 0, -1) in result)
        assertTrue(listOf(0, 1, -1, 1) in result)
        assertTrue(listOf(0, 0, -1, -1) in result)
        assertTrue(listOf(0, 0, 0, 0) in result)
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
        val state = readInputState("day17-test.txt", 3)
        val coord = listOf(x, y, z)

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
        val state = readInputState("day17-test.txt", 3)
        val coord = listOf(x, y, z)

        // Act
        val result = state.canLiveInNextGeneration(coord)

        // Assert
        assertEquals(expected, result)
    }

    @Test
    fun testNextGeneration3D() {
        // Arrange
        val state = readInputState("day17-test.txt", 3)

        // Act
        val result = state.nextGeneration()

        // Assert
        assertEquals(11, result.size)
    }

    @Test
    fun testRunLife3D() {
        // Arrange
        val state = readInputState("day17-test.txt", 3)

        // Act
        val result = runLife(state, 6)

        // Assert
        assertEquals(112, result.size)
    }

    @Test
    fun testNextGeneration4D() {
        // Arrange
        val state = readInputState("day17-test.txt", 4)

        // Act
        val result = state.nextGeneration()

        // Assert
        assertEquals(29, result.size)
    }

    @Test
    fun testRunLife4D() {
        // Arrange
        val state = readInputState("day17-test.txt", 4)

        // Act
        val result = runLife(state, 6)

        // Assert
        assertEquals(848, result.size)
    }
}
