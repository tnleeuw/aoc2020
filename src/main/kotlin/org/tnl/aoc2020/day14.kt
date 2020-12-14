package org.tnl.aoc2020

object Day14Puzzle1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val answer = runProgram("day14-data.txt")
        println("Sum of memory: $answer")
    }
}


class ProgramState {
    var mask: String = "X".repeat(36)
    val memory = mutableMapOf<Long, Long>()
}

fun ProgramState.setMemory(address: Long, value: Long) {
    memory[address] = value.applyMask(mask)
}

fun ProgramState.sumOfMemory(): Long =
    memory.values.sum()

fun Long.applyMask(mask: String): Long =
    this.toString(2).padStart(36, '0')
        .mapIndexed { index, bit ->
            when(mask[index]) {
                'X' -> bit
                '1' -> '1'
                '0' -> '0'
                else -> IllegalArgumentException("Illegal mask bit at $index: $mask")
            }
        }
        .joinToString(separator = "")
        .toLong(2)


private val MEM_INS_RE = "mem\\[(\\d+)]".toRegex()
fun applyInstruction(state: ProgramState, line: String) {
    val (oper, value) = line.split("=")
    if (oper.trim() == "mask") {
        state.mask = value.trim()
    } else {
        val match = MEM_INS_RE.find(oper) ?: throw IllegalArgumentException("Cannot parse instruction '$line'")
        val address = match.groupValues[1].toLong()
        state.setMemory(address, value.trim().toLong())
    }
}

fun runProgram(fileName: String): Long {
    val state = ProgramState()
    getDataInputStream(fileName).bufferedReader()
        .lineSequence()
        .forEach { applyInstruction(state, it) }
    return state.sumOfMemory()
}
