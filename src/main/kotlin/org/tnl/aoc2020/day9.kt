package org.tnl.aoc2020

object Day9Puzzle1 {

    @JvmStatic
    fun main(args: Array<String>) {
        val result = scanInvalidNumberInFile("day9-data.txt", 25)
        println("Found the number: $result")
    }
}

object Day9Puzzle2 {

    @JvmStatic
    fun main(args: Array<String>) {
        val result = findEncryptionWeaknessInFile("day9-data.txt", 25)
        println("Found the number: $result")
    }
}

fun scanInvalidNumberInFile(fileName: String, lookbackSize: Int): Long =
    scanInvalidNumber(getNumbersFromStream(getDataInputStream(fileName)), lookbackSize)

fun scanInvalidNumber(numbers: List<Long>, lookbackSize: Int): Long {
    for (i in lookbackSize..numbers.size) {
        val lookbackWindow = numbers.subList(i - lookbackSize, i)
        val targetSum = numbers[i]
        val hasTargetSum = hasTargetSum(lookbackWindow, targetSum)
        if (!hasTargetSum) {
            return targetSum
        }
    }
    return -1
}

fun hasTargetSum(lookbackWindow: List<Long>, targetSum: Long): Boolean {
    try {
        Day1Puzzle1.findPair(lookbackWindow, targetSum)
        return true
    } catch (e: IllegalArgumentException) {
        return false
    }
}

fun findEncryptionWeaknessInFile(fileName: String, lookbackSize: Int): Long =
    findEncryptionWeakness(getNumbersFromStream(getDataInputStream(fileName)), lookbackSize)

fun findEncryptionWeakness(numbers: List<Long>, lookbackSize: Int): Long {
    val targetSum = scanInvalidNumber(numbers, lookbackSize)
    val range = findRangeThatSumsTo(numbers, targetSum)
    return range.minOrNull()!! + range.maxOrNull()!!
}

fun findRangeThatSumsTo(numbers: List<Long>, targetSum: Long): List<Long> {
    for (windowSize in 2..numbers.size-1) {
        val r = numbers.windowed(windowSize).find { it.sum() == targetSum }
        if (r != null) return r
    }
    throw IllegalArgumentException("Cannot find matching range")
}
