package org.tnl.aoc2020

object Day14Puzzle1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val answer = runProgram("day14-data.txt", ProgramState::setMemoryDecoderV1)
        println("Sum of memory: $answer")
    }
}

object Day14Puzzle2 {
    @JvmStatic
    fun main(args: Array<String>) {
        val answer = runProgram("day14-data.txt", ProgramState::setMemoryDecoderV2)
        println("Sum of memory: $answer")
    }
}


class ProgramState(val setMemory: ProgramState.(Long, Long) -> Unit) {
    var mask: String = "X".repeat(36)
    val memory = mutableMapOf<Long, Long>()
}

fun ProgramState.setMemoryDecoderV1(address: Long, value: Long) {
    memory[address] = value.applyMask(mask)
}

fun ProgramState.setMemoryDecoderV2(address: Long, value: Long) {
    address.applyV2Mask(mask).generateAddressesFromFloatingBits()
        .forEach { memory[it] = value }
}

fun Long.applyV2Mask(mask: String): String =
    this.toString(2).padStart(36, '0')
        .mapIndexed { index, bit ->
            when (mask[index]) {
                'X' -> 'X'
                '1' -> '1'
                '0' -> bit
                else -> IllegalArgumentException("Illegal mask bit at $index: $mask")
            }
        }.joinToString(separator = "")

fun String.generateAddressesFromFloatingBits(): Sequence<Long> =
    sequence {
        val x = indexOf('X')
        when(x) {
            -1 -> yield(toLong(2))
            0 -> {
                yieldAll(("0" + substring(1))
                    .generateAddressesFromFloatingBits())
                yieldAll(("1" + substring(1))
                    .generateAddressesFromFloatingBits())
            }
            (length - 1) -> {
                yieldAll((substring(0, x) + "0")
                    .generateAddressesFromFloatingBits())
                yieldAll((substring(0, x) + "1")
                    .generateAddressesFromFloatingBits())
            }
            else -> {
                yieldAll((substring(0, x) + "0" + substring(x+1))
                    .generateAddressesFromFloatingBits())
                yieldAll((substring(0, x) + "1" + substring(x+1))
                    .generateAddressesFromFloatingBits())

            }
        }
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
        state.setMemory(state, address, value.trim().toLong())
    }
}

fun runProgram(fileName: String, decoder: ProgramState.(Long, Long) -> Unit): Long {
    val state = ProgramState(decoder)
    getDataInputStream(fileName).bufferedReader()
        .lineSequence()
        .forEach { applyInstruction(state, it) }
    return state.sumOfMemory()
}
