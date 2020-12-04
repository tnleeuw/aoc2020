package org.tnl.aoc2020

import kotlin.streams.asSequence

object Day4Puzzle1 {

    @JvmStatic
    fun main(args: Array<String>) {
        val passports = readPassports("day4-data.txt")
        val nrValid = countValidPassports(passports)

        println("Nr of valid passports: $nrValid")
    }

    fun readPassports(fileName: String): List<Passport> =
        readPassports(fileName, ::isValidFieldPuzzle1)

}

object Day4Puzzle2 {

    @JvmStatic
    fun main(args: Array<String>) {
        val passports = readPassports("day4-data.txt")
        val nrValid = countValidPassports(passports)

        println("Nr of valid passports: $nrValid")
    }

    fun readPassports(fileName: String): List<Passport> =
        readPassports(fileName, ::isValidFieldPuzzle2)

}

typealias Passport = Map<String, String>

fun countValidPassports(passports: List<Passport>): Int {
    return passports.count { it.isValidForPuzzle1() }
}

fun readPassports(fileName: String, isValidField: (String, String) -> Boolean): List<Passport> {
    return readPassports(getDataInputStream(fileName)
        .bufferedReader()
        .lines().asSequence(), isValidField)
}

fun readPassports(lines: Sequence<String>, isValidField: (String, String) -> Boolean): List<Passport> {
    val sequence = sequence<Passport> {
        var passport = mutableMapOf<String, String>()
        lines.forEach {  line ->
            if (line.isBlank()) {
                yield(passport)
                passport = mutableMapOf()
            } else {
                extractFieldsFromLine(passport, line, isValidField)
            }
        }
        yield(passport)
    }
    return sequence.filter { it.isNotEmpty() }.toList()
}

val VALID_FIELDS = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid")
fun isValidFieldPuzzle1(field: String, value: String): Boolean {
    return field in VALID_FIELDS
}

val VALID_YEAR_RE = "\\d{4}".toRegex()
val VALID_HEX_COLOR = "#[a-f0-9]{6}".toRegex()
val VALID_PID = "\\d{9}".toRegex()
val VALID_HEIGHT_RE = "(\\d+)(cm|in)".toRegex()
val VALID_EYE_COLOURS = setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
fun isValidYear(value: String, min: Int, max: Int): Boolean {
    return VALID_YEAR_RE.matches(value) && value.toInt() in min..max
}

fun isValidHeight(value: String): Boolean {
    val matchResult = VALID_HEIGHT_RE.matchEntire(value) ?: return false

    val (_, height, units) = matchResult.groupValues
    return when(units) {
        "cm" -> height.toInt() in 150..193
        "in" -> height.toInt() in 59..76
        else -> false
    }
}

val FIELD_VALIDATIONS = mapOf(
    "byr" to { value: String -> isValidYear(value, 1920, 2002) },
    "iyr" to { value: String -> isValidYear(value, 2010, 2020) },
    "eyr" to { value: String -> isValidYear(value, 2020, 2030) },
    "hgt" to { value: String -> isValidHeight(value) },
    "hcl" to { value: String -> VALID_HEX_COLOR.matches(value) },
    "ecl" to { value: String -> value in VALID_EYE_COLOURS },
    "pid" to { value: String -> VALID_PID.matches(value) },
    "cid" to { value: String -> true },
)
fun isValidFieldPuzzle2(field: String, value: String): Boolean {
    val validator = FIELD_VALIDATIONS[field] ?: return false
    return validator(value)
}

fun Passport.isValidForPuzzle1(): Boolean {
    return size == 8
            || (size == 7 && !containsKey("cid"))
}

val PASSPORT_FIELD_RE = "([a-z]{3}:[#a-zA-Z0-9]+)\\s*".toRegex()
internal fun extractFieldsFromLine(
    passport: MutableMap<String, String>,
    line: String,
    isValidField: (String, String) -> Boolean
) {
    PASSPORT_FIELD_RE.findAll(line).forEach { matchResult ->
        matchResult.groupValues.forEach { entry ->
            val (field, value) = entry.trim().split(":")
            if (isValidField(field, value)) {
                passport.put(field, value)
            }
        }
    }
}
