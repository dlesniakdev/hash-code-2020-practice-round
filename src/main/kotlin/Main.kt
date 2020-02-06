import java.io.File

fun main(args: Array<String>) {
    args.forEach { processFile(it) }
}

fun processFile(fileName: String) {
    println("Computing for $fileName")
    val input = parseInputFile("input${File.separator + fileName}")
    println(input)
    val output = compute(input)
    println(output)
    println("Slices difference: ${input.maxSlices - output.slices}")
    println("Real score: ${output.picks.map { input.types[it] }.sum()}")
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
    var bestScore = Output(0, 0, emptyList())
    input.types.reversed().forEachIndexed { i, _ ->
        var currentSlices: Long = 0
        val size = input.types.size - i
        val tempList = arrayListOf<Int>()

        for (j in (size - 1) downTo 0) {
            val it = input.types[j]
            if (currentSlices + it <= input.maxSlices) {
                currentSlices += it
                tempList.add(j)
            }
        }

        val tmpOutput = Output(currentSlices, tempList.size.toLong(), tempList.sorted())
        if (bestScore.slices < tmpOutput.slices) {
            bestScore = tmpOutput
        }
    }
    return bestScore
}

fun createOutputFile(fileName: String, output: Output) {
    val outputFile = File("output${File.separator + fileName}")
    outputFile.printWriter().use { out ->
        out.println(output.types)
        out.println(output.picks.joinToString(separator = " "))
    }
}
