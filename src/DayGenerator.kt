import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun main(args: Array<String>) {
    val dayToday = if (args.isNotEmpty()) args[0] else "day${LocalDate.now().format(DateTimeFormatter.ofPattern("dd"))}"
    val dayDirectory = File(dayToday)
    val outputFiles = listOf("sample.txt", "input.txt", "Solution.kt")
    val solutionSkeleton = """
        package $dayToday
        
        import readInputLines
        
        fun part1(input: List<String>): Int {
            return 0
        }
        
        fun part2(input: List<String>): Int {
            return 0
        }
        
        fun main(args: Array<String>) {
            val input = readInputLines(args[0])
            println(input)
        //    println(part1(input))
        //    println(part2(input))
        //    check(part1(input) == 53334)
        //    check(part2(input) == 52834)
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
