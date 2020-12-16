package org.tnl.aoc2020

import java.lang.IllegalArgumentException


object Day16Puzzle1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val (ticketRules, ownTicket, nearbyTickets) = parseInputData("day16-data.txt")
        val result = calculateTicketScanningErrorRate(ticketRules, nearbyTickets)
        println("And the answer is: $result")
    }
}
typealias TicketRules=Map<String, List<IntRange>>
typealias Ticket=List<Int>

enum class ParseState {FIELDS, OWN_TICKET_HEADER, OWN_TICKET, OTHER_TICKETS_HEADER, OTHER_TICKETS, EOF}

fun parseInputData(fileName: String): Triple<TicketRules, Ticket, List<Ticket>> {

    fun nextParseState(parseState: ParseState): ParseState =
        when(parseState) {
            ParseState.FIELDS -> ParseState.OWN_TICKET_HEADER
            ParseState.OWN_TICKET_HEADER -> ParseState.OWN_TICKET
            ParseState.OWN_TICKET -> ParseState.OTHER_TICKETS_HEADER
            ParseState.OTHER_TICKETS_HEADER -> ParseState.OTHER_TICKETS
            ParseState.OTHER_TICKETS -> ParseState.EOF
            ParseState.EOF -> throw IllegalArgumentException("End Reached")
        }

    val lines = getDataInputStream(fileName).bufferedReader()
        .lines()

    val ticketRules: MutableList<Pair<String, List<IntRange>>> = mutableListOf()
    var ownTicket: Ticket? = null
    val nearbyTickets: MutableList<Ticket> = mutableListOf()
    var parseState = ParseState.FIELDS
    lines.forEach { line ->
        if (line.isBlank()) {
            parseState = nextParseState(parseState)
        } else {
            when(parseState) {
                ParseState.FIELDS -> ticketRules.add(parseTicketRule(line))
                ParseState.OWN_TICKET_HEADER -> {
                    require(line == "your ticket:")
                    parseState = nextParseState(parseState)
                }
                ParseState.OWN_TICKET -> ownTicket = parseTicket(line)
                ParseState.OTHER_TICKETS_HEADER -> {
                    require(line == "nearby tickets:")
                    parseState = nextParseState(parseState)
                }
                ParseState.OTHER_TICKETS -> nearbyTickets.add(parseTicket(line))
                ParseState.EOF -> return@forEach
            }
        }
    }
    return Triple(ticketRules.associateBy({ it.first }, { it.second }),
        ownTicket!!, nearbyTickets)
}

fun <T> Sequence<T>.takeNext(): T =
    take(1).first()

internal val TICkET_RULE_RANGE_RE = "(\\d+)-(\\d+)".toRegex()
fun parseTicketRule(line: String): Pair<String, List<IntRange>> =
    Pair(line.substringBefore(':'),
        TICkET_RULE_RANGE_RE.findAll(line)
            .map { match -> match.groupValues }
            .map { values ->
                IntRange(values[1].toInt(), values[2].toInt())
        }.toList()
    )

fun parseTicket(line: String): List<Int> =
    line.split(',').map { it.toInt() }


fun findAllTicketValuesInError(ticketRules: TicketRules, tickets: List<Ticket>): List<Int> {
    val allTicketRanges = ticketRules.allTicketFieldRanges()
    return tickets
        .map { ticket -> ticket.filter { fv ->  !allTicketRanges.any { r -> fv in r }} }
        .filter { it.isNotEmpty() }
        .flatten()
}

fun TicketRules.allTicketFieldRanges(): List<IntRange> =
    values.flatten()

fun calculateTicketScanningErrorRate(ticketRules: TicketRules, tickets: List<Ticket>): Int =
    findAllTicketValuesInError(ticketRules, tickets).sum()
