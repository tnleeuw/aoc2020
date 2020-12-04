package org.tnl.aoc2020

import java.io.InputStream
import kotlin.streams.asSequence

object Day2Puzzle1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val result = countValidEntries("day2-data.txt", PwdDbEntry::puzzle1Policy)
        println("Total valid passwords: $result")
    }

}

object Day2Puzzle2 {
    @JvmStatic
    fun main(args: Array<String>) {
        val result = countValidEntries("day2-data.txt", PwdDbEntry::puzzle2Policy)
        println("Total valid passwords: $result")
    }

}

fun countValidEntries(fileName: String, matchesPolicy: PwdDbEntry.() -> Boolean): Int {
    return getStringsFromStream(getDataInputStream(fileName))
        .filter { it.isNotBlank() }
        .map { PwdDbEntry.parse(it) }
        .count { it.matchesPolicy() }
}

class PwdDbEntry(val n1: Int, val n2: Int, val letter: Char, val pwd: String) {
    companion object {

        val PARSE_RE = "^(\\d+)-(\\d+)\\s+([a-zA-Z]):\\s+([a-zA-Z]+)$".toRegex()
        fun parse(line: String): PwdDbEntry {
            return PARSE_RE.matchEntire(line)?.let { match ->
                val values = match.groupValues

                return PwdDbEntry(
                    values[1].toInt(),
                    values[2].toInt(),
                    values[3][0],
                    values[4]
                )
            } ?: throw IllegalArgumentException("No match for pwd db entry in line '$line'")
        }
    }
}

fun PwdDbEntry.puzzle1Policy(): Boolean {
    return pwd.count { chr -> chr == letter } in n1..n2
}

fun PwdDbEntry.puzzle2Policy(): Boolean {
    return (listOf(n1, n2)
        .map { idx -> pwd[idx - 1] }
        .count { chr -> chr == letter }
            == 1)
}

fun getStringsFromStream(stream: InputStream): Sequence<String> {
    return stream.bufferedReader().lines().asSequence()
}

