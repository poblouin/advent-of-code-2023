package day02

import readInput
import readInputLines

data class Game(val id: Int, val rawRounds: List<String> = emptyList()) {
    companion object {
        const val MAX_RED = 12
        const val MAX_GREEN = 13
        const val MAX_BLUE = 14
    }

    val rounds: List<Round> = buildRounds()

    fun withinRules(): Boolean {
        return rounds.all { round ->
            round.blue <= MAX_BLUE && round.red <= MAX_RED && round.green <= MAX_GREEN
        }
    }

    private fun buildRounds(): List<Round> {
        return rawRounds.map {
            val pairs = "(\\d+)\\s(\\w+)".toRegex()
                .findAll(it)
                .map { it.groupValues[2] to it.groupValues[1].toInt() }.toList()

            Round(
                blue = pairs.find { it.first == "blue" }?.second ?: 0,
                red = pairs.find { it.first == "red" }?.second ?: 0,
                green = pairs.find { it.first == "green" }?.second ?: 0
            )
        }
    }
}

data class Round(var blue: Int = 0, var red: Int = 0, var green: Int = 0) {
}

fun part1(input: List<Game>): Int {
    return input.filter { it.withinRules() }.sumOf { it.id }
}

fun part2(input: List<String>): List<String> {
    return input
}

fun main(args: Array<String>) {
    val input = readInput(args[0])
        .split("\n")
        .map { it.split(":") }
        .map { Game("\\d+".toRegex().find(it[0])!!.value.toInt(), it[1].split(";")) }
    println(part1(input))
//    println(part2(input))
    check(part1(input) == 2256)
//    check(part2(input) == 52834)
}