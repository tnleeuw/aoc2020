package org.tnl.aoc2020

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day2Puzzle1Test {

    @Test
    internal fun testCountValidEntriesPuzzle1() {
        // Act
        val result = countValidEntries("d2p1-test.txt", PwdDbEntry::puzzle1Policy)

        // Assert
        assertEquals(2, result)
    }

    @Test
    internal fun testCountValidEntriesPuzzle2() {
        // Act
        val result = countValidEntries("d2p1-test.txt", PwdDbEntry::puzzle2Policy)

        // Assert
        assertEquals(1, result)
    }
}

internal class PwdDbEntryTest {

    @Test
    internal fun testParse() {
        // Act
        val result = PwdDbEntry.parse("1-3 a: abcde")

        // Assert
        assertEquals(1, result.n1)
        assertEquals(3, result.n2)
        assertEquals('a', result.letter)
        assertEquals("abcde", result.pwd)
    }

    @Test
    internal fun testPuzzle1IsValid() {
        // Arrange
        val entry = PwdDbEntry.parse("1-3 a: abcde")

        // Act / Assert
        assertTrue(entry.puzzle1Policy())
    }

    @Test
    internal fun testPuzzle1IsNotValid() {
        // Arrange
        val entry = PwdDbEntry.parse("1-3 b: cdefg")

        // Act / Assert
        assertFalse(entry.puzzle1Policy())
    }
}
