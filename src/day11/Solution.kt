package day11

import readInputLines
import kotlin.math.abs

data class Coordinate(val x: Long, val y: Long) {
    fun distanceTo(other: Coordinate): Long {
        return abs(x - other.x) + abs(y - other.y)
    }
}

fun List<String>.transpose(): List<String> =
    (this[0].indices).map { col -> this.map { it[col] }.joinToString("") }

fun solve(input: List<String>, expand: Int = 2): Long {
    val emptyRows = input.mapIndexedNotNull { i, row -> if (row.all { it == '.' }) i else null }
    val emptyColumns = input.transpose().mapIndexedNotNull { i, col -> if (col.all { it == '.' }) i else null }

    return input
        .flatMapIndexed { i, row -> row.mapIndexedNotNull { j, char -> if (char == '#') Coordinate(i.toLong(), j.toLong()) else null } }
        .map {
            Coordinate(
                it.x + emptyRows.count { row -> row < it.x } * (expand - 1),
                it.y + emptyColumns.count { col -> col < it.y } * (expand - 1),
            )
        }.let {
            it.flatMapIndexed { i, galaxy -> it.drop(i + 1).map { galaxy to it } }.sumOf { (a, b) -> a.distanceTo(b) }
        }
}

fun main(args: Array<String>) {
    val input = readInputLines(args[0])

    check(solve(input) == 10289334L)
    check(solve(input, 1000000) == 649862989626L)
}
