package day07

import readInputLines

data class Hand(val cardDigits: List<Int>, val groups: List<Int>, val bid: Int)

fun solve(input: List<List<String>>, mapping: Map<Char, Int>): Int {
    return input.map { (hand, bid) ->
        val parsedHand = hand.map { mapping[it]!! }
        val groups = if (mapping.get('J') != 0) {
            parsedHand.groupBy { it }.map { it.value.size }.sortedByDescending { it }
        } else {
            (1..mapping.size)
                .map { i ->
                    parsedHand.map { if (it == 0) i else it }.groupBy { it }.map { it.value.size }.sortedByDescending { it }
                }
                .sortedWith(compareBy({ it[0] }, { it.getOrNull(1) }))
                .last()
        }

        Hand(parsedHand, groups, bid.toInt())
    }
        .sortedWith(
            compareBy(
                { it.groups[0] },
                { it.groups.getOrNull(1) },
                { it.cardDigits[0] },
                { it.cardDigits[1] },
                { it.cardDigits[2] },
                { it.cardDigits[3] },
                { it.cardDigits[4] },
            ),
        )
        .mapIndexed { index, hand -> (index + 1) * hand.bid }
        .sum()
}

fun main(args: Array<String>) {
    val input = readInputLines(args[0]).map { it.split(" ") }

    check(solve(input, "23456789TJQKA".withIndex().associate { it.value to it.index }) == 253638586)
    check(solve(input, "J23456789TQKA".withIndex().associate { it.value to it.index }) == 253253225)
}
