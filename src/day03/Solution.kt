package day03

import readInputLines

data class NumberCoordinate(val startX: Int, val y: Int, val number: Int) {
    private val endX = startX + number.toString().length
    val coordinates = (startX..< endX).map { Coordinate(it, y) }
}

data class Coordinate(val x: Int, val y: Int) {
    private fun isAdjacent(coordinate: Coordinate): Boolean {
        return (coordinate.x in (x - 1)..(x + 1)) && (coordinate.y in (y - 1)..(y + 1))
    }

    fun isAdjacent(coordinates: List<Coordinate>): Boolean {
        return coordinates.any { isAdjacent(it) }
    }

    fun gearValue(numberCoordinates: List<NumberCoordinate>): Int {
        return numberCoordinates.filter { isAdjacent(it.coordinates) }.let { coordinate ->
            if (coordinate.size != 2) 0 else coordinate[0].number * coordinate[1].number
        }
    }
}

fun part1(numberCoordinates: List<NumberCoordinate>, symbolCoordinates: List<Coordinate>): Int {
    return numberCoordinates.filter {
        symbolCoordinates.any { symbol -> symbol.isAdjacent(it.coordinates) }
    }.sumOf { it.number }
}

fun part2(numberCoordinates: List<NumberCoordinate>, symbolCoordinates: List<Coordinate>): Int {
    return symbolCoordinates.sumOf { it.gearValue(numberCoordinates) }
}

fun main(args: Array<String>) {
    val (numberCoordinates, symbolCoordinates) = readInputLines(args[0]).mapIndexed { y, str ->
        val numberCoords = "\\d+".toRegex().findAll(str)
            .map { NumberCoordinate(it.range.first, y, it.value.toInt()) }
            .toList()

        val symbolCoords = "[^\\d.]".toRegex().findAll(str)
            .map { Coordinate(it.range.first, y) }
            .toList()

        numberCoords to symbolCoords
    }.unzip().let { (numberList, symbolList) ->
        numberList.flatten() to symbolList.flatten()
    }

    check(part1(numberCoordinates, symbolCoordinates) == 498559)
    check(part2(numberCoordinates, symbolCoordinates) == 72246648)
}