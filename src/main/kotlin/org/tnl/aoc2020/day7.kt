package org.tnl.aoc2020

object Day7Puzzle1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val nrOfColours = findNrOfBagColours("day7-data.txt", "shiny gold")
        println("Your shiny gold bag can be contained in $nrOfColours outer bags")
    }

    fun findNrOfBagColours(fileName: String, forColour: String): Int {
        val rules = parseAllRules(fileToLines(fileName))
        val reverseMap = reverseRuleMap(rules)

        val result = findAllPossibleOuterBagsFor(forColour, reverseMap)
        return result.size
    }
}
class BagRule(val bagColour: String, val containRules: List<ContainRule> = listOf())

class ContainRule(val bagColour: String, val quantity: Int)

val PARSE_RE_TOP = "(\\w+\\s+\\w+)\\s+bags\\s+contain\\s+(.*)".toRegex()
val PARSE_RE_CONTAINS = "\\s*(\\d+)\\s+(\\w+\\s+\\w+)\\s+bag[s,. ]*".toRegex()
val PARSE_RE_NO_OTHER = "\\s*no other bag[s.]*".toRegex()
fun parseRule(rule: String): BagRule {

    val (_, bagColour, containRules) = PARSE_RE_TOP.matchEntire(rule)?.groupValues
        ?: throw IllegalArgumentException("No valid data contained in rule: '$rule'")

    if (PARSE_RE_NO_OTHER.containsMatchIn(containRules)) {
        return BagRule(bagColour)
    }

    val containRuleList = PARSE_RE_CONTAINS.findAll(containRules)
        .map { it.groupValues }
        .map { ContainRule(it[2], it[1].toInt()) }
        .toList()
    return BagRule(bagColour, containRuleList)
}

fun parseAllRules(rules: Sequence<String>): Map<String, BagRule> =
    rules.map { parseRule(it) }.associateBy { it.bagColour }

fun reverseRuleMap(rules: Map<String, BagRule>): Map<String, List<String>> = rules.values.map { bagRule ->
    bagRule.containRules.map { contained ->
        Pair(contained.bagColour, bagRule.bagColour) } }
    .flatten()
    .groupBy(Pair<String, String>::first, Pair<String, String>::second)

fun findAllPossibleOuterBagsFor(bagColour: String, reverseMap: Map<String, List<String>>): Set<String> {
    val canBeIn = reverseMap[bagColour]?.toSet() ?: setOf<String>()

    return canBeIn.plus(canBeIn
        .map { findAllPossibleOuterBagsFor(it, reverseMap) }
        .flatten())
}
