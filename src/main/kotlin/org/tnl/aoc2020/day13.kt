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
        return puzzle2Lcm(buses.entries.toList())
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


fun puzzle2Lcm(buses: List<Map.Entry<Int, BigInteger>>): BigInteger {
    tailrec fun findLcm(lcm: BigInteger, increment: BigInteger, busIndex: BigInteger, busId: BigInteger): BigInteger =
        if ((lcm + busIndex) % busId == BigInteger.ZERO) lcm
        else findLcm(lcm + increment, increment,busIndex, busId)

    tailrec fun find(lcm: BigInteger, increment: BigInteger, buses: List<Map.Entry<Int, BigInteger>>):  BigInteger =
        if (buses.isEmpty()) lcm
        else find(findLcm(lcm, increment, buses[0].key.toBigInteger(), buses[0].value),
                  increment * buses[0].value, buses.subList(1, buses.size))

    return find(BigInteger.ZERO, buses[0].value, buses.subList(1, buses.size))
}
