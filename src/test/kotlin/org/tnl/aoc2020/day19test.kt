package org.tnl.aoc2020

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day19Test {

    @Test
    fun testPuzzle1() {
        // Act
        val result = Day19Puzzle1.puzzle1("day19-test.txt")

        // Assert
        assertEquals(2, result)
    }
}
