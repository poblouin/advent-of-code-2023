import java.io.File

fun readInput(fileName: String): String {
    return getFile(fileName).readText()
}

fun readInputLines(fileName: String): List<String> {
    return getFile(fileName).readLines()
}

private fun getFile(fileName: String): File {
    return File(System.getProperty("user.dir"), "$fileName.txt")
}