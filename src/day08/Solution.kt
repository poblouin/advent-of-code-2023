package day08

import readInputLines

private fun steps(instructions: String, nodeValue: String, nodes: Map<String, Pair<String, String>>): Int {
    var steps = 0
    var current = nodeValue

    while (!current.endsWith("Z")) {
        val node = nodes[current]!!
        current = if (instructions[steps % instructions.length] == 'L') node.first else node.second
        steps++
    }

    return steps
}

fun gcd(a: Long, b: Long): Long {
    if (b == 0L) return a
    return gcd(b, a % b)
}

fun lcm(a: Long, b: Long): Long {
    return (a / gcd(a, b)) * b
}

fun part1(instructions: String, nodes: Map<String, Pair<String, String>>): Int {
    return steps(instructions, "AAA", nodes)
}

fun part2(instructions: String, nodes: Map<String, Pair<String, String>>): Long {
    return nodes
        .filterKeys { it.endsWith('A') }
        .map { steps(instructions, it.key, nodes).toLong() }
        .reduce { acc, l -> lcm(acc, l) }
}

fun main(args: Array<String>) {
    val input = readInputLines(args[0])
    val instructions = input.take(1).first()
    val nodes = input.drop(2).associate {
        val value = it.substringBefore(" =")
        val children = it.substringAfter("= ").removeSurrounding("(", ")").split(", ").let { it[0] to it[1] }
        value to children
    }

    check(part1(instructions, nodes) == 13771)
    check(part2(instructions, nodes) == 13129439557681)
}
