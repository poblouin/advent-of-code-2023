package day01

import java.io.File

fun readInput(fileName: String): List<String> {
    return File("./$fileName").readLines()
}

fun part1(formattedInput: List<String>): List<String> {
    return formattedInput
}

fun main(args: Array<String>) {
    val formattedInput = readInput(args[0])
    println(part1(formattedInput))
}