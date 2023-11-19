import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun main(args: Array<String>) {
    val dayToday = if (args.isNotEmpty()) args[0] else "day${LocalDate.now().format(DateTimeFormatter.ofPattern("dd"))}"
    val dayDirectory = File(dayToday)
    val outputFiles = listOf("sample.txt", "input.txt", "Solution.kt")
    val solutionSkeleton = """
        package ${dayDirectory.name}
        
        import java.io.File
        
        fun readInput(fileName: String): List<String> {
            return File("./${'$'}fileName").readLines()
        }

        fun part1(formattedInput: List<String>): List<String> {
            return formattedInput
        }

        fun main(args: Array<String>) {
            val formattedInput = readInput(args[0])
            println(part1(formattedInput))
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
