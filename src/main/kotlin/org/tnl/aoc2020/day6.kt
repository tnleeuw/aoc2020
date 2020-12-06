package org.tnl.aoc2020

object Day6Puzzle1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val answer = calculatePuzzleAnswer("day6-data.txt")
        println("Sum of all counts = $answer")
    }

    fun calculatePuzzleAnswer(fileName: String): Int =
        sumAnswerSizes(getGroupAnswers(fileToLines(fileName)))
}

fun sumAnswerSizes(groupAnswers: Sequence<Set<Char>>): Int =
    groupAnswers.map { it -> it.size }.sum()

fun getGroupAnswers(lines: Sequence<String>): Sequence<Set<Char>> {
    return sequence {
        var group = mutableSetOf<Char>()
        lines.forEach { line ->
            if (line.isBlank()) {
                yield(group)
                group = mutableSetOf()
            } else {
                group.addAnswers(line)
            }
        }
        yield(group)
    }
}

fun MutableSet<Char>.addAnswers(line: String) {
    line.forEach { chr -> this.add(chr) }
}
