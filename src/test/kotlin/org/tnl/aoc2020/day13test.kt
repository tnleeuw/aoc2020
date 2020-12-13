import org.tnl.aoc2020.Day13Puzzle1
import org.tnl.aoc2020.calculateWaitingAllTimes
import org.tnl.aoc2020.calculateWaitingTime
import org.tnl.aoc2020.findShortestWait
import org.tnl.aoc2020.parseBusIds
import org.tnl.aoc2020.readInputs
import kotlin.test.Test
import kotlin.test.assertEquals

class Day13Test {

    @Test
    fun testParseBusIds() {
        // Arrange
        val inpLine = "7,13,x,x,59,x,31,19"

        // Act
        val result = parseBusIds(inpLine)

        // Assert
        assertEquals(setOf<Long>(7L, 13L, 19L, 31L, 59L), result)
    }

    @Test
    fun testReadInputs() {
        // Act
        val (arrival, busIds) = readInputs("day13-test.txt")

        // Assert
        assertEquals(939L, arrival)
        assertEquals(setOf<Long>(7L, 13L, 19L, 31L, 59L), busIds)
    }

    @Test
    fun testCalculateWaitingTime() {
        // Act
        val result = calculateWaitingTime(939, 59)

        // Assert
        assertEquals(5L, result)
    }

    @Test
    fun testFindShortestWait() {
        // Arrange
        val (arrival, busIds) = readInputs("day13-test.txt")
        val waitingTimes = calculateWaitingAllTimes(arrival, busIds)

        // Act
        val shortestWait = findShortestWait(waitingTimes)

        // Assert
        assertEquals(5L, shortestWait.value)
        assertEquals(59L, shortestWait.key)
    }

    @Test
    fun testPuzzle1Answer() {
        // Act
        val result = Day13Puzzle1.calculateAnswer("day13-test.txt")

        // Assert
        assertEquals(295L, result)

    }
}
