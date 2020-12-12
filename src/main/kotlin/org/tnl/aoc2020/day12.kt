package org.tnl.aoc2020

import kotlin.math.abs

object Day12Puzzle1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val answer = Ship(Direction.E, 0L, 0L).sumManhattanDistAfterAllMoves("day12-data.txt")
        println("After all moves the ship is at a distance of: $answer")
    }
}

enum class Direction { N, E, S, W }
data class Ship(val facing: Direction, val ewPos: Long, val nsPos: Long)

fun Direction.toChar(): Char = name[0]
fun Direction.turn(turnBy: String): Direction =
    Direction.values()[(this.ordinal
            + (turnBy.replace('R', '+')
                .replace('L', '-')
                .toInt()
            / 90)
            + Direction.values().size) % Direction.values().size]

fun String.nrOfMoves(): Long =
    this.replace('E', '+')
        .replace('N', '+')
        .replace('W', '-')
        .replace('S', '-')
        .toLong()

fun Ship.move(moveBy: String): Ship =
    when(moveBy[0]) {
        'F' -> move(moveBy.replace('F', facing.toChar()))
        'R', 'L' -> Ship(facing.turn(moveBy), ewPos, nsPos)
        'E', 'W' -> Ship(facing, ewPos + moveBy.nrOfMoves(), nsPos)
        'N', 'S' -> Ship(facing, ewPos, nsPos + moveBy.nrOfMoves())
        else -> throw IllegalArgumentException("Cannot execute movement instruction '$moveBy'")
    }

fun Ship.manhattanDistance(other: Ship): Pair<Long, Long> =
    Pair(abs(this.ewPos - other.ewPos), abs(this.nsPos - other.nsPos))

fun Ship.runMoves(moves: Sequence<String>): Ship =
    moves.fold(this) { ship, moveBy -> ship.move(moveBy)}

fun Ship.runMoves(fileName: String): Ship =
    runMoves(getMoves(fileName))

fun getMoves(fileName: String): Sequence<String> =
    getDataInputStream(fileName).bufferedReader().lineSequence()

fun Ship.sumManhattanDist(other: Ship): Long =
    manhattanDistance(other).toList().sum()

fun Ship.sumManhattanDistAfterAllMoves(fileName: String): Long =
    sumManhattanDist(runMoves(fileName))
