package org.tnl.aoc2020

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day16Test {

    @Test
    fun testParseTicketRule() {
        // Act
        val (fieldName, ranges) = parseTicketRule("class: 1-3 or 5-7")

        // Assert
        assertEquals("class", fieldName)
        assertEquals(2, ranges.size)
        val range1 = ranges[0]
        assertEquals(1, range1.start)
        assertEquals(3, range1.endInclusive)
        val range2 = ranges[1]
        assertEquals(5, range2.start)
        assertEquals(7, range2.endInclusive)
    }

    @Test
    fun testParseTicket() {
        // Act
        val result = parseTicket("7,1,14")

        // Assert
        assertEquals(listOf(7, 1, 14), result)
    }

    @Test
    fun testParseInputFile() {
        // Act
        val (ticketRules, ownTicket, nearbyTickets) = parseInputData("day16-test.txt")

        // Assert
        assertEquals(3, ticketRules.size)
        assertEquals(3, ownTicket.size)
        assertEquals(4, nearbyTickets.size)
    }

    @Test
    fun testFindAllTicketValuesInError() {
        // Arrange
        val (ticketRules, ownTicket, nearbyTickets) = parseInputData("day16-test.txt")

        // Act
        val result = findAllTicketValuesInError(ticketRules, nearbyTickets)

        // Assert
        assertEquals(listOf(4, 55, 12), result)
    }

    @Test
    fun testTicketErrorScanningRate() {
        // Arrange
        val (ticketRules, ownTicket, nearbyTickets) = parseInputData("day16-test.txt")

        // Act
        val result = calculateTicketScanningErrorRate(ticketRules, nearbyTickets)

        // Assert
        assertEquals(71, result)
    }
}
