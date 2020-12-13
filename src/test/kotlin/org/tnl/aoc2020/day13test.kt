import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.tnl.aoc2020.BusEntry
import org.tnl.aoc2020.Day13Puzzle1
import org.tnl.aoc2020.Day13Puzzle2
import org.tnl.aoc2020.calculateWaitingAllTimes
import org.tnl.aoc2020.calculateWaitingTime
import org.tnl.aoc2020.findShortestWait
import org.tnl.aoc2020.isGoldenTimestamp
import org.tnl.aoc2020.parseBusIds
import org.tnl.aoc2020.parseBusIdsWithIndex
import org.tnl.aoc2020.puzzle2BruteForcing
import org.tnl.aoc2020.readInputs
import org.tnl.aoc2020.readInputsPuzzle2
import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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

    @Test
    fun testParseBusIdsWithIndex() {
        // Act
        val result = parseBusIdsWithIndex("7,13,x,x,59,x,31,19")

        // Assert
        assertTrue(result.containsKey(0))
        assertTrue(result.containsKey(1))
        assertFalse(result.containsKey(2))
        assertFalse(result.containsKey(3))
        assertTrue(result.containsKey(4))
        assertFalse(result.containsKey(5))
        assertTrue(result.containsKey(6))
        assertTrue(result.containsKey(7))

        assertEquals(BigInteger.valueOf(7), result[0])
        assertEquals(BigInteger.valueOf(13), result[1])
        assertEquals(BigInteger.valueOf(59), result[4])
        assertEquals(BigInteger.valueOf(31), result[6])
        assertEquals(BigInteger.valueOf(19), result[7])
    }

    @Test
    fun testReadInputsPuzzle2() {
        // Act
        val result = readInputsPuzzle2("day13-test.txt")

        // Assert
        assertTrue(result.containsKey(0))
        assertTrue(result.containsKey(1))
        assertFalse(result.containsKey(2))
        assertFalse(result.containsKey(3))
        assertTrue(result.containsKey(4))
        assertFalse(result.containsKey(5))
        assertTrue(result.containsKey(6))
        assertTrue(result.containsKey(7))

        assertEquals(BigInteger.valueOf(7), result[0])
        assertEquals(BigInteger.valueOf(13), result[1])
        assertEquals(BigInteger.valueOf(59), result[4])
        assertEquals(BigInteger.valueOf(31), result[6])
        assertEquals(BigInteger.valueOf(19), result[7])
    }

    @ParameterizedTest
    @CsvSource(
        "1068781, true",
        "1068788, false",
        "10681, false",
        "1068782, false",
        "1068780, false",
    )
    fun testIsGoldenTimestamp(timestamp: Long, expected: Boolean) {
        // Arrange
        val buses = parseBusIdsWithIndex("7,13,x,x,59,x,31,19")

        // Act
        val result = isGoldenTimestamp(BigInteger.valueOf(timestamp), buses)

        // Assert
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @CsvSource(
        "7,13,x,x,59,x,31,19|1068781",
        "17,x,13,19|3417",
        "67,7,59,61|754018",
        "67,x,7,59,61|779210",
        "67,7,x,59,61|1261476",
        "1789,37,47,1889|1202161486",
        delimiter = '|'
    )
    fun testBruteForce(inputs: String, expected: Long) {
        // Arrange
        val buses = parseBusIdsWithIndex(inputs)
        val steppingWithBus = BusEntry(BigInteger.ZERO, buses[0]!!)

        // Act
        val result = puzzle2BruteForcing(BigInteger.ZERO, steppingWithBus, buses)

        // Assert
        assertEquals(BigInteger.valueOf(expected), result)
    }

    @Test
    fun testPuzzle2Answer() {
        // Act
        val result = Day13Puzzle2.calculateAnswer("day13-test.txt", BigInteger("1000000"))

        // Assert
        assertEquals(BigInteger.valueOf(1068781), result)
    }
}
