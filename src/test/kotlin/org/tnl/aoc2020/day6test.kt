package org.tnl.aoc2020

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class ReadAnswersTest {

    @Test
    internal fun testAddAnswers() {
        // Arrange
        val group = mutableSetOf<Char>()

        // Act
        group.addAnswers("abcx")

        // Assert
        assertEquals(4, group.size)
        assertTrue { group.contains('a') }
        assertTrue { group.contains('b') }
        assertTrue { group.contains('c') }
        assertTrue { group.contains('x') }
    }

    @Test
    internal fun testGetGroupAnswers() {
        // Arrange
        val lines = fileToLines("day6-test.txt")

        // Act
        val groupAnswers = getGroupAnswers(lines).toList()

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
    internal fun testSumAnswerSizes() {
        // Arrange
        val lines = fileToLines("day6-test.txt")
        val groupAnswers = getGroupAnswers(lines)

        // Act
        val result = sumAnswerSizes(groupAnswers)

        // Assert
        assertEquals(11, result)
    }

    @Test
    internal fun testDay6Puzzle1() {
        // Act
        val result = Day6Puzzle1.calculatePuzzleAnswer("day6-test.txt")

        // Assert
        assertEquals(11, result)
    }
}
