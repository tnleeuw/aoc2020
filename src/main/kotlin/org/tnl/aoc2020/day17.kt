package org.tnl.aoc2020


object Day17Puzzle1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val startingState = readInputState("day17-data.txt", 3)
        val endState = runLife(startingState, 6)
        val result = endState.size

        println("After 6 cycles, $result cells are active and alive")
    }
}

object Day17Puzzle2 {
    @JvmStatic
    fun main(args: Array<String>) {
        val startingState = readInputState("day17-data.txt", 4)
        val endState = runLife(startingState, 6)
        val result = endState.size

        println("After 6 cycles, $result cells are active and alive")
    }
}

typealias Coord=List<Int>
fun Coord.allNeighbours(): Sequence<Coord> {

    fun generateOffsetsOverDimensions(workingState: Coord, nrDimensions: Int): Sequence<Coord> {
        return sequence {
            if (nrDimensions == 0) {
                if (!workingState.all { it == 0 }) {
                    yield(workingState)
                }
            }
            else for (dd in -1..1) {
                yieldAll(generateOffsetsOverDimensions(workingState + dd, nrDimensions -  1))
            }
        }
    }

    return generateOffsetsOverDimensions(listOf(), size)
        .map { offset -> offset.mapIndexed { dim, dimOffset -> get(dim) + dimOffset } }
}

fun Set<Coord>.minOverDim(dimension: Int): Int = minOf { it[dimension] }
fun Set<Coord>.maxOverDim(dimension: Int): Int = maxOf { it[dimension] }
fun Set<Coord>.nrOfDimensions(): Int = first().size

fun Set<Coord>.fullGridWithEdges(): Sequence<Coord> {

    fun generateOverDimensions(workingState: Coord, remainingDimensions: Int): Sequence<Coord> {
        return sequence {
            if (remainingDimensions <= 0) {
                yield(workingState)
            } else {
                val currentDim = workingState.size
                for (dimCoord in minOverDim(currentDim)-1..maxOverDim(currentDim)+1) {
                    yieldAll(generateOverDimensions(workingState + dimCoord, remainingDimensions - 1))
                }
            }
        }
    }

    return generateOverDimensions(listOf(), nrOfDimensions())
}

fun readInputState(fileName: String, nrDimensions: Int): Set<Coord> =
    sequence {
        val remainingDims = (0 until nrDimensions-2).map { 0 }
        getDataInputStream(fileName).bufferedReader()
            .lineSequence()
            .forEachIndexed { y, line ->
                line.forEachIndexed { x, chr ->
                    if (chr == '#')
                        yield(listOf(x, y) + remainingDims)
                }
            }
    }.toSet()

fun Set<Coord>.countNeighbours(coord: Coord): Int =
    coord.allNeighbours()
        .count { contains(it) }

fun Set<Coord>.canLiveInNextGeneration(coord: Coord): Boolean =
    when(countNeighbours(coord)) {
        3 -> true
        2 -> contains(coord)
        else -> false
    }

fun Set<Coord>.nextGeneration(): Set<Coord> =
    sequence {
        fullGridWithEdges().forEach { mcoord ->
            if (canLiveInNextGeneration(mcoord)) yield(mcoord)

        }
    }.toSet()

tailrec fun runLife(state: Set<Coord>, nrOfGenerations: Int): Set<Coord> =
    if (state.isEmpty() || nrOfGenerations == 0) state
    else runLife(state.nextGeneration(), nrOfGenerations - 1)
