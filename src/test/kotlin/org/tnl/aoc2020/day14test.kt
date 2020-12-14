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
    fun testRunProgramV1() {
        // Act
        val result = runProgram("day14p1-test.txt", ProgramState::setMemoryDecoderV1)

        // Assert
        assertEquals(165L, result)
    }

    @Test
    fun testRunProgramV2() {
        // Act
        val result = runProgram("day14p2-test.txt", ProgramState::setMemoryDecoderV2)

        // Assert
        assertEquals(208L, result)
    }

    @ParameterizedTest
    @CsvSource(
        "42,000000000000000000000000000000X1001X,000000000000000000000000000000X1101X",
        "26,00000000000000000000000000000000X0XX,00000000000000000000000000000001X0XX"
    )
    fun testApplyV2Mask(number: Long, mask: String, expected: String) {
        // Act
        val result = number.applyV2Mask(mask)

        // Assert
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @CsvSource(
        "00000000000000000000000000000001X0XX|16,17,18,19,24,25,26,27",
        "000000000000000000000000000000X1101X|26,27,58,59",
        delimiter = '|'
    )
    fun testGenerateAddressesFromFloatingBits(floatBitAddr: String, expected: String) {
        // Arrange
        val expectedAddrSet = expected.split(",")
            .map { it.toLong() }.toSet()

        // Act
        val result = floatBitAddr.generateAddressesFromFloatingBits().toSet()

        // Assert
        assertEquals(expectedAddrSet, result)
    }
}
