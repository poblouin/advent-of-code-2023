package day10

import readInputLines

data class Coordinate(val x: Int, val y: Int) {
    operator fun plus(other: Coordinate): Coordinate = Coordinate(x + other.x, y + other.y)
}

enum class Direction {
    NORTH, SOUTH, EAST, WEST;

    fun toCoordinate(): Coordinate = when (this) {
        NORTH -> Coordinate(0, -1)
        SOUTH -> Coordinate(0, 1)
        EAST -> Coordinate(1, 0)
        WEST -> Coordinate(-1, 0)
    }
}

data class Grid(private val grid: List<String>) {
    private val start by lazy { findStart() }
    val loop by lazy { findLoop() }

    private fun findLoop(): Set<Coordinate> {
        val loopPath = mutableSetOf<Coordinate>()
        val stack = mutableListOf(start)

        while (stack.isNotEmpty()) {
            val current = stack.removeLast()

            if (loopPath.contains(current)) continue
            loopPath.add(current)

            val possibleDirections = getPossibleDirections(current)
            for (dir in possibleDirections) {
                val next = current + dir.toCoordinate()
                if (!loopPath.contains(next)) {
                    stack.add(next)
                }
            }
        }

        return loopPath
    }

    private fun findStart(): Coordinate =
        grid.mapIndexedNotNull { i, line -> line.indexOf('S').takeIf { it != -1 }?.let { Coordinate(it, i) } }
            .firstOrNull() ?: throw IllegalStateException("No start found")

    private fun getPossibleDirections(c: Coordinate): List<Direction> {
        return try {
            when (getAt(c)) {
                '|' -> listOf(Direction.NORTH, Direction.SOUTH)
                '-' -> listOf(Direction.EAST, Direction.WEST)
                'L' -> listOf(Direction.NORTH, Direction.EAST)
                'J' -> listOf(Direction.NORTH, Direction.WEST)
                '7' -> listOf(Direction.SOUTH, Direction.WEST)
                'F' -> listOf(Direction.SOUTH, Direction.EAST)
                '.' -> listOf()
                'S' -> listOf(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST)
                else -> throw IllegalStateException("Unknown character")
            }
        } catch (e: StringIndexOutOfBoundsException) {
            listOf()
        }
    }

    private fun getAt(c: Coordinate): Char = grid[c.y][c.x]
}

fun part1(grid: Grid): Int {
    return grid.loop.size / 2
}

fun part2(grid: Grid): Int {
    return 0
}

fun main(args: Array<String>) {
    val input = readInputLines(args[0])
    val grid = Grid(input)

    check(part1(grid) == 6778)
    println(part2(grid))
//    check(part2(input) == 52834)
}