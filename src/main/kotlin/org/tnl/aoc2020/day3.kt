import org.tnl.aoc2020.PwdDbEntry
import org.tnl.aoc2020.countValidEntries
import org.tnl.aoc2020.getDataInputStream
import org.tnl.aoc2020.getStringsFromStream


object Day3Puzzle1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val result = countTreesInPath("d3p1-data.txt")
        println("Number of trees hit: $result")
    }

}

object Day3Puzzle2 {
    @JvmStatic
    fun main(args: Array<String>) {
        val result = multiplyTreesForAllSlopes("d3p1-data.txt")
        println("Tree-collision product for all tested slopes: $result")
        println("Int.MAX_VALUE: ${Int.MAX_VALUE}, result is larger? ${result > Int.MAX_VALUE.toLong()}")
    }

}

fun multiplyTreesForAllSlopes(fileName: String): Long {
    val map = readMap(fileName)
    val slopesToTest = listOf(Pair(1, 1), Pair(1, 3), Pair(1, 5), Pair(1, 7), Pair(2, 1))

    return slopesToTest.map { slope ->
        countTreesInPath(map, stepDown = slope.first, stepRight = slope.second)
    }.fold(1L) { r, n -> r * n }
}

fun countTreesInPath(fileName: String): Int {
    return countTreesInPath(readMap(fileName))
}

fun countTreesInPath(map: MapGrid, startLine: Int = 0, startPos: Int = 0, stepDown: Int = 1, stepRight: Int = 3): Int {
    return steps(startLine, startPos, stepDown, stepRight)
        .takeWhile { step -> map.isLineOnMap(step.first) }
        .count { step -> map.positionHasTree(step.first, step.second) }
}

fun steps(startLine: Int = 0, startPos: Int = 0, stepDown: Int = 1, stepRight: Int = 3): Sequence<Pair<Int, Int>> {

    fun step(coords: Pair<Int, Int>): Pair<Int, Int> {
        return Pair(coords.first + stepDown, coords.second + stepRight)
    }

    return generateSequence(Pair(startLine, startPos)) { step(it) }
}


fun readMap(fileName: String): MapGrid {
    return MapGrid.parseGridLines(getStringsFromStream(getDataInputStream(fileName)))
}

class MapGrid(val trees: Array<BooleanArray>) {
    init {
        require(trees.isNotEmpty()) { "The map may not be empty" }
    }

    val height = trees.size
    val width = trees[0].size

    init {
        require(trees.all { line -> line.size == width }) {
            "All lines of the map should be of the same width"
        }
    }

    companion object {
        fun parseGridLines(lines: Sequence<String>): MapGrid {
            val trees: Array<BooleanArray> = lines.map { line ->
                line.map { chr -> chr == '#' }.toBooleanArray()
            }.toList().toTypedArray()
            return MapGrid(trees)
        }
    }

    fun isLineOnMap(line: Int): Boolean {
        return line < height
    }

    fun positionHasTree(line: Int, pos: Int): Boolean {
        val gridPos = pos % width
        return trees[line][gridPos]
    }
}
