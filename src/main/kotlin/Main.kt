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
    var currentSlices: Long = 0
    val tempList = arrayListOf<Int>()
    input.types.reversed().forEachIndexed { i, it ->
        if (currentSlices + it <= input.maxSlices) {
            currentSlices += it
            tempList.add(input.types.size - i - 1)
        }
    }
    return Output(currentSlices, tempList.size.toLong(), tempList.sorted())
}

fun createOutputFile(fileName: String, output: Output) {
    val outputFile = File("output${File.separator + fileName}")
    outputFile.printWriter().use { out ->

        out.println(output.types)
        out.println(output.picks.joinToString(separator = " "))
    }
}

