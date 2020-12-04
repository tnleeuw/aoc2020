package org.tnl.aoc2020

import java.io.InputStream
import java.util.stream.Collectors

object Day1Puzzle1 {

    @JvmStatic
    fun main(args: Array<String>) {
        System.out.println("Product is: " + calculateResultFromInputFile("d1p1-data.txt"))
    }

    fun calculateResultFromInputFile(resourceName: String): Long {
        val (n1, n2) = findPair(getNumbersFromStream(getDataInputStream(resourceName)))
        return n1.toLong() * n2.toLong()
    }

    fun findPair(numbers: List<Int>, targetSum: Int = 2020): Pair<Int, Int> {

        numbers.forEachIndexed { index, candidate1 ->
            numbers.subList(index+1, numbers.size).forEach { candidate2 ->
                if (candidate1 + candidate2 == targetSum) {
                    return Pair(candidate1, candidate2)
                }
            }
        }

        throw IllegalArgumentException("None of the numbers in list '$numbers' sums up to $targetSum")
    }
}

object Day1Puzzle2 {

    @JvmStatic
    fun main(args: Array<String>) {
        System.out.println("Product is: " + calculateResultFromInputFile("d1p1-data.txt"))
    }

    fun calculateResultFromInputFile(resourceName: String): Long {
        val (n1, n2, n3) = findTriplet(getNumbersFromStream(getDataInputStream(resourceName)))
        return n1.toLong() * n2.toLong() * n3.toLong()
    }

    fun findTriplet(numbers: List<Int>, targetSum: Int = 2020): Triple<Int, Int, Int> {

        numbers.forEachIndexed { index1, candidate1 ->
            numbers.subList(index1 + 1, numbers.size).forEachIndexed { index2, candidate2 ->
                numbers.subList(index1 + index2 + 1, numbers.size).forEach { candidate3 ->
                    if (candidate1 + candidate2 + candidate3 == targetSum) {
                        return Triple(candidate1, candidate2, candidate3)
                    }
                }
            }
        }

        throw IllegalArgumentException("None of the numbers in list '$numbers' sums up to $targetSum")
    }
}

fun getNumbersFromStream(stream: InputStream): List<Int> {
    return stream.bufferedReader().lines().map { v -> v.toInt() }.collect(Collectors.toList())
}

fun getDataInputStream(resourceName: String): InputStream {
    return Day1Puzzle1::class.java.getResourceAsStream(resourceName)
}
