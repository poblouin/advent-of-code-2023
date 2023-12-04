package day04

import readInputLines

fun part1(input: List<List<Set<Int>>>): Int {
    return input
        .map { (first, second) -> first intersect second }
        .filter { it.isNotEmpty() }
        .sumOf {
            when (it.size) {
                1 -> 1
                else -> 1.shl(it.size - 1)
            }
        }
}

fun part2(input: List<List<Set<Int>>>): Int {
    return input.foldIndexed(Array(input.size) { 1 }) { index, acc, (first, second) ->
        (first intersect second).forEachIndexed { i, _ -> acc[index + i + 1] += acc[index] }
        acc
    }.sum()
}

fun main(args: Array<String>) {
    val input = readInputLines(args[0])
        .flatMap { it.split(':').drop(1) }
        .map { it.split('|') }
        .map { it.map { "\\d+".toRegex().findAll(it).map { it.value.toInt() }.toSet() } }

    check(part1(input) == 21138)
    check(part2(input) == 7185540)
}
