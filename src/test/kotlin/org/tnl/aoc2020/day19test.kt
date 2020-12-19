package org.tnl.aoc2020

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

internal class Day19Test {

    @ParameterizedTest
    @CsvSource(
        "day19p1-test.txt, 2",
        "day19p2-test1.txt, 3",
        "day19p2-test2.txt, 12",
    )
    fun testPuzzle1(fileName: String, expected: Int) {
        // Act
        val result = Day19Puzzle1.puzzle1(fileName)

        // Assert
        assertEquals(expected, result)
    }

    @Test
    fun testPuzzle2() {
        // Act
        val result = Day19Puzzle1.puzzle1("day19p2-data.txt")

        // Assert
        assertNotEquals(342, result)
    }
}
