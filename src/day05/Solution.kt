package day05

import readInput

data class Mapping(val destinationStart: Long, val sourceStart: Long, val rangeLength: Long) {
    fun containsSource(source: Long): Boolean {
        return sourceStart <= source && sourceStart + rangeLength >= source
    }

    fun getDestinationForSource(source: Long): Long {
        return destinationStart + (source - sourceStart)
    }
}

class Garden {
    var seeds = setOf<Long>()
    var seedToSoil = listOf<Mapping>()
    var soilToFertilizer = listOf<Mapping>()
    var fertilizerToWater = listOf<Mapping>()
    var waterToLight = listOf<Mapping>()
    var lightToTemperature = listOf<Mapping>()
    var temperatureToHumidity = listOf<Mapping>()
    var humidityToLocation = listOf<Mapping>()

    companion object {
        fun fromInput(input: List<List<List<Long>>>): Garden {
            val garden = Garden()

            input.forEachIndexed { i, items ->
                when (i) {
                    0 -> garden.seeds = items.flatten().toSet()
                    1 -> garden.seedToSoil = buildMap(items)
                    2 -> garden.soilToFertilizer = buildMap(items)
                    3 -> garden.fertilizerToWater = buildMap(items)
                    4 -> garden.waterToLight = buildMap(items)
                    5 -> garden.lightToTemperature = buildMap(items)
                    6 -> garden.temperatureToHumidity = buildMap(items)
                    7 -> garden.humidityToLocation = buildMap(items)
                }
            }

            return garden
        }

        private fun buildMap(ranges: List<List<Long>>): List<Mapping> {
            return ranges.map { Mapping(it[0], it[1], it[2] - 1) }
        }
    }

    private fun findDestination(seed: Long, finder: List<Mapping>): Long {
        return finder.find { it.containsSource(seed) }?.getDestinationForSource(seed) ?: seed
    }

    fun getLowestLocationOfSeeds(): Long {
        return seeds.minOf { getLocationOfSeed(it) }
    }

    fun getLowestLocationOfSeedsPairs(): Long {
        var min = Long.MAX_VALUE
        seeds.chunked(2).forEach { (start, length) ->
            (start..start + (length - 1)).asSequence().forEach {
                min = minOf(min, getLocationOfSeed(it))
            }
        }

        return min
    }

    private fun getLocationOfSeed(seed: Long): Long {
        return findDestination(seed, seedToSoil)
            .let { findDestination(it, soilToFertilizer) }
            .let { findDestination(it, fertilizerToWater) }
            .let { findDestination(it, waterToLight) }
            .let { findDestination(it, lightToTemperature) }
            .let { findDestination(it, temperatureToHumidity) }
            .let { findDestination(it, humidityToLocation) }
    }
}

fun part1(garden: Garden): Long {
    return garden.getLowestLocationOfSeeds()
}

fun part2(garden: Garden): Long {
    return garden.getLowestLocationOfSeedsPairs()
}

fun main(args: Array<String>) {
    val garden = Garden.fromInput(
        readInput(args[0])
            .split("\n\n")
            .map { it.split("\n").map { it.substringAfter(":") } }
            .map {
                it.map { "\\d+".toRegex().findAll(it).toList().map { it.value.toLong() } }.filter { it.isNotEmpty() }
            },
    )

    check(part1(garden) == 57075758L)
    check(part2(garden) == 31161857L)
}
