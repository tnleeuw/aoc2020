package org.tnl.aoc2020

object Day5Puzzle1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val highestSeatId = findHighestSeatId(
            fileToLines("day5-data.txt")
        )

        println("The highest seat id found: $highestSeatId")
    }
}

object Day5Puzzle2 {
    @JvmStatic
    fun main(args: Array<String>) {
        val mySeatId = findMySeat("day5-data.txt")

        println("My seat id is: $mySeatId")
    }
}

fun findMySeat(fileName: String) = findGappedSeatId(
    boardingPassesToSeatIds(
        fileToLines(fileName)
    ))
/**
 * This ignores, for brevity, the problem if returning the
 * sentinel or the max value in the list.
 */
fun findGappedSeatId(seatIds: Sequence<Int>): Int =
    seatIds.sorted().fold(0) { prev, curr ->
        if (curr - prev == 2) {
            return curr - 1
        } else {
            curr
        }
    }


fun fileToLines(fileName: String): Sequence<String> =
    getDataInputStream(fileName).bufferedReader().lineSequence()

fun findHighestSeatId(boardingPasses: Sequence<String>): Int =
    boardingPassesToSeatIds(boardingPasses)
        .maxOrNull() ?: throw IllegalArgumentException("No boarding passes in input")

fun boardingPassesToSeatIds(boardingPasses: Sequence<String>): Sequence<Int> =
    boardingPasses.map { pass -> calculateSeatId(pass) }


val TO_0_RE = "([FL])".toRegex()
val TO_1_RE = "[BR]".toRegex()
fun String.specToBinary(): String =
    this.replace(TO_0_RE, "0")
        .replace(TO_1_RE, "1")

fun String.binaryToInt(): Int = toInt(2)

fun calculateSeatId(seatSpec: String): Int = seatSpec.specToBinary().binaryToInt()

val SEAT_SPEC_RE = "([FB]+)([LR]+)".toRegex()
fun seatSpecToRowCol(seatSpec: String): Pair<Int, Int> {
    val match = SEAT_SPEC_RE.matchEntire(seatSpec) ?: throw IllegalArgumentException("Invalid seat spec: $seatSpec")

    val specs = match.groupValues

    return Pair(rowToInt(specs[1]), colToInt(specs[2]))
}

fun rowToInt(rowSpec: String) = rowSpec.specToBinary().binaryToInt()

fun colToInt(colSpec: String) = colSpec.specToBinary().binaryToInt()
