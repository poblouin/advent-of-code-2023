package day06

import readInputLines

fun part1(input: List<String>): Int {
    val parsed = input.map { "\\d+".toRegex().findAll(it).toList() }.map { it.map { it.value.toLong() } }
    return parsed[0].mapIndexed { i, ms -> racePossibilities(ms, parsed[1][i]) }.reduce(Int::times)
}

fun part2(input: List<String>): Int {
    val (time, distance) = input.map { it.substringAfter(":").replace(" ", "").toLong() }
    return racePossibilities(time, distance)
}

private fun racePossibilities(time: Long, distance: Long): Int {
    return (0..time).count {
        (time - it) * it > distance
    }
}

fun main(args: Array<String>) {
    val input = readInputLines(args[0])

    check(part1(input) == 6209190)
    check(part2(input) == 28545089)
}
