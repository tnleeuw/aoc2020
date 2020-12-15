package org.tnl.aoc2020

import java.lang.IllegalArgumentException

object Day15Puzzle1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val answer = playGame(readNumbersFromFile("day15-data.txt"))
        println("Puzzle 1 answer: $answer")
    }
}

object Day15Puzzle2 {
    @JvmStatic
    fun main(args: Array<String>) {
        val answer = playGame(readNumbersFromFile("day15-data.txt"), 30000000)
        println("Puzzle 1 answer: $answer")
    }
}

typealias MemoryGameState=MutableMap<Int, Pair<Int, Int>>

fun lineToNumbers(line: String): List<Int> =
    line.split(",").map { it.toInt() }

fun readNumbersFromFile(fileName: String): List<Int> =
    lineToNumbers(getDataInputStream(fileName).bufferedReader().readLine())


fun addStartingNumbers(numbers: List<Int>): MemoryGameState {
    val result = mutableMapOf<Int, Pair<Int, Int>>()
    numbers.forEachIndexed { index, number -> result[number] = Pair(index+1, 0) }
    return result
}

fun playTurn(turn: Int, lastNumber: Int, gameState: MemoryGameState): Int {
    val turnBefore = gameState[lastNumber] ?: throw IllegalArgumentException("lastNumber $lastNumber not in game state")
    val nextNumber = if (turnBefore.second == 0) {
        0
    } else {
        turnBefore.first - turnBefore.second
    }
    updateGameState(nextNumber, turn, gameState)
    return nextNumber
}

fun updateGameState(number: Int, turn: Int, gameState: MemoryGameState) {
    val turnBefore = gameState[number]?.first ?: 0
    gameState[number] = Pair(turn, turnBefore)
}

fun playGame(startingNumbers: List<Int>, endAtTurn: Int = 2020): Int {
    val gameState = addStartingNumbers(startingNumbers)
    var lastNumber: Int = startingNumbers.last()
    for (turn in startingNumbers.size+1..endAtTurn) {
        lastNumber = playTurn(turn, lastNumber, gameState)
    }
    return lastNumber
}
