package day13

import readInput
import kotlin.math.abs

fun constructRange(initial: Int, final: Int): List<Pair<Int, Int>> =
    (initial downTo 0).zip(initial + 1..final)

private fun String.compareStr(otherStr: String): Int =
    indices.count { this[it] != otherStr[it] } + abs(length - otherStr.length)

private fun List<String>.stringCol(column: Int): String =
    map { it[column] }.joinToString("")

private fun calculateDifferenceInRow(grid: List<String>, startRow: Int, checkValue: Int): Int? {
    val totalVal = constructRange(startRow, grid.lastIndex).sumOf { (value1, value2) -> grid[value1].compareStr(grid[value2]) }
    return if (totalVal == checkValue) (startRow + 1) * 100 else null
}

private fun calculateDifferenceInColumn(grid: List<String>, startColumn: Int, checkValue: Int): Int? {
    val totalVal = constructRange(startColumn, grid.first().lastIndex).sumOf {(value1, value2) -> grid.stringCol(value1).compareStr(grid.stringCol(value2)) }
    return if (totalVal == checkValue) startColumn + 1 else null
}


fun solve(inputGrids: List<List<String>>, checkValue: Int): Int {
    return inputGrids.sumOf { grid ->
        (0..< grid.lastIndex).firstNotNullOfOrNull { startRow -> calculateDifferenceInRow(grid, startRow, checkValue) }
            ?: (0..< grid.first().lastIndex).firstNotNullOfOrNull { startColumn -> calculateDifferenceInColumn(grid, startColumn, checkValue) }
            ?: 0
    }
}

fun main(args: Array<String>) {
    val input = readInput(args[0]).split("\n\n").map { it.split("\n") }

    check(solve(input, 0) == 29213)
    check(solve(input, 1) == 37453)
}