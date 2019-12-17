import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.isActive
import java.util.*

fun main() {
    val amplifierControlProgram = arrayOf(3,8,1001,8,10,8,105,1,0,0,21,34,47,72,81,102,183,264,345,426,99999,3,9,102,5,9,9,1001,9,3,9,4,9,99,3,9,101,4,9,9,1002,9,3,9,4,9,99,3,9,102,3,9,9,101,2,9,9,102,5,9,9,1001,9,3,9,1002,9,4,9,4,9,99,3,9,101,5,9,9,4,9,99,3,9,101,3,9,9,1002,9,5,9,101,4,9,9,102,2,9,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,99,3,9,101,1,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,2,9,9,4,9,99,3,9,101,1,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,99,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,99
    )

    val result = optimizePhaseSettings(amplifierControlProgram)

    println("Best value ${result.first} found for phase setting ${result.second}")
}

fun optimizePhaseSettings(amplifierProgram: Array<Int>): Pair<Int, List<Int>> {
    val permutations = permute((5..9).toList())

    var maxValue = 0
    var bestPerm = listOf<Int>()

    for (perm in permutations) {
        println("Testing $perm")
        val rw = perm.map { generateReaderWriter(listOf(it)) }

        // Give a 0 as input to first Amp
        rw[0].second(0)

        val amplifiers = perm.mapIndexed {
            index, _ -> IntcodeComputer(amplifierProgram.clone(), rw[index].first, rw[(index + 1) % 5].second)
        }

        runBlocking {
            val jobs = amplifiers.map {
                launch {
                    it.evaluate()
                }
            }

            while (!jobs.last().isCompleted) {
                delay(100L)
            }

            jobs.slice(0..3).forEach { it.cancel() }
        }

        val lastValue = rw[0].third()

        println("Finished $perm with output $lastValue")

        if (lastValue > maxValue) {
            maxValue = lastValue
            bestPerm = perm
        }
    }

    return Pair(maxValue, bestPerm)
}

fun <T> permute(input: List<T>): List<List<T>> {
    if (input.size == 1) return listOf(input)
    val perms = mutableListOf<List<T>>()
    val toInsert = input[0]
    for (perm in permute(input.drop(1))) {
        for (i in 0..perm.size) {
            val newPerm = perm.toMutableList()
            newPerm.add(i, toInsert)
            perms.add(newPerm)
        }
    }
    return perms
}

fun generateReaderWriter(initialValue: List<Int>): Triple<suspend () -> Int, (Int) -> Unit, () -> Int> {
    val buffer = initialValue.toMutableList()
    var readPointer = 0

    val reader = suspend {
        while (readPointer >= buffer.size) {
            delay(10L)
        }
        buffer[readPointer++]
    }

    val writer: (Int) -> Unit = {
        buffer.add(it)
    }

    return Triple(reader, writer) { buffer.last() }
}
