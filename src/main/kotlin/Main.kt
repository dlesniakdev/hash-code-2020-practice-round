import java.io.File

fun main(args: Array<String>) {
    args.forEach { processFile(it) }
}

fun processFile(fileName: String) {
    val input = parseInputFile("input${File.separator + fileName}")
    println(input)
    val output = compute(input)
    println(output)
    println("Slices difference: ${input.maxSlices - output.slices}")
    createOutputFile(fileName, output)
    println()
}

fun parseInputFile(fileName: String): Input {
    val readLines = File(fileName).readLines()
    val firstLine = readLines[0].split(" ")
    val secondLine = readLines[1].split(" ")
    return Input(firstLine[0].toLong(), firstLine[1].toLong(), secondLine.map { it.toLong() })
}

fun compute(input: Input): Output {
    return Output(1L, 1L, emptyList())
}

fun createOutputFile(fileName: String, output: Output) {
    val outputFile = File("output${File.separator + fileName}")
    outputFile.printWriter().use { out ->

        out.println(output.types)
        out.println(output.picks.joinToString(separator = " "))
    }
}

