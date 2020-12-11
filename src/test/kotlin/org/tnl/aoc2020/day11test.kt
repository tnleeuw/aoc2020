package org.tnl.aoc2020

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.math.exp
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Day11Test {

    @Test
    fun testParseGridRow() {
        // Act
        val result = parseGridRow("#.#L.L")

        // Assert
        assertEquals(6, result.size)
        assertEquals(SeatState.OCCUPIED, result[0])
        assertEquals(SeatState.FLOOR, result[1])
        assertEquals(SeatState.OCCUPIED, result[2])
        assertEquals(SeatState.EMPTY, result[3])
        assertEquals(SeatState.FLOOR, result[4])
        assertEquals(SeatState.EMPTY, result[5])
    }

    @Test
    fun testReadGrid() {
        // Act
        val result = readGrid("day11-test.txt")

        // Assert
        assertEquals(10, result.size)
        assertEquals(10, result[0].size)
    }

    @ParameterizedTest
    @CsvSource(
        "0,0,OCCUPIED",
        "-1,0,FLOOR",
        "0,-1,FLOOR",
        "11,1,FLOOR",
        "1,11,FLOOR",
        "0,1,FLOOR",
        "1,1,EMPTY"
    )
    fun testGetSeatStateAt(row: Int, col: Int, expected: SeatState) {
        // Arrange
        val grid = readGrid("day11-test6.txt")

        // Act
        val result = grid.getSeatAtPosition(row, col)

        // Assert
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @CsvSource(
        "0,0,7",
        "0,1,5",
        "1,2,6"
    )
    fun testCountFreeSurroundingSeats(row: Int, col: Int, expected: Int) {
        // Arrange
        val grid = readGrid("day11-test6.txt")

        // Act
        val result = grid.countFreeSurroundingSeats(row, col)

        // Assert
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @CsvSource(
        "0,0,1",
        "0,1,3",
        "1,2,2"
    )
    fun testCountOccupiedSurroundingSeats(row: Int, col: Int, expected: Int) {
        // Arrange
        val grid = readGrid("day11-test6.txt")

        // Act
        val result = grid.countOccupiedSurroundingSeats(row, col)

        // Assert
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @CsvSource(
        "day11-test4.txt,0,0,OCCUPIED",
        "day11-test4.txt,2,2,EMPTY",
        "day11-test4.txt,0,1,FLOOR",
        "day11-test3.txt,0,2,OCCUPIED"

    )
    fun testNewSeatState(fileName: String, row: Int, col: Int, expected: SeatState) {
        // Arrange
        val grid = readGrid(fileName)

        // Act
        val result = grid.newSeatState(row, col)

        // Assert
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @CsvSource(
        "day11-test.txt,day11-test.txt,true",
        "day11-test3.txt,day11-test3.txt,true",
        "day11-test3.txt,day11-test4.txt,false",
    )
    fun testIsSameState(fileName1: String, fileName2: String, expected: Boolean) {
        // Arrange
        val grid1 = readGrid(fileName1)
        val grid2 = readGrid(fileName2)

        // Act
        val result = grid1.isSameState(grid2)

        // Assert
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @CsvSource(
        "day11-test3.txt,20",
        "day11-test6.txt,37",
    )
    fun testCountOccupiedSeats(fileName: String, expected: Int) {
        // Arrange
        val grid = readGrid(fileName)

        // Act
        val result = grid.countOccupiedSeats()

        // Assert
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @CsvSource(
        "day11-test3.txt,day11-test4.txt",
        "day11-test.txt,day11-test2.txt",
        "day11-test2.txt,day11-test3.txt",
        "day11-test6.txt,day11-test6.txt",
    )
    fun testNextGridState(fileName: String, expectedFileName: String) {
        // Arrange
        val grid = readGrid(fileName)
        val expected = readGrid(expectedFileName)

        // Act
        val result = grid.nextGridState()

        // Assert
        assertEquals(expected, result)
        assertTrue(expected.isSameState(result))
    }

    @ParameterizedTest
    @CsvSource(
        "day11-test.txt,37,day11-test6.txt"
    )
    fun testFindStableState(fileName: String, expectedOccupiedSeats: Int, expectedFileName: String) {
        // Arrange
        val grid = readGrid(fileName)
        val expected = readGrid(expectedFileName)

        // Act
        val result = findStableState(grid)

        // Assert
        assertEquals(expected, result)
        assertTrue(expected.isSameState(result))
        assertEquals(expectedOccupiedSeats, result.countOccupiedSeats())
    }

    fun testFindAnswer() {
        // Act
        val answer = Day11Puzzle1.findAnswer("day11-test.txt")

        // Assert
        assertEquals(37, answer)
    }
}
