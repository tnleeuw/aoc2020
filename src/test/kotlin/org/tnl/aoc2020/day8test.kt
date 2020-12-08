package org.tnl.aoc2020

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day8Test {

    @ParameterizedTest
    @CsvSource(
        "nop +0, NOP, 0",
        "acc +1, ACC, 1",
        "acc -1, ACC, -1",
        "jmp -15, JMP, -15",
        "jmp +15, JMP, 15",
    )
    internal fun testParseLine(line: String, expectedOpcode: OpCode, expectedOperand: Int) {
        // Act
        val instruction = parseLine(line)

        // Assert
        assertEquals(expectedOpcode, instruction.opCode)
        assertEquals(expectedOperand, instruction.operand)
    }

    @ParameterizedTest
    @CsvSource(
        "nop +5, 10, 10, 10, 11",
        "acc +5, 1, 3, 6, 4",
        "acc -5, 1, 3, -4, 4",
        "jmp -1, 4, 6, 4, 5",
        "jmp +15, 4, 5, 4, 20",
    )
    internal fun testExecuteInstruction(line: String, startAcc: Int, startIp: Int, expectedAcc: Int, expectedIp: Int) {
        // Arrange
        val instruction = parseLine(line)
        val registers = CpuRegisters(startAcc, startIp)

        // Act
        executeInstruction(instruction, registers)

        // Assert
        assertEquals(expectedAcc, registers.accumulator)
        assertEquals(expectedIp, registers.ip)
    }

    @Test
    internal fun testRunProgram() {
        // Arrange
        val program = readProgram("day8-test.txt")

        // Act
        val result = runTillHaltOrLoop(program)

        // Assert
        assertEquals(5, result)
    }
}
