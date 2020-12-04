package org.tnl.aoc2020

import kotlin.streams.asSequence

object Day4Puzzle1 {

    @JvmStatic
    fun main(args: Array<String>) {
        val passports = readPassports("day4-data.txt")
        val nrValid = countValidPassports(passports)

        println("Nr of valid passports: $nrValid")
    }

    fun countValidPassports(passports: List<Passport>): Int {
        return passports.count { it.isValidForPuzzle1() }
    }
}

typealias Passport = Map<String, String>

fun readPassports(fileName: String): List<Passport> {
    return readPassports(getDataInputStream(fileName)
        .bufferedReader()
        .lines().asSequence())
}

fun readPassports(lines: Sequence<String>, isValidField: (String, String) -> Boolean = ::isValidField): List<Passport> {
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
fun isValidField(field: String, value: String): Boolean {
    return field in VALID_FIELDS
}

fun Passport.isValidForPuzzle1(): Boolean {
    return size == 8
            || (size == 7 && !containsKey("cid"))
}

val PASSPORT_FIELD_RE = "([a-z]{3}:.+?)\\s*".toRegex()
internal fun extractFieldsFromLine(
    passport: MutableMap<String, String>,
    line: String,
    isValidField: (String, String) -> Boolean
) {
    PASSPORT_FIELD_RE.findAll(line).forEach { matchResult ->
        matchResult.groupValues.forEach { entry ->
            val (field, value) = entry.split(":")
            if (isValidField(field, value)) {
                passport.put(field, value)
            }
        }
    }
}
