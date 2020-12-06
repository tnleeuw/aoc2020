package org.tnl.aoc2020

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class ReadAnswersTest {

    @Test
    internal fun testAddAnswersPuzzle() {
        // Arrange
        val group = mutableSetOf<Char>()

        // Act
        group.addAnswersPuzzle1("abcx", true)

        // Assert
        assertEquals(4, group.size)
        assertTrue { group.contains('a') }
        assertTrue { group.contains('b') }
        assertTrue { group.contains('c') }
        assertTrue { group.contains('x') }
    }

    @Test
    internal fun testGetGroupAnswersPuzzle1() {
        // Arrange
        val lines = fileToLines("day6-test.txt")

        // Act
        val groupAnswers = getGroupAnswers(lines, MutableSet<Char>::addAnswersPuzzle1).toList()

        // Assert
        assertEquals(5, groupAnswers.size)
        val (g1, g2, g3, g4, g5) = groupAnswers
        assertEquals(3, g1.size)
        assertEquals(3, g2.size)
        assertEquals(3, g3.size)
        assertEquals(1, g4.size)
        assertEquals(1, g5.size)
    }

    @Test
    internal fun testGetGroupAnswersPuzzle2() {
        // Arrange
        val lines = fileToLines("day6-test.txt")

        // Act
        val groupAnswers = getGroupAnswers(lines, MutableSet<Char>::addAnswersPuzzle2).toList()

        // Assert
        assertEquals(5, groupAnswers.size)
        val (g1, g2, g3, g4, g5) = groupAnswers
        assertEquals(3, g1.size)
        assertEquals(0, g2.size)
        assertEquals(1, g3.size)
        assertEquals(1, g4.size)
        assertEquals(1, g5.size)
    }

    @ParameterizedTest
    @MethodSource("providesSumAnswers")
    internal fun testSumAnswerSizes(addAnswers: MutableSet<Char>.(String, Boolean) -> Unit, expected: Int) {
        // Arrange
        val lines = fileToLines("day6-test.txt")
        val groupAnswers = getGroupAnswers(lines, addAnswers)

        // Act
        val result = sumAnswerSizes(groupAnswers)

        // Assert
        assertEquals(expected, result)
    }

    @Test
    internal fun testDay6Puzzle1() {
        // Act
        val result = Day6Puzzle1.calculatePuzzleAnswer("day6-test.txt")

        // Assert
        assertEquals(11, result)
    }

    @Test
    internal fun testDay6Puzzle2() {
        // Act
        val result = Day6Puzzle2.calculatePuzzleAnswer("day6-test.txt")

        // Assert
        assertEquals(6, result)
    }

    companion object {
        @JvmStatic
        fun providesSumAnswers() =
            Stream.of(
                Arguments.of(MutableSet<Char>::addAnswersPuzzle1, 11),
                Arguments.of(MutableSet<Char>::addAnswersPuzzle2, 6),
            )
    }
}
