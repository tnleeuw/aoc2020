package org.tnl.aoc2020

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal class ParsingTest {

    @ParameterizedTest
    @CsvSource(
        "light red bags contain 1 bright white bag, 2 muted yellow bags.|light red|1 bright white bag, 2 muted yellow bags.",
        "bright white bags contain 1 shiny gold bag.|bright white|1 shiny gold bag.",
        delimiter = '|'
    )
    internal fun testParseTopRe(rule: String, expectedColour: String, expectedContains: String) {
        // Act
        val values = PARSE_RE_TOP.matchEntire(rule)?.groupValues

        // Assert
        assertNotNull(values)
        assertEquals(3, values.size)
        assertEquals(expectedColour, values[1])
        assertEquals(expectedContains, values[2])
    }

    @ParameterizedTest
    @CsvSource(
        "1 bright white bag, 2 muted yellow bags.|2|bright white|1",
        "1 shiny gold bag.|1|shiny gold|1",
        delimiter = '|'
    )
    internal fun testParseContainsRe(ruleContains: String, expectedSize: Int, expectedFirstColour: String, expectedFirstQuantity: String) {
        // Act
        val values = PARSE_RE_CONTAINS.findAll(ruleContains).map { it.groupValues }.toList()

        // Assert
        assertEquals(expectedSize, values.size)

        val match1 = values[0]
        assertEquals(expectedFirstQuantity, match1[1])
        assertEquals(expectedFirstColour, match1[2])
    }

    @ParameterizedTest
    @CsvSource(
        "1 bright white bag, 2 muted yellow bags.|false",
        "1 shiny gold bag.|false",
        "no other bags.|true",
        delimiter = '|'
    )
    internal fun testParseNoOtherRe(ruleContains: String, expectedMatch: Boolean) {
        // Act
        val result = PARSE_RE_NO_OTHER.containsMatchIn(ruleContains)

        // Assert
        assertEquals(expectedMatch, result)
    }

    @ParameterizedTest
    @CsvSource(
        "light red bags contain 1 bright white bag, 2 muted yellow bags.|light red|2",
        "bright white bags contain 1 shiny gold bag.|bright white|1",
        "faded blue bags contain no other bags.|faded blue|0",
        delimiter = '|'
    )
    internal fun testParseRule_ColourAndNrRules(rule: String, expectedColour: String, expectedNrRules: Int) {
        // Act
        val bagRule = parseRule(rule)

        // Assert
        assertEquals(expectedColour, bagRule.bagColour)
        assertEquals(expectedNrRules, bagRule.containRules.size)
    }

    @ParameterizedTest
    @CsvSource(
        "day7p1-test.txt,9",
        "day7-data.txt,594"
    )
    internal fun testParseRules() {
        // Act
        val rules = parseAllRules(fileToLines("day7p1-test.txt"))

        // Assert
        assertEquals(9, rules.size)
    }

    @Test
    internal fun testReverseRuleMap() {
        // Arrange
        val rules = parseAllRules(fileToLines("day7p1-test.txt"))

        // Act
        val reverseMap = reverseRuleMap(rules)

        // Assert
        assertEquals(7, reverseMap.size)
        assertTrue("shiny gold" in reverseMap)
        val goldIsIn = reverseMap["shiny gold"]
        assertNotNull(goldIsIn)
        assertEquals(2, goldIsIn.size)
        assertTrue("bright white" in goldIsIn)
        assertTrue("muted yellow" in goldIsIn)
    }

    @Test
    internal fun testFindPossibleOuterBags() {
        // Arrange
        val rules = parseAllRules(fileToLines("day7p1-test.txt"))
        val reverseMap = reverseRuleMap(rules)

        // Act
        val result = findAllPossibleOuterBagsFor("shiny gold", reverseMap)

        // Assert
        assertEquals(4, result.size)

        assertTrue("bright white" in result)
        assertTrue("muted yellow" in result)
        assertTrue("dark orange" in result)
        assertTrue("light red" in result)
    }

    @Test
    internal fun testFindNrOfPossibleOuterBags() {
        // Act
        val result = Day7Puzzle1.findNrOfBagColours("day7p1-test.txt", "shiny gold")

        // Assert
        assertEquals(4, result)
    }

    @ParameterizedTest
    @CsvSource(
        "day7p1-test.txt,32",
        "day7p2-test.txt,126",
    )
    internal fun testCountContainedBags(fileName: String, expected: Int) {
        // Arrange
        val rules = parseAllRules(fileToLines(fileName))

        // Act
        val result = countBagsContainedIn("shiny gold", rules)

        // Assert
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @CsvSource(
        "day7p1-test.txt,32",
        "day7p2-test.txt,126",
    )
    internal fun testCountContainedBags2(fileName: String, expected: Int) {
        // Act
        val result = Day7Puzzle2.countBagsContainedIn(fileName, "shiny gold")

        // Assert
        assertEquals(expected, result)
    }
}
