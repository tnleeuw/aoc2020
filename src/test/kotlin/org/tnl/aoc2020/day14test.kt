package org.tnl.aoc2020

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.Test
import kotlin.test.assertEquals

class Day14Test {

    @ParameterizedTest
    @CsvSource(
        "11,73",
        "101,101",
        "0,64"
    )
    fun testApplyMask(value: Long, expected: Long) {
        // Act
        val result = value.applyMask("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X")

        // Assert
        assertEquals(expected, result)
    }

    @Test
    fun testRunProgram() {
        // Act
        val result = runProgram("day14-test.txt")

        // Assert
        assertEquals(165L, result)
    }
}
