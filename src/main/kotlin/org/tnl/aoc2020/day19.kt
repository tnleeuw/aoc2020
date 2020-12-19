package org.tnl.aoc2020

object Day19Puzzle1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val answer = puzzle1("day19-data.txt")
        println("Answer puzzle1 = $answer")
    }

    fun puzzle1(fileName: String): Int {
        val (ruleBase, messages) = parseRulesAndMessages(fileName)
        return matchingMessages(messages, ruleBase).size
    }
}


sealed class Rule {
    class ExpandingRule(val expansions: List<Int>) : Rule() {
        companion object {
            fun of(ruleData: String) = ExpandingRule(ruleData.trim().split(' ').map { it.toInt() })
        }
    }
    class TerminalRule(val chr: Char) : Rule()
    class OptionRule(val options: List<ExpandingRule>) : Rule()
}

typealias RuleBase=Map<Int, Rule>
typealias RuleEntry=Pair<Int, Rule>
fun parseMessageMatchRule(line: String): RuleEntry {
    val (idx, ruleData) = line.split(":")
    val rule = when {
        ruleData.contains('"') -> Rule.TerminalRule(ruleData.trim(' ', '"')[0])
        ruleData.contains('|') -> Rule.OptionRule(ruleData.split('|').map { Rule.ExpandingRule.of(it) })
        else -> Rule.ExpandingRule.of(ruleData)
    }
    return Pair(idx.toInt(), rule)
}

fun expandRule(rule: Rule, ruleBase: RuleBase): String = when(rule) {
    is Rule.TerminalRule -> rule.chr.toString()
    is Rule.ExpandingRule -> {
        rule.expansions.mapNotNull { ruleBase[it] }
            .joinToString(separator = "") { expandRule(it, ruleBase) }
    }
    is Rule.OptionRule -> {
        rule.options.joinToString(
            separator = "|",
            prefix = "(", postfix = ")"
        ) { expandRule(it, ruleBase) }
    }
}

fun parseRulesAndMessages(fileName: String): Pair<RuleBase, List<String>> {
    val rules = mutableListOf<RuleEntry>()
    val messages = mutableListOf<String>()

    var readingRules = true
    getDataInputStream(fileName).bufferedReader().lines().forEach { line ->
        if (line.isBlank()) {
            readingRules = false
        } else if (readingRules) {
            rules.add(parseMessageMatchRule(line))
        } else {
            messages.add(line)
        }
    }

    return Pair(mapOf(*rules.toTypedArray()), messages)
}

fun matchingMessages(messages: List<String>, ruleBase: RuleBase): List<String> {
    val ruleRE = expandRule(ruleBase[0]!!, ruleBase).toRegex()
    return messages.filter { ruleRE.matchEntire(it) != null }
}
