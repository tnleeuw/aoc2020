package org.tnl.aoc2020

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals

internal class SeatNumConversionTest {

    @Test
    internal fun testRowToInt() {
        // Act
        val row = rowToInt("FBFBBFF")

        // Assert
        assertEquals(44, row)
    }

    @Test
    internal fun testColToInt() {
        // Act
        val col = colToInt("RLR")

        // Assert
        assertEquals(5, col)
    }

    @ParameterizedTest
    @CsvSource(
        "FBFBBFFRLR, 44, 5",
        "BFFFBBFRRR, 70, 7",
        "FFFBBBFRRR, 14, 7",
        "BBFFBBFRLL, 102, 4"
    )
    internal fun testSeatSpec(seatSpec: String, expectedRow: Int, expectedCol: Int) {
        // Act
        val (row, col) = seatSpecToRowCol(seatSpec)

        // Assert
        assertEquals(expectedRow, row)
        assertEquals(expectedCol, col)

    }

    @ParameterizedTest
    @CsvSource(
        "FBFBBFFRLR, 357",
        "BFFFBBFRRR, 567",
        "BFFFBBFRLR, 565",
        "FFFBBBFRRR, 119",
        "BBFFBBFRLL, 820",
        "BBFFBBFRRL, 822",
    )
    internal fun testCalculateSeatId(seatSpec: String, expectedSeatId: Int) {
        // Act
        val seatId = calculateSeatId(seatSpec)

        // Assert
        assertEquals(expectedSeatId, seatId)
    }
}

internal class Day5Puzzle1Test {

    @Test
    internal fun testFindHighestSeatId() {
        // Arrange
        val lines = fileToLines("day5p1-test.txt")

        // Act
        val highest = findHighestSeatId(lines)

        // Assert
        assertEquals(820, highest)
    }
}

internal class Day5Puzzle2Test {

    @Test
    internal fun testFindGappedSeat() {
        // Arrange
        val seatIds = boardingPassesToSeatIds(fileToLines("day5p2-test.txt"))

        // Act
        val mySeat = findGappedSeatId(seatIds)

        // Assert
        assertEquals(566, mySeat)
    }

    @Test
    internal fun testFindMySeat() {
        // Act
        val mySeat = findMySeat("day5p2-test.txt")

        // Assert
        assertEquals(566, mySeat)
    }
}
