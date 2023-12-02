package day02

import readInput

data class Game(val id: Int) {
    companion object {
        const val MAX_RED = 12
        const val MAX_GREEN = 13
        const val MAX_BLUE = 14
    }

    private var rounds: List<Round> = emptyList()

    fun withinRules(): Boolean {
        return rounds.all { it.blue <= MAX_BLUE && it.red <= MAX_RED && it.green <= MAX_GREEN }
    }

    fun findMaxEachColorAcrossRounds(): List<Int> {
        return listOf(
            rounds.maxOf { it.blue },
            rounds.maxOf { it.red },
            rounds.maxOf { it.green },
        )
    }

    fun buildRounds(rawRounds: List<String>): Game {
        rounds = rawRounds.map {
            val pairs = "(\\d+)\\s(\\w+)".toRegex()
                .findAll(it)
                .map { it.groupValues[2] to it.groupValues[1].toInt() }.toList()

            Round(
                blue = pairs.find { it.first == "blue" }?.second ?: 0,
                red = pairs.find { it.first == "red" }?.second ?: 0,
                green = pairs.find { it.first == "green" }?.second ?: 0,
            )
        }

        return this
    }
}

data class Round(var blue: Int = 0, var red: Int = 0, var green: Int = 0)

fun part1(input: List<Game>): Int {
    return input.filter { it.withinRules() }.sumOf { it.id }
}

fun part2(input: List<Game>): Int {
    return input.sumOf { it.findMaxEachColorAcrossRounds().reduce { acc, num -> acc * num } }
}

fun main(args: Array<String>) {
    val input = readInput(args[0])
        .split("\n")
        .map { it.split(":") }
        .map { Game("\\d+".toRegex().find(it[0])!!.value.toInt()).buildRounds(it[1].split(";")) }

    check(part1(input) == 2256)
    check(part2(input) == 74229)
}
