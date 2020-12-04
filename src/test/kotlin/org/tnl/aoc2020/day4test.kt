package org.tnl.aoc2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
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
        val result = isValidFieldPuzzle1(field, value)

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
        extractFieldsFromLine(passport, line, ::isValidFieldPuzzle1)

        // Assert
        assertEquals(expectedNrOfFields, passport.size)
        assertTrue(passport.containsKey(fieldToCheck))
    }

    @Test
    internal fun testReadPassports() {
        // Arrange
        val lines = getDataInputStream("day4-test.txt").bufferedReader().lines().asSequence()

        // Act
        val passports = readPassports(lines, ::isValidFieldPuzzle1)

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
        val passports = Day4Puzzle1.readPassports("day4-test.txt")

        // Act
        val result = passports[passportNr].isValidForPuzzle1()

        // Assert
        assertEquals(expected, result)
    }

    @Test
    internal fun testCountValidPassports() {
        // Arrange
        val passports = Day4Puzzle1.readPassports("day4-test.txt")

        // Act
        val nrValid = countValidPassports(passports)

        // Assert
        assertEquals(2, nrValid)
    }
}

internal class Puzzle2FieldValidationsTest {

    @DisplayName("{index} {0} value {1} expected: {2}")
    @ParameterizedTest(name = "{index} {0} value {1} expected: {2}")
    @CsvSource(
        "byr, 2002, true",
        "byr, 2003, false",
        "hgt, 60in, true",
        "hgt, 190cm, true",
        "hgt, 190in, false",
        "hgt, 190, false",
        "hcl, #123abc, true",
        "hcl, #123abz, false",
        "ecl, brn, true",
        "ecl, wat, false",
        "pid, 000000001, true",
        "pid, 0123456789, false",
        "cid, wat, true",
        "byr, 1919, false",
        "byr, 1920, true",
        "iyr, 2009, false",
        "iyr, 2010, true",
        "iyr, 2020, true",
        "iyr, 2021, false",
        "eyr, 2019, false",
        "eyr, 2020, true",
        "eyr, 2030, true",
        "eyr, 2031, false",
        "ecl, amb, true",
        "ecl, blu, true",
        "ecl, gry, true",
        "ecl, grn, true",
        "ecl, hzl, true",
        "ecl, oth, true",
        "hgt, 6ft, false",
        "hgt, 149cm, false",
        "hgt, 150cm, true",
        "hgt, 193cm, true",
        "hgt, 194cm, false",
        "hgt, 58in, false",
        "hgt, 59in, true",
        "hgt, 76in, true",
        "hgt, 77in, false",
        "hcl, 123456, false",
        "hcl, #abc, false",
        "pid, 1234, false",
        "kwd, val, false",
    )
    internal fun testFieldValidations(field: String, value: String, expected: Boolean) {
        // Act
        val result = isValidFieldPuzzle2(field, value)

        // Assert
        assertEquals(expected, result) {
            "Field '$field' value '$value' should match: $expected"
        }
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
        extractFieldsFromLine(passport, line, ::isValidFieldPuzzle2)

        // Assert
        assertEquals(expectedNrOfFields, passport.size)
        assertTrue(passport.containsKey(fieldToCheck))
    }


    @ParameterizedTest
    @CsvSource(
        "day4-test.txt, 2",
        "day4p2-invalidpassports.txt, 0",
        "day4p2-validpassports.txt, 4"
    )
    internal fun testCountValidPassports(fileName: String, expected: Int) {
        // Act
        val passports = Day4Puzzle2.readPassports(fileName)
        val result = countValidPassports(passports)

        // Assert
        assertEquals(expected, result)
    }
}
