package day09

import readInputLines

fun solve(input: List<List<Int>>): Int {
    return input.sumOf { sequence ->
        generateSequence(sequence) { it.zipWithNext { a, b -> b - a } }
            .takeWhile { it.any { it != 0 } }
            .sumOf { it.last() }
    }
}

fun main(args: Array<String>) {
    val input = readInputLines(args[0]).map { it.split(" ").map { it.toInt() } }

    check(solve(input) == 2098530125)
    check(solve(input.map { it.reversed() }) == 1016)
}