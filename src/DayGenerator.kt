import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun main(args: Array<String>) {
    val dayToday = if (args.isNotEmpty()) args[0] else "day${LocalDate.now().format(DateTimeFormatter.ofPattern("dd"))}"
    val dayDirectory = File(dayToday)
    val outputFiles = listOf("sample.txt", "input.txt", "Solution.kt")
    val solutionSkeleton = """
        fun part1(input: String): String {
            return input
        }
        
        fun main(args: Array<String>) {
            val input = readInput(args[0])
            println(part1(input))
        }
    """.trimIndent()

    if (!dayDirectory.exists()) {
        dayDirectory.mkdir()
    }

    File(dayDirectory, "Solution.kt").takeIf { !it.exists() }?.writeText(solutionSkeleton)
    outputFiles.forEach { fileName ->
        val file = File(dayDirectory, fileName)
        if (!file.exists()) {
            file.createNewFile()
        }
    }
}
