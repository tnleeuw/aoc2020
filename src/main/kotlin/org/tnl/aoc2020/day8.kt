package org.tnl.aoc2020

object Day8Puzzle1 {

    @JvmStatic
    fun main(args: Array<String>) {
        val program = readProgram("day8-data.txt")
        val result = runTillHaltOrLoop(program)

        println("Before looping, the accumulator had value: $result")
    }
}

enum class OpCode { NOP, ACC, JMP }

class Instruction(val opCode: OpCode, val operand: Int)

class CpuRegisters(var accumulator: Int = 0, var ip: Int = 0)

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
    val registers = CpuRegisters();
    val executedLines = mutableSetOf<Int>()

    while (registers.ip in 0..program.size && registers.ip !in executedLines) {
        executedLines += registers.ip
        executeInstruction(program[registers.ip], registers)
    }

    return registers.accumulator
}
