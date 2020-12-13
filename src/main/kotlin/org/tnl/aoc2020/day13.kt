package org.tnl.aoc2020


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

