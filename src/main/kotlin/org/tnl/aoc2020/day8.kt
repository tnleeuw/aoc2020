package org.tnl.aoc2020

import java.lang.IllegalStateException

object Day8Puzzle1 {

    @JvmStatic
    fun main(args: Array<String>) {
        val program = readProgram("day8-data.txt")
        val result = runTillHaltOrLoop(program)

        println("Before looping, the accumulator had value: $result")
    }
}

object Day8Puzzle2 {

    @JvmStatic
    fun main(args: Array<String>) {
        val program = readProgram("day8-data.txt")
        val result = runProgramWithHotfix(program)

        println("At program exit, the accumulator had value: $result")
    }
}

enum class OpCode { NOP, ACC, JMP }

data class Instruction(val opCode: OpCode, val operand: Int)

data class CpuRegisters(var accumulator: Int = 0, var ip: Int = 0)

class ProgramLoopException(val accumulator: Int, val ip: Int): Exception("OOPS! Program Loop detected! Accumulator at loop: $accumulator, IP: $ip")

fun readProgram(fileName: String): List<Instruction> =
    fileToLines(fileName).map { parseLine(it) }.toList()

private val PARSE_LINE_RE = "(\\w+)\\s+([+-]\\d+)".toRegex()
fun parseLine(line: String): Instruction {
    val match = PARSE_LINE_RE.matchEntire(line)?.groupValues ?: throw IllegalArgumentException("Line doesn't contain a recognized instruction: '$line'")
    return Instruction(OpCode.valueOf(match[1].toUpperCase()), match[2].toInt())
}

fun executeInstruction(instruction: Instruction, registers: CpuRegisters) {
    when(instruction.opCode) {
        OpCode.NOP -> registers.ip++
        OpCode.ACC -> {
            registers.accumulator += instruction.operand
            registers.ip++
        }
        OpCode.JMP -> registers.ip += instruction.operand
    }
}

fun runTillHaltOrLoop(program: List<Instruction>): Int {
    return try {
        runTillHalt(program)
    } catch (ple: ProgramLoopException) {
        ple.accumulator
    }
}

fun runTillHalt(program: List<Instruction>): Int {
    val registers = CpuRegisters();
    val executedLines = mutableSetOf<Int>()

    while (registers.ip in 0 until program.size) {
        if (registers.ip in executedLines) {
            throw ProgramLoopException(registers.accumulator, registers.ip)
        }
        executedLines += registers.ip
        executeInstruction(program[registers.ip], registers)
    }

    return registers.accumulator
}

fun runProgramWithHotfix(program: List<Instruction>): Int {
    var fixAt = 0
    while (fixAt in 0 until program.size) {
        val (modifiedProgram, lastFixAt) = fixSingleInstructionFrom(program, fixAt)
        try {
            return runTillHalt(modifiedProgram)
        } catch (ple: ProgramLoopException) {
            println("Fix at $lastFixAt didn't work. Trying next position.")
            fixAt = lastFixAt + 1
        }
    }
    throw IllegalArgumentException("Couldn't determine how to fix input program to not loop")
}

internal fun fixSingleInstructionFrom(program: List<Instruction>, fromInstruction: Int): Pair<List<Instruction>, Int> {
    var modifiedAt: Int = -1
    val modifiedProgram = program.mapIndexed { index, instruction ->
        when {
            modifiedAt >= 0 -> instruction
            index < fromInstruction -> instruction
            instruction.opCode == OpCode.ACC -> instruction
            else -> {
                modifiedAt = index
                Instruction(if(instruction.opCode == OpCode.JMP) OpCode.NOP else OpCode.JMP, instruction.operand)
            }
        }
    }
    return Pair(modifiedProgram, modifiedAt)
}

