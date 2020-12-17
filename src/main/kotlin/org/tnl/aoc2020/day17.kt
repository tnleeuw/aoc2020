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

typealias Point=List<Int>
fun Point.allNeighbours(): Sequence<Point> {

    fun generateOffsetsOverDimensions(workingState: Point, nrDimensions: Int): Sequence<Point> {
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

fun Set<Point>.minOverDim(dimension: Int): Int = minOf { it[dimension] }
fun Set<Point>.maxOverDim(dimension: Int): Int = maxOf { it[dimension] }
fun Set<Point>.nrOfDimensions(): Int = first().size

fun Set<Point>.fullGridWithEdges(): Sequence<Point> {

    fun generateOverDimensions(workingState: Point, remainingDimensions: Int): Sequence<Point> {
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

fun readInputState(fileName: String, nrDimensions: Int): Set<Point> =
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

fun Set<Point>.countNeighbours(point: Point): Int =
    point.allNeighbours()
        .count { contains(it) }

fun Set<Point>.canLiveInNextGeneration(point: Point): Boolean =
    when(countNeighbours(point)) {
        3 -> true
        2 -> contains(point)
        else -> false
    }

fun Set<Point>.nextGeneration(): Set<Point> =
    sequence {
        fullGridWithEdges().forEach { mcoord ->
            if (canLiveInNextGeneration(mcoord)) yield(mcoord)

        }
    }.toSet()

tailrec fun runLife(state: Set<Point>, nrOfGenerations: Int): Set<Point> =
    if (state.isEmpty() || nrOfGenerations == 0) state
    else runLife(state.nextGeneration(), nrOfGenerations - 1)
