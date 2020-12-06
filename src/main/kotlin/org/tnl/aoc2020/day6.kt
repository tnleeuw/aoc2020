package org.tnl.aoc2020

object Day6Puzzle1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val answer = calculatePuzzleAnswer("day6-data.txt")
        println("Sum of all counts = $answer")
    }

    fun calculatePuzzleAnswer(fileName: String): Int =
        sumAnswerSizes(getGroupAnswers(fileToLines(fileName), MutableSet<Char>::addAnswersPuzzle1))
}

object Day6Puzzle2 {
    @JvmStatic
    fun main(args: Array<String>) {
        val answer = calculatePuzzleAnswer("day6-data.txt")
        println("Sum of all counts = $answer")
    }

    fun calculatePuzzleAnswer(fileName: String): Int =
        sumAnswerSizes(getGroupAnswers(fileToLines(fileName), MutableSet<Char>::addAnswersPuzzle2))
}

fun sumAnswerSizes(groupAnswers: Sequence<Set<Char>>): Int =
    groupAnswers.map { it -> it.size }.sum()

fun getGroupAnswers(lines: Sequence<String>, addAnswers: MutableSet<Char>.(String, Boolean) -> Unit): Sequence<Set<Char>> {
    return sequence {
        var group = mutableSetOf<Char>()
        var isNewGroup: Boolean = true
        lines.forEach { line ->
            if (line.isBlank()) {
                yield(group)
                isNewGroup = true
                group = mutableSetOf()
            } else {
                group.addAnswers(line, isNewGroup)
                isNewGroup = false
            }
        }
        yield(group)
    }
}

fun MutableSet<Char>.addAnswersPuzzle1(line: String, isNewGroup: Boolean) {
    addAll(line.toSet())
}

fun MutableSet<Char>.addAnswersPuzzle2(line: String, isNewGroup: Boolean) {
    if (isNewGroup) {
        addAll(line.toSet())
    } else {
        retainAll(line.toSet())
    }
}
