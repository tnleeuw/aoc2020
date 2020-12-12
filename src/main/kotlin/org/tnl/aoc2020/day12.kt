package org.tnl.aoc2020

import kotlin.math.abs

object Day12Puzzle1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val answer = startingPoint().sumManhattanDistAfterAllMoves("day12-data.txt")
        println("After all moves the ship is at a distance of: $answer")
    }

    fun startingPoint() = Ship(Direction.E, 0L, 0L)
}

object Day12Puzzle2 {
    @JvmStatic
    fun main(args: Array<String>) {
        val answer = startingPoint().sumManhattanDistAfterAllMovesWithWayPoint("day12-data.txt")
        println("After all moves the ship is at a distance of: $answer")
    }

    fun startingPoint() = Ship(Direction.E, 0L, 0L, WayPoint(10L, 1L))
}

data class WayPoint(val ew: Long, val ns: Long)
enum class Direction { N, E, S, W }
data class Ship(
    val facing: Direction, val ewPos: Long, val nsPos: Long,
    val wayPoint: WayPoint = WayPoint(0L, 0L)
)

fun Direction.toChar(): Char = name[0]
fun Direction.turn(turnBy: String): Direction =
    Direction.values()[(this.ordinal
            + (turnBy.replace('R', '+')
                     .replace('L', '-')
                     .toInt()
            / 90)
            + Direction.values().size) % Direction.values().size]

fun String.toNrOfMoves(): Long =
    this.replace('F', '+')
        .replace('E', '+')
        .replace('N', '+')
        .replace('W', '-')
        .replace('S', '-')
        .toLong()

fun String.toNrOfTurns(): Int =
    substring(1).toInt() / 90

fun WayPoint.move(moveBy: String): WayPoint =
    when (moveBy[0]) {
        'E', 'W' -> WayPoint(ew + moveBy.toNrOfMoves(), ns)
        'N', 'S' -> WayPoint(ew, ns + moveBy.toNrOfMoves())
        // Ignore invalid instructions
        else -> this
    }

tailrec fun WayPoint.turnLeft(nrOfTurns: Int): WayPoint =
    if (nrOfTurns == 0) this
    else WayPoint(ew = -ns, ns = ew).turnLeft(nrOfTurns-1)

tailrec fun WayPoint.turnRight(nrOfTurns: Int): WayPoint =
    if (nrOfTurns == 0) this
    else WayPoint(ew = ns, ns = -ew).turnRight(nrOfTurns-1)

fun WayPoint.turn(turnBy: String): WayPoint =
    when(turnBy[0]) {
        'R' -> turnRight(turnBy.toNrOfTurns())
        'L' -> turnLeft(turnBy.toNrOfTurns())
        // Ignore invalid turn directions
        else -> this
    }

fun Ship.moveToWayPoint(nrOfMoves: String): Ship =
    moveToWayPoint(nrOfMoves.toNrOfMoves())

fun Ship.moveToWayPoint(nrOfMoves: Long): Ship =
    Ship(
        facing,
        ewPos + nrOfMoves * wayPoint.ew,
        nsPos + nrOfMoves * wayPoint.ns,
        wayPoint
    )

fun Ship.move(moveBy: String): Ship =
    when (moveBy[0]) {
        'F' -> move(moveBy.replace('F', facing.toChar()))
        'R', 'L' -> Ship(facing.turn(moveBy), ewPos, nsPos)
        'E', 'W' -> Ship(facing, ewPos + moveBy.toNrOfMoves(), nsPos)
        'N', 'S' -> Ship(facing, ewPos, nsPos + moveBy.toNrOfMoves())
        else -> throw IllegalArgumentException("Cannot execute movement instruction '$moveBy'")
    }

fun Ship.moveByWayPoint(moveBy: String): Ship =
    when (moveBy[0]) {
        'F' -> moveToWayPoint(moveBy.substring(1))
        'R', 'L' -> Ship(facing, ewPos, nsPos, wayPoint.turn(moveBy))
        else -> Ship(facing, ewPos, nsPos, wayPoint.move(moveBy))
    }

fun Ship.manhattanDistance(other: Ship): Pair<Long, Long> =
    Pair(abs(this.ewPos - other.ewPos), abs(this.nsPos - other.nsPos))

fun Ship.runMoves(moves: Sequence<String>): Ship =
    moves.fold(this) { ship, moveBy -> ship.move(moveBy) }

fun Ship.runMoves(fileName: String): Ship =
    runMoves(getMoves(fileName))

fun Ship.runMovesWithWayPoint(moves: Sequence<String>): Ship =
    moves.fold(this) { ship, moveBy -> ship.moveByWayPoint(moveBy) }

fun Ship.runMovesWithWayPoint(fileName: String): Ship =
    runMovesWithWayPoint(getMoves(fileName))

fun getMoves(fileName: String): Sequence<String> =
    getDataInputStream(fileName).bufferedReader().lineSequence()

fun Ship.sumManhattanDist(other: Ship): Long =
    manhattanDistance(other).toList().sum()

fun Ship.sumManhattanDistAfterAllMoves(fileName: String): Long =
    sumManhattanDist(runMoves(fileName))

fun Ship.sumManhattanDistAfterAllMovesWithWayPoint(fileName: String): Long =
    sumManhattanDist(runMovesWithWayPoint(fileName))
