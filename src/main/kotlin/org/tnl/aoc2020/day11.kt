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
        val result = findStableState(grid)
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
    if (row < 0 || row >= size || col < 0 || col >= this[row].size) SeatState.FLOOR
    else this[row][col]

fun SeatGrid.getSurroundingSeats(row: Int, col: Int): List<SeatState> =
    sequence {
        (-1..1).map { rowDelta ->
            (-1..1).map { colDelta ->
                if (!(colDelta == 0 && rowDelta == 0)) {
                    yield(getSeatAtPosition(row+rowDelta, col+colDelta))
                }
            }
        }
    }.toList()

fun SeatGrid.countFreeSurroundingSeats(row: Int, col: Int): Int =
    getSurroundingSeats(row, col).count { it != SeatState.OCCUPIED }

fun SeatGrid.countOccupiedSurroundingSeats(row: Int, col: Int): Int =
    getSurroundingSeats(row, col).count { it == SeatState.OCCUPIED }

fun SeatGrid.newSeatState(row: Int, col: Int): SeatState =
    when {
        getSeatAtPosition(row, col) == SeatState.FLOOR -> SeatState.FLOOR
        countFreeSurroundingSeats(row, col) == 8 -> SeatState.OCCUPIED
        countOccupiedSurroundingSeats(row, col) >= 4 -> SeatState.EMPTY
        else -> getSeatAtPosition(row, col)
    }

fun SeatGrid.isSameState(other: SeatGrid): Boolean =
    zip(other).all { (rowThis, rowOther) ->
        rowThis.zip(rowOther).all { (seatThis, seatOther) -> seatThis == seatOther }
    }

fun SeatGrid.countOccupiedSeats(): Int =
    map { row -> row.count { it == SeatState.OCCUPIED } }.sum()

fun SeatGrid.nextGridState(): SeatGrid =
    (this.indices).map { row ->
        (this[row].indices).map { col ->
            newSeatState(row, col)
        }
    }

tailrec fun findStableState(grid: SeatGrid): SeatGrid {
    val nextGrid = grid.nextGridState()
    return if (grid.isSameState(nextGrid)) grid
    else findStableState(nextGrid)
}
