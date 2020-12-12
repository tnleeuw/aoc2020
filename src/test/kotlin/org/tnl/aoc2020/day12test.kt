package org.tnl.aoc2020

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals

class Day12Test {

    @ParameterizedTest
    @CsvSource(
        "N,R90,E",
        "N,R0,N",
        "N,L90,W",
        "N,L360,N",
        "S,L90,E",
        "W,R180,E"
    )
    fun testTurn(originalDirection: Direction, turnBy: String, expectedDirection: Direction) {
        // Act
        val result = originalDirection.turn(turnBy)

        // Assert
        assertEquals(expectedDirection, result)
    }

    @ParameterizedTest
    @CsvSource(
        "E5, 5",
        "W3, -3",
        "N10, 10",
        "S7, -7"
    )
    fun testNrOfMoves(moves: String, expected: Long) {
        // Act
        val result = moves.toNrOfMoves()

        // Assert
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @CsvSource(
        "N10, E, 0, 10",
        "R90, S, 0, 0",
        "W5, E, -5, 0",
        "S7, E, 0, -7"
    )
    fun testMove(moveBy: String, expectedFacing: Direction, expectedEW: Long, expectedNS: Long) {
        // Arrange
        val ship = Ship(Direction.E, 0L, 0L)

        // Act
        val movedShip = ship.move(moveBy)

        // Assert
        assertEquals(expectedFacing, movedShip.facing)
        assertEquals(expectedEW, movedShip.ewPos)
        assertEquals(expectedNS, movedShip.nsPos)
    }

    @ParameterizedTest
    @CsvSource(
        "E, 0, 0, S, 10, 10, 10, 10",
        "W, -5, -5, S, 10, 10, 15, 15",
        "E, 10, 10, E, 5, 5, 5, 5"
    )
    fun testManhattanDistance(ship1F: Direction, ship1EW: Long, ship1NS: Long,
                              ship2F: Direction, ship2EW: Long, ship2NS: Long,
                              expectedEW: Long, expectedNS: Long) {
        // Arrange
        val ship1 = Ship(ship1F, ship1EW, ship1NS)
        val ship2 = Ship(ship2F, ship2EW, ship2NS)

        // Act
        val result = ship1.manhattanDistance(ship2)

        // Assert
        assertEquals(Pair(expectedEW, expectedNS), result)
    }

    @Test
    fun testRunMoves() {
        // Act
        val result = Day12Puzzle1.startingPoint().runMoves("day12-test.txt")

        // Assert
        assertEquals(Direction.S, result.facing)
        assertEquals(17L, result.ewPos)
        assertEquals(-8L, result.nsPos)
    }

    @Test
    fun testSumManhattanDistAfterAllMoves() {
        // Act
        val result = Day12Puzzle1.startingPoint().sumManhattanDistAfterAllMoves("day12-test.txt")

        // Assert
        assertEquals(25, result)
    }

    @Test
    fun testMoveByWayPoint() {
        // Arrange
        val ship = Day12Puzzle2.startingPoint()

        // Act
        val result = ship.moveByWayPoint("F10")

        // Assert
        assertEquals(100L, result.ewPos)
        assertEquals(10, result.nsPos)
    }

    @ParameterizedTest
    @CsvSource(
        "10, 1, N3, 10, 4",
        "10, 1, S3, 10, -2",
        "10, 1, E5, 15, 1",
        "10, 1, W5, 5, 1"
    )
    fun testMoveWayPoint(ew: Long, ns: Long, moveBy: String, expectedEW: Long, expectedNS: Long) {
        // Arrange
        val wayPoint = WayPoint(ew, ns)

        // Act
        val result = wayPoint.move(moveBy)

        // Assert
        assertEquals(expectedEW, result.ew)
        assertEquals(expectedNS, result.ns)
    }

    @ParameterizedTest
    @CsvSource(
        "10, 4, R90, 4, -10",
        "10, 4, R180, -10, -4",
        "10, 4, R270, -4, 10",
        "10, 4, R360, 10, 4",
        "10, 4, L90, -4, 10",
        "10, 4, L180, -10, -4",
        "10, 4, L270, 4, -10",
        "10, 4, L360, 10, 4",
    )
    fun testTurnWayPoint(ew: Long, ns: Long, turnBy: String, expectedEW: Long, expectedNS: Long) {
        // Arrange
        val wayPoint = WayPoint(ew, ns)

        // Act
        val result = wayPoint.turn(turnBy)

        // Assert
        assertEquals(expectedEW, result.ew)
        assertEquals(expectedNS, result.ns)

    }

    @Test
    fun testSumManhattanDistAfterAllMovesWithWayPoint() {
        // Act
        val result = Day12Puzzle2.startingPoint().sumManhattanDistAfterAllMovesWithWayPoint("day12-test.txt")

        // Assert
        assertEquals(286, result)
    }
}
