package day15

import readInput

data class Item(val label: String, val operator: Char, val focalLength: Int? = null) {
    override fun equals(other: Any?): Boolean {
        return other is Item && other.label == this.label
    }

    override fun hashCode(): Int {
        return label.toHash()
    }
}

fun String.toHash(): Int =
    this.fold(0) { hash, char -> (((hash + char.code) * 17) % 256) }

fun String.toItem(): Item = "(\\w+)([-=])(\\d*)".toRegex().matchEntire(this).let {
    it?.destructured?.let { (label, operator, value) ->
        Item(label, operator.first(), value.toIntOrNull())
    } ?: throw IllegalArgumentException("Invalid input: $this")
}

fun part1(input: List<String>): Int {
    return input.sumOf { it.toHash() }
}

fun part2(input: List<Item>): Int {
    return input.fold(mutableMapOf<Int, MutableList<Item>>()) { map, item ->
        map.getOrPut(item.label.toHash()) { mutableListOf() }.apply {
            val index = indexOf(item)
            when {
                item.operator == '-' -> remove(item)
                index >= 0 -> this[index] = item
                else -> add(item)
            }
        }
        map
    }.entries.sumOf { (box, list) ->
        list.mapIndexed { i, item ->
            (box + 1) * (i + 1) * item.focalLength!!
        }.sum()
    }
}

fun main(args: Array<String>) {
    val input = readInput(args[0]).replace("\n", "").split(",")

    check(part1(input) == 505379)
    check(part2(input.map { it.toItem() }) == 263211)
}