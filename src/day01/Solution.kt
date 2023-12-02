package day01

import readInputLines

fun part1(input: List<String>): Int {
    return input
        .map { it.filter(Char::isDigit) }
        .sumOf { "${it.first()}${it.last()}".toInt() }
}

fun String.toDigit(): Int {
    return when (this) {
        "zero" -> 0
        "one" -> 1
        "two" -> 2
        "three" -> 3
        "four" -> 4
        "five" -> 5
        "six" -> 6
        "seven" -> 7
        "eight" -> 8
        "nine" -> 9
        else -> this.toInt()
    }
}

fun part2(input: List<String>): Int {
    val regex = Regex("[0-9]|one|two|three|four|five|six|seven|eight|nine")
    return input
        .sumOf { l -> regex.findAll(l).map { it.value.toDigit() }.let { d -> "${d.first()}${d.last()}".toInt() } }
}

fun main(args: Array<String>) {
    val input = readInputLines(args[0])
    check(part1(input) == 53334)
    check(part2(input) == 52834)
}