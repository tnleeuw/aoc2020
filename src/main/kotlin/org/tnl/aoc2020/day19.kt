package org.tnl.aoc2020

object Day19Puzzle1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val answer1 = puzzle1("day19p1-data.txt")
        println("Answer puzzle1 = $answer1")

        val answer2 = puzzle1("day19p2-data.txt")
        println("Answer puzzle2 = $answer2")
    }

    fun puzzle1(fileName: String): Int {
        val (ruleBase, messages) = parseRulesAndMessages(fileName)
        return matchingMessages(messages, ruleBase).size
    }
}


sealed class Rule(val idx: Int) {
    class TerminalRule(idx: Int, val chr: Char) : Rule(idx)
    class ExpandingRule(idx: Int, val expansions: List<Int>) : Rule(idx) {
        companion object {
            fun of(idx: Int, ruleData: String) = ExpandingRule(idx, ruleData.trim().split(' ').map { it.toInt() })
        }

        override fun toString(): String {
            return "$idx: ${expansions.joinToString(separator = " ")}"
        }
    }
    class OptionRule(idx: Int, val options: List<ExpandingRule>) : Rule(idx) {
        val isRecursive: Boolean = options.any { it.expansions.contains(idx) }
        override fun toString(): String {
            return "$idx: ${options.map { it.expansions.joinToString(separator = " ") }.joinToString(separator = "|")}"
        }
    }
}

typealias RuleBase=Map<Int, Rule>
typealias RuleEntry=Pair<Int, Rule>
fun parseMessageMatchRule(line: String): RuleEntry {
    val (idxStr, ruleData) = line.split(":")
    val idx = idxStr.toInt()
    val rule = when {
        ruleData.contains('"') -> Rule.TerminalRule(idx, ruleData.trim(' ', '"')[0])
        ruleData.contains('|') -> Rule.OptionRule(idx, ruleData.split('|')
            .map { Rule.ExpandingRule.of(-1, it) })
        else -> Rule.ExpandingRule.of(idx, ruleData)
    }
    return Pair(idx, rule)
}

fun expandRule(rule: Rule, ruleBase: RuleBase): String {
    val result = when (rule) {
        is Rule.TerminalRule -> rule.chr.toString()
        is Rule.ExpandingRule -> {
            rule.expansions.mapNotNull { ruleBase[it] }
                .joinToString(separator = "") { expandRule(it, ruleBase) }
        }
        is Rule.OptionRule -> {
            if (rule.isRecursive) {
                // Use special knowledge about the recursive rules: The rules
                // we have, they loop their first terms
                rule.options[0].expansions
                    .mapNotNull { ruleBase[it] }
                    .map { expandRule(it, ruleBase) }
                    .joinToString(
                        separator = "",
                        prefix = "(", postfix = ")"
                    ) { "($it)+?" }
            } else {
                rule.options.joinToString(
                    separator = "|",
                    prefix = "(", postfix = ")"
                ) { expandRule(it, ruleBase) }
            }
        }
    }
    if ((rule is Rule.ExpandingRule && rule.idx != -1) || rule is Rule.OptionRule)
        println("Expansion: $rule -> $result")
    return result
}

fun parseRulesAndMessages(fileName: String): Pair<RuleBase, List<String>> {
    val rules = mutableListOf<RuleEntry>()
    val messages = mutableListOf<String>()

    var readingRules = true
    getDataInputStream(fileName).bufferedReader().lines().forEach { line ->
        when {
            line.isBlank() -> readingRules = false
            readingRules -> rules.add(parseMessageMatchRule(line))
            else -> messages.add(line)
        }
    }

    return Pair(mapOf(*rules.toTypedArray()), messages)
}

fun matchingMessages(messages: List<String>, ruleBase: RuleBase): List<String> {
    val ruleRE = ("^${expandRule(ruleBase[0]!!, ruleBase)}$").toRegex()
    println("Rule RE: ${ruleRE.pattern}")
    return messages.filter { ruleRE.matches(it) }
}
