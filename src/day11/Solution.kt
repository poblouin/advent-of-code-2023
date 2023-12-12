package day11

import readInputLines
import kotlin.math.abs

data class Coordinate(val x: Int, val y: Int) {
    fun distanceTo(other: Coordinate): Int {
        return abs(x - other.x) + abs(y - other.y)
    }
}

fun part1(galaxies: List<Coordinate>): Int {
    val pairs = galaxies
        .flatMap { galaxy -> galaxies.filter { it != galaxy }.map { galaxy to it } }
    println(pairs)
    return 0
}

fun part2(input: List<String>): Int {
    return 0
}

fun main(args: Array<String>) {
    val input = readInputLines(args[0])
        .flatMap { line -> if (line.all { it == '.' }) listOf(line, line) else listOf(line) }
        .let { rows ->
            val columns = rows[0].indices.map { col -> rows.map { it[col] } }
            columns.flatMap { col -> if (col.all { it == '.' }) listOf(col, col) else listOf(col) }
        }
        .let { (0 ..< it[0].size).map { row -> it.map { it[row] }.joinToString("") } }

    val galaxies = input.flatMapIndexed { i, row -> row.mapIndexedNotNull { j, col -> if (col == '#') Coordinate(i, j) else null } }

    println(part1(galaxies))
//    println(part2(input))
//    check(part1(input) == 53334)
//    check(part2(input) == 52834)
}