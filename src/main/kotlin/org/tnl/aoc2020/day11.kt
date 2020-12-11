package org.tnl.aoc2020

import kotlin.streams.toList

object Day11Puzzle1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val answer = findAnswer("day11-data.txt")
        println("Nr of occupied seats in stable state: $answer")
    }

    fun findAnswer(fileName: String): Int {
        val grid = readGrid(fileName)
        val result = findStableState(grid, false, 4)
        return result.countOccupiedSeats()
    }
}


object Day11Puzzle2 {
    @JvmStatic
    fun main(args: Array<String>) {
        val answer = findAnswer("day11-data.txt")
        println("Nr of occupied seats in stable state: $answer")
    }

    fun findAnswer(fileName: String): Int {
        val grid = readGrid(fileName)
        val result = findStableState(grid, true, 5)
        return result.countOccupiedSeats()
    }
}


enum class SeatState {FLOOR, EMPTY, OCCUPIED}

typealias SeatGrid=List<List<SeatState>>

fun readGrid(fileName: String): SeatGrid =
    getDataInputStream(fileName)
    .bufferedReader()
    .lines()
    .map { line -> parseGridRow(line) }
    .toList()

fun parseGridRow(line: String): List<SeatState> =
    line.map { chr ->
        when(chr) {
            '.' -> SeatState.FLOOR
            'L' -> SeatState.EMPTY
            '#' -> SeatState.OCCUPIED
            else -> throw IllegalArgumentException("Illegal char in input: $chr")
        }
    }

fun SeatGrid.getSeatAtPosition(row: Int, col: Int): SeatState =
    if (row < 0 || row >= size || col < 0 || col >= this[row].size) SeatState.EMPTY
    else this[row][col]

fun SeatGrid.getSurroundingSeats(row: Int, col: Int, ignoreFloors: Boolean): List<SeatState> =
    sequence {
        (-1..1).map { rowDelta ->
            (-1..1).map { colDelta ->
                if (!(colDelta == 0 && rowDelta == 0)) {
                    yield(
                        if (ignoreFloors) findSeatInDirection(row, col, rowDelta, colDelta)
                        else getSeatAtPosition(row+rowDelta, col+colDelta)
                    )
                }
            }
        }
    }.toList()

tailrec fun SeatGrid.findSeatInDirection(row: Int, col: Int, rowDelta: Int, colDelta: Int): SeatState {
    val state = getSeatAtPosition(row+rowDelta, col+colDelta)
    return if (state != SeatState.FLOOR) state
    else findSeatInDirection(row+rowDelta, col+colDelta, rowDelta, colDelta)
}

fun SeatGrid.countFreeSurroundingSeats(row: Int, col: Int, ignoreFloors: Boolean): Int =
    getSurroundingSeats(row, col, ignoreFloors).count { it != SeatState.OCCUPIED }

fun SeatGrid.countOccupiedSurroundingSeats(row: Int, col: Int, ignoreFloors: Boolean): Int =
    getSurroundingSeats(row, col, ignoreFloors).count { it == SeatState.OCCUPIED }

fun SeatGrid.newSeatState(row: Int, col: Int, ignoreFloors: Boolean, occupancyTolerance: Int): SeatState {
    val thisSeat = getSeatAtPosition(row, col)
    return when {
        thisSeat == SeatState.FLOOR -> SeatState.FLOOR
        thisSeat == SeatState.EMPTY && countFreeSurroundingSeats(row, col, ignoreFloors) == 8 -> SeatState.OCCUPIED
        thisSeat == SeatState.OCCUPIED && countOccupiedSurroundingSeats(row, col, ignoreFloors) >= occupancyTolerance -> SeatState.EMPTY
        else -> thisSeat
    }
}

fun SeatGrid.isSameState(other: SeatGrid): Boolean =
    zip(other).all { (rowThis, rowOther) ->
        rowThis.zip(rowOther).all { (seatThis, seatOther) -> seatThis == seatOther }
    }

fun SeatGrid.countOccupiedSeats(): Int =
    map { row -> row.count { it == SeatState.OCCUPIED } }.sum()

fun SeatGrid.nextGridState(ignoreFloors: Boolean, occupancyTolerance: Int): SeatGrid =
    (this.indices).map { row ->
        (this[row].indices).map { col ->
            newSeatState(row, col, ignoreFloors, occupancyTolerance)
        }
    }

tailrec fun findStableState(grid: SeatGrid, ignoreFloors: Boolean, occupancyTolerance: Int): SeatGrid {
    val nextGrid = grid.nextGridState(ignoreFloors, occupancyTolerance)
    return if (grid.isSameState(nextGrid)) grid
    else findStableState(nextGrid, ignoreFloors, occupancyTolerance)
}
