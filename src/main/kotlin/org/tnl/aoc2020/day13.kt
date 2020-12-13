package org.tnl.aoc2020

import java.math.BigInteger


object Day13Puzzle1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val answer = calculateAnswer("day13-data.txt")
        println("The answer is: $answer")
    }

    fun calculateAnswer(fileName: String): Long {
        val (arrival, busIds) = readInputs(fileName)
        val waitingTimes = calculateWaitingAllTimes(arrival, busIds)

        val shortestWait = findShortestWait(waitingTimes)

        return shortestWait.key * shortestWait.value
    }
}

object Day13Puzzle2 {
    @JvmStatic
    fun main(args: Array<String>) {
        val answer = calculateAnswer("day13-data.txt", BigInteger("100000000000000"))
        println("The answer is: $answer")
    }

    fun calculateAnswer(fileName: String, startingAt: BigInteger): BigInteger {
        val buses = readInputsPuzzle2(fileName)
        val maxBus = buses.busWithLargestId()
        return puzzle2BruteForcing(firstCandidateAfter(startingAt, maxBus), maxBus, buses)
    }
}

fun readInputs(fileName: String): Pair<Long, Set<Long>> {
    val inp = getDataInputStream(fileName).bufferedReader()
    return Pair(inp.readLine().toLong(), parseBusIds(inp.readLine()))
}

fun parseBusIds(line: String): Set<Long> =
    line.split(",")
        .filter { it != "x" }
        .map { it.toLong() }
        .toSet()

fun calculateWaitingAllTimes(arrival: Long, busIds: Set<Long>): Map<Long, Long> =
    busIds.associateBy({bus -> bus}) { bus ->
        calculateWaitingTime(arrival, bus)
    }

fun calculateWaitingTime(arrival: Long, bus: Long): Long =
    ((arrival / bus) + 1) * bus - arrival

fun findShortestWait(busWaitTimes: Map<Long, Long>): Map.Entry<Long, Long> =
    busWaitTimes.entries.minByOrNull { e -> e.value } ?: throw IllegalArgumentException("Cannot find shortest wait time")

fun readInputsPuzzle2(fileName: String): Map<Int, BigInteger> {
    val inp = getDataInputStream(fileName).bufferedReader()
    // Discard first line
    inp.readLine()
    // Process only the 2nd line
    return parseBusIdsWithIndex(inp.readLine())
}

fun parseBusIdsWithIndex(line: String): Map<Int, BigInteger> =
    line.split(",")
        .mapIndexed { index, s ->  Pair(index, s)}
        .filter { it.second != "x" }
        .map { (index, s) -> Pair(index, BigInteger.valueOf(s.toLong())) }
        .associate { it }

fun isGoldenTimestamp(timestamp: BigInteger, buses: Map<Int, BigInteger>): Boolean =
    buses.all { (index, busId)  -> (timestamp + BigInteger.valueOf(index.toLong())) % busId == BigInteger.ZERO}

tailrec fun puzzle2BruteForcing(testValue: BigInteger, steppingWithBus: Map.Entry<BigInteger, BigInteger>, buses: Map<Int, BigInteger>): BigInteger =
    if (isGoldenTimestamp(testValue.testValueToTimestamp(steppingWithBus), buses)) testValue.testValueToTimestamp(steppingWithBus)
    else puzzle2BruteForcing(testValue + steppingWithBus.value, steppingWithBus, buses)

fun firstCandidateAfter(timestamp: BigInteger, bus: Map.Entry<BigInteger, BigInteger>): BigInteger =
    ((timestamp / bus.value) + BigInteger.ONE) * bus.value

fun BigInteger.testValueToTimestamp(withBus: Map.Entry<BigInteger, BigInteger>): BigInteger =
    this - withBus.key

fun Map<Int, BigInteger>.busWithLargestId(): Map.Entry<BigInteger, BigInteger> = maxByOrNull { it.value }!!.withBigIntKey()

fun Map.Entry<Int, BigInteger>.withBigIntKey() =
    BusEntry(key.toBigInteger(), value)

class BusEntry(override val key: BigInteger,
               override val value: BigInteger): Map.Entry<BigInteger, BigInteger>
