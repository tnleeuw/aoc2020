package org.tnl.aoc2020

import java.lang.IllegalArgumentException
import kotlin.math.pow

typealias Joltage=Long

/**
 * complicated code to follow their story, not needed
 */
fun findNextFittingAdapter(availableAdapters: List<Joltage>,
                           usedAdapters: List<Joltage>,
                           startingJoltage: Joltage = 0): Pair<List<Joltage>, List<Joltage>> {
    val joltageReached = usedAdapters.maxOrNull() ?: startingJoltage

    val nextAdapter = availableAdapters.sorted().first { adapterFits(joltageReached, it) }
    return Pair(availableAdapters - nextAdapter, usedAdapters + nextAdapter)
}

fun adapterFits(availableJoltage: Joltage, adapterJoltage: Joltage): Boolean =
    (adapterJoltage - availableJoltage) in 0..3

/**
 * complicated code to follow their story, not needed
 */
fun buildAdapterChain(availableAdapters: List<Joltage>,
                      startingJoltage: Joltage = 0): List<Joltage> {
    // Add device adapter to available adapters
    val adaptersAndDevice = availableAdapters + (availableAdapters.maxOf { it } + 3)

    tailrec fun buildAdapterChainRecurse(adapters: Pair<List<Joltage>, List<Joltage>>): List<Joltage> {
        return if (adapters.first.isEmpty()) {
            adapters.second
        } else {
            buildAdapterChainRecurse(findNextFittingAdapter(adapters.first, adapters.second, startingJoltage))
        }
    }

    return buildAdapterChainRecurse(Pair(adaptersAndDevice, listOf<Joltage>()))
}

// this is what's really needed
object Day10Puzzle1 {

    @JvmStatic
    fun main(args: Array<String>) {
        val answer = calculateAnswer("day10-data.txt")
        println("The answer is: $answer")
    }

    fun calculateAnswer(fileName: String): Int =
        calculateDifferenceDistribution(calculateDifferences(readAllJoltages(fileName)))
}

object Day10Puzzle2 {

    @JvmStatic
    fun main(args: Array<String>) {
        val answer = calculatePossibleCombinationsFromFile("day10-data.txt")
        println("The answer is: $answer")
    }

}

fun readAllJoltages(fileName: String): List<Joltage> =
    buildJoltageList(readAdapterJoltages(fileName))

fun readAdapterJoltages(fileName: String): List<Joltage> =
    getNumbersFromStream(getDataInputStream(fileName))

fun buildJoltageList(joltages: List<Joltage>): List<Joltage> =
    listOf(0L) + joltages + calculateDeviceJoltage(joltages)

fun calculateDeviceJoltage(adapters: List<Joltage>): Joltage =
    (adapters.maxOf { it } + 3)

fun calculateDifferences(adapters: List<Joltage>): List<Long> =
    adapters.sorted().windowed(2).map { it[1] - it[0] }

fun calculateDifferenceDistribution(differences: List<Joltage>): Int =
    differences.filter { it == 1L }.count() * differences.filter { it == 3L }.count()

fun calculateConsecutive1JoltageDifferences(differences: List<Long>): Int =
    differences.windowed(2).filter { it.all { diff -> diff == 1L } }.count()

fun findConsecutiveRangesOf1s(differences: List<Long>): List<Int> =
    sequence<Int> {
        val last = differences.fold(0) { acc, l ->
            if (l == 1L) acc + 1
            else {
                yield(acc)
                0
            }
        }
        yield(last)
    }.filter { it > 0 }.toList()

fun groupRanges(rangeLengths: List<Int>): Map<Int, Int> =
    rangeLengths.groupBy(keySelector = {it}, valueTransform = {it})
        .mapValues { it.value.size }

fun calculateTotalPossibleCombinations(countedRanges: Map<Int, Int>): Long =
    countedRanges.map { (rangeLength, count) ->
        when(rangeLength) {
            1 -> 1L
            2 -> 2L
            3 -> 4L
            4 -> 7L
            else -> throw IllegalArgumentException("Don't know number of combinations for range length $rangeLength")
        }.pow(count)
    }.reduce{acc, value -> acc * value}

fun calculatePossibleCombinationsFromFile(fileName: String): Long =
    calculateTotalPossibleCombinations(groupRanges(
        findConsecutiveRangesOf1s(calculateDifferences(readAllJoltages(fileName)))
    ))

fun Long.pow(power: Int): Long {
    tailrec fun powR(base: Long, r: Long, power: Int): Long =
    when  {
        power == 0 -> r
        power and 1 == 1 -> powR(base, r * base, power-1)
        else -> powR(base * base, r, power shr 1)
    }
    return powR(this, 1, power)
}
