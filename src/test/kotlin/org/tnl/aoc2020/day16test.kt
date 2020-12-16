package org.tnl.aoc2020

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

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
        val (ticketRules, ownTicket, nearbyTickets) = parseInputData("day16-test1.txt")

        // Assert
        assertEquals(3, ticketRules.size)
        assertEquals(3, ownTicket.size)
        assertEquals(4, nearbyTickets.size)
    }

    @Test
    fun testFindAllTicketValuesInError() {
        // Arrange
        val (ticketRules, _, nearbyTickets) = parseInputData("day16-test1.txt")

        // Act
        val result = findAllTicketValuesInError(ticketRules, nearbyTickets)

        // Assert
        assertEquals(listOf(4, 55, 12), result)
    }

    @Test
    fun testTicketErrorScanningRate() {
        // Arrange
        val (ticketRules, _, nearbyTickets) = parseInputData("day16-test1.txt")

        // Act
        val result = calculateTicketScanningErrorRate(ticketRules, nearbyTickets)

        // Assert
        assertEquals(71, result)
    }

    @Test
    fun testDiscardInvalidTickets() {
        // Arrange
        val (ticketRules, _, nearbyTickets) = parseInputData("day16-test1.txt")

        // Act
        val result = discardInvalidTickets(ticketRules, nearbyTickets)

        // Assert
        assertEquals(1, result.size)
        val ticket1 = result[0]
        assertEquals(listOf(7, 3, 47), ticket1)
    }

    @Test
    fun testTicketSlice() {
        // Arrange
        val (_, _, nearbyTickets) = parseInputData("day16-test2.txt")

        // Act
        val result = nearbyTickets.slice(0)

        // Arrange
        assertEquals(listOf(3,15,5), result)
    }

    @Test
    fun testIsRuleMatchingAllTicketsAtPos() {
        // Arrange
        val (ticketRules, _, nearbyTickets) = parseInputData("day16-test2.txt")

        // Act
        val result = isRuleMatchingAllTicketsAtPos(ticketRules["class"]!!, nearbyTickets, 1)

        // Assert
        assertTrue(result)
    }

    @Test
    fun testMapFieldsToPosition() {
        // Arrange
        val (ticketRules, ownTicket, nearbyTickets) = parseInputData("day16-test2.txt")

        // Act
        val result = mapFieldsToPosition(ticketRules, nearbyTickets)

        // Assert
        assertEquals(0, result["row"])
        assertEquals(1, result["class"])
        assertEquals(2, result["seat"])

        assertEquals(12, ownTicket[result["class"]!!])
        assertEquals(11, ownTicket[result["row"]!!])
        assertEquals(13, ownTicket[result["seat"]!!])
    }

    @Test
    fun testMapFieldsToPositionInFullFile() {
        // Arrange
        val (ticketRules, _, nearbyTickets) = parseInputData("day16-data.txt")
        val validatedTickets = discardInvalidTickets(ticketRules, nearbyTickets)

        // Act
        val result = mapFieldsToPosition(ticketRules, validatedTickets)

        // Assert
        assertEquals(result.size, result.values.toSet().size)
    }

    @Test
    fun testFindAllCandidatePositions() {
        // Arrange
        val (ticketRules, _, nearbyTickets) = parseInputData("day16-data.txt")
        val validatedTickets = discardInvalidTickets(ticketRules, nearbyTickets)

        // Act
        val result = findAllCandidatePositions(ticketRules, validatedTickets)

        println(result)
        // Assert
        assertEquals(result.size, result.values.toSet().size)
    }

    @Test
    fun testResolveCandidates() {
        // Arrange
        val (ticketRules, _, nearbyTickets) = parseInputData("day16-data.txt")
        val validatedTickets = discardInvalidTickets(ticketRules, nearbyTickets)
        val candidates = findAllCandidatePositions(ticketRules, validatedTickets)

        // Act
        val result = resolveCandidates(candidates)

        println(result)

        // Assert
        val (found, remaining) = result
        assertEquals(20, found.size)
        assertEquals(0, remaining.values.sumBy { it.size })
        assertEquals(20, found.values.toSet().size)
    }
}
