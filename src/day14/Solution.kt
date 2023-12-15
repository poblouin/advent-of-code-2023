package day14

import readInputLines

fun List<String>.rotateRight(): List<String> {
    return indices.map { c ->
        (lastIndex downTo 0).map { r -> this[r][c] }.joinToString("")
    }
}

fun List<String>.rotateLeft(): List<String> {
    return (lastIndex downTo 0).map { c ->
        indices.map { r -> this[r][c] }.joinToString("")
    }
}

fun List<String>.tilt(): List<String> {
    return this.map { it.split("(?<=#)".toRegex()).filterNot(String::isEmpty) }
        .map { list ->
            list.joinToString("") { str ->
                str.groupBy { it }.entries
                    .sortedByDescending { it.key == 'O' }
                    .joinToString("") { it.key.toString().repeat(it.value.size) }
            }
        }
    }

fun part1(input: List<String>): Int =
    input.rotateLeft().tilt().rotateRight()
        .mapIndexed { i, row -> row.count { it == 'O' } * (input[0].length - i) }.sum()

fun part2(input: List<String>): Int {
    return 0
}

fun main(args: Array<String>) {
    val input = readInputLines(args[0])

    check(part1(input) == 112048)
//    check(part2(input) == 52834)
}