package org.tnl.aoc2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.streams.asSequence

internal class ReadPassportTest {

    @ParameterizedTest
    @CsvSource(
        "x, x, false",
        "byr, x, true",
        "iyr, x, true",
        "eyr, x, true",
        "hgt, x, true",
        "hcl, x, true",
        "ecl, x, true",
        "pid, x, true",
        "cid, x, true",
    )
    internal fun testIsValidField(field: String, value: String, expected: Boolean) {
        // Act
        val result = isValidField(field, value)

        // Assert
        assertEquals(expected, result)
    }

    internal fun testExtractionRe() {
        // Arrange
        val testLine = "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd"

        // Act
        val matches = PASSPORT_FIELD_RE.findAll(testLine)
        val allMatches = matches.map { it.groupValues }.toList()

        // Assert
        assertEquals(4, allMatches.size)
    }

    @ParameterizedTest
    @CsvSource(
        "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd, 4, eyr",
        "hcl:#cfa07d byr:1929, 2, byr"
    )
    internal fun testExtractFieldsFromLine(line: String, expectedNrOfFields: Int, fieldToCheck: String) {
        // Arrange
        val passport = mutableMapOf<String, String>()

        // Act
        extractFieldsFromLine(passport, line, ::isValidField)

        // Assert
        assertEquals(expectedNrOfFields, passport.size)
        assertTrue(passport.containsKey(fieldToCheck))
    }

    @Test
    internal fun testReadPassports() {
        // Arrange
        val lines = getDataInputStream("day4-test.txt").bufferedReader().lines().asSequence()

        // Act
        val passports = readPassports(lines)

        // Assert
        assertEquals(4, passports.size)
    }

    @ParameterizedTest
    @CsvSource(
        "0, true",
        "1, false",
        "2, true",
        "3, false",
    )
    internal fun testValidPassports(passportNr: Int, expected: Boolean) {
        // Arrange
        val passports = readPassports("day4-test.txt")

        // Act
        val result = passports[passportNr].isValidForPuzzle1()

        // Assert
        assertEquals(expected, result)
    }

    @Test
    internal fun testCountValidPassports() {
        // Arrange
        val passports = readPassports("day4-test.txt")

        // Act
        val nrValid = Day4Puzzle1.countValidPassports(passports)

        // Assert
        assertEquals(2, nrValid)
    }
}
