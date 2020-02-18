import java.io.File
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    args.map { file -> measureTimeMillis { processFile(file) } }.forEach { println("Execution time $it[ms]") }
}

fun processFile(fileName: String) {
    println("Computing for $fileName")
    val input = parseInputFile("input${File.separator + fileName}")
    println(input)
    val output = compute(input.types, input.maxSlices)
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

fun compute(types_: List<Long>, maxSlices: Long, from: Int = types_.size, root: Boolean = true): Output {
    var bestScore = Output(0, 0, 0, emptyList())
    var types = types_
    if (types_.size != from) {
        types = types_.subList(0, from)
    }

    types.reversed().forEachIndexed { i, _ ->
        var currentSlices: Long = 0
        val size = types.size - i
        val tempList = arrayListOf<Int>()
        var lastAdded = size

        for (j in (size - 1) downTo 0) {
            val it = types[j]
            if (currentSlices + it <= maxSlices) {
                currentSlices += it
                tempList.add(j)
                lastAdded = j
            }
        }

        val tmpOutput = Output(currentSlices, tempList.size.toLong(), lastAdded.toLong(), tempList.sorted())
        if (tmpOutput.slices == maxSlices) {
            return tmpOutput
        } else if (bestScore.slices < tmpOutput.slices) {
            bestScore = tmpOutput
        }

        if (root && tempList.isNotEmpty() && lastAdded != 0) {
            val adjusted = compute(types, maxSlices - currentSlices + types[lastAdded], lastAdded + 1, false)
                .takeIf { it.slices > 0 }
                ?.let { mergeOutputs(tmpOutput, it, types[lastAdded]) }

            if (adjusted?.slices == maxSlices) {
                return adjusted
            } else if (bestScore.slices < adjusted?.slices!!) {
                bestScore = tmpOutput
            }

        }

    }
    return bestScore
}

fun mergeOutputs(tmpOutput: Output, it: Output, lastAddedValue: Long): Output {
    val joinedList = arrayListOf<Int>()
    joinedList.addAll(it.picks)
    joinedList.addAll(tmpOutput.picks.subList(1, tmpOutput.picks.size))
    return Output(
        tmpOutput.slices - lastAddedValue + it.slices,
        tmpOutput.types - 1 + it.types,
        0,
        joinedList
    )
}

fun createOutputFile(fileName: String, output: Output) {
    val outputFile = File("output${File.separator + fileName}")
    outputFile.printWriter().use { out ->
        out.println(output.types)
        out.println(output.picks.joinToString(separator = " "))
    }
}
