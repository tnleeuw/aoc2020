package org.tnl.aoc2020


object Day17Puzzle1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val startingState = readInputState("day17-data.txt")
        val endState = runLife(startingState, 6)
        val result = endState.size

        println("After 6 cycles, $result cells are active and alive")
    }
}

data class Coord(val x: Int, val y: Int, val z: Int)

fun readInputState(fileName: String): Set<Coord> =
    sequence {
        getDataInputStream(fileName).bufferedReader()
            .lineSequence()
            .forEachIndexed { y, line ->
                line.forEachIndexed { x, chr ->
                    if (chr == '#')
                        yield(Coord(x, y, 0))
                }
            }
    }.toSet()

fun Coord.getSurroundingCoordinates(): Sequence<Coord> =
    sequence {
        for (nx in x-1..x+1) {
            for (ny in y-1..y+1) {
                for (nz in z-1..z+1) {
                    if (!(x == nx && y == ny && z == nz))
                        yield(Coord(nx, ny, nz))
                }
            }
        }
    }

fun Set<Coord>.minX(): Int = minOf { it.x }
fun Set<Coord>.minY(): Int = minOf { it.y }
fun Set<Coord>.minZ(): Int = minOf { it.z }
fun Set<Coord>.maxX(): Int = maxOf { it.x }
fun Set<Coord>.maxY(): Int = maxOf { it.y }
fun Set<Coord>.maxZ(): Int = maxOf { it.z }

fun Set<Coord>.countNeighbours(coord: Coord): Int =
    coord.getSurroundingCoordinates()
        .count { contains(it) }

fun Set<Coord>.canLiveInNextGeneration(coord: Coord): Boolean =
    when(countNeighbours(coord)) {
        3 -> true
        2 -> contains(coord)
        else -> false
    }

fun Set<Coord>.nextGeneration(): Set<Coord> =
    sequence {
        for (x in minX()-1..maxX()+1) {
            for (y in minY()-1..maxY()+1) {
                for (z in minZ()-1..maxZ()+1) {
                    val coord = Coord(x, y, z)
                    if (canLiveInNextGeneration(coord)) yield(coord)
                }
            }
        }
    }.toSet()

tailrec fun runLife(state: Set<Coord>, nrOfGenerations: Int): Set<Coord> =
    if (state.isEmpty() || nrOfGenerations == 0) state
    else runLife(state.nextGeneration(), nrOfGenerations - 1)
