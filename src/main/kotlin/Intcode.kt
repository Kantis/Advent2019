import kotlin.math.pow
import kotlin.math.roundToInt

fun main() {
    val cpu = IntcodeComputer(
        arrayOf(
            3,225,1,225,6,6,1100,1,238,225,104,0,1102,83,20,225,1102,55,83,224,1001,224,-4565,224,4,224,102,8,223,223,101,5,224,224,1,223,224,223,1101,52,15,225,1102,42,92,225,1101,24,65,225,101,33,44,224,101,-125,224,224,4,224,102,8,223,223,1001,224,7,224,1,223,224,223,1001,39,75,224,101,-127,224,224,4,224,1002,223,8,223,1001,224,3,224,1,223,224,223,2,14,48,224,101,-1300,224,224,4,224,1002,223,8,223,1001,224,2,224,1,223,224,223,1002,139,79,224,101,-1896,224,224,4,224,102,8,223,223,1001,224,2,224,1,223,224,223,1102,24,92,225,1101,20,53,224,101,-73,224,224,4,224,102,8,223,223,101,5,224,224,1,223,224,223,1101,70,33,225,1101,56,33,225,1,196,170,224,1001,224,-38,224,4,224,102,8,223,223,101,4,224,224,1,224,223,223,1101,50,5,225,102,91,166,224,1001,224,-3003,224,4,224,102,8,223,223,101,2,224,224,1,224,223,223,4,223,99,0,0,0,677,0,0,0,0,0,0,0,0,0,0,0,1105,0,99999,1105,227,247,1105,1,99999,1005,227,99999,1005,0,256,1105,1,99999,1106,227,99999,1106,0,265,1105,1,99999,1006,0,99999,1006,227,274,1105,1,99999,1105,1,280,1105,1,99999,1,225,225,225,1101,294,0,0,105,1,0,1105,1,99999,1106,0,300,1105,1,99999,1,225,225,225,1101,314,0,0,106,0,0,1105,1,99999,1107,677,677,224,1002,223,2,223,1006,224,329,1001,223,1,223,1107,226,677,224,102,2,223,223,1005,224,344,101,1,223,223,108,677,677,224,1002,223,2,223,1006,224,359,101,1,223,223,107,677,677,224,1002,223,2,223,1006,224,374,1001,223,1,223,1007,677,677,224,102,2,223,223,1006,224,389,101,1,223,223,108,677,226,224,102,2,223,223,1006,224,404,101,1,223,223,1108,226,677,224,102,2,223,223,1005,224,419,1001,223,1,223,7,677,226,224,102,2,223,223,1005,224,434,101,1,223,223,1008,677,677,224,102,2,223,223,1006,224,449,1001,223,1,223,1007,677,226,224,1002,223,2,223,1006,224,464,101,1,223,223,1108,677,677,224,1002,223,2,223,1005,224,479,1001,223,1,223,107,226,226,224,1002,223,2,223,1005,224,494,101,1,223,223,8,226,677,224,102,2,223,223,1006,224,509,101,1,223,223,8,677,677,224,102,2,223,223,1006,224,524,101,1,223,223,1007,226,226,224,1002,223,2,223,1006,224,539,1001,223,1,223,107,677,226,224,102,2,223,223,1006,224,554,101,1,223,223,1107,677,226,224,1002,223,2,223,1006,224,569,1001,223,1,223,1008,226,677,224,102,2,223,223,1006,224,584,1001,223,1,223,1008,226,226,224,1002,223,2,223,1005,224,599,1001,223,1,223,7,677,677,224,1002,223,2,223,1005,224,614,1001,223,1,223,1108,677,226,224,1002,223,2,223,1005,224,629,101,1,223,223,7,226,677,224,1002,223,2,223,1005,224,644,1001,223,1,223,8,677,226,224,102,2,223,223,1005,224,659,101,1,223,223,108,226,226,224,102,2,223,223,1005,224,674,101,1,223,223,4,223,99,226
       ),
        { readLine()!!.toInt() },
        { println(it) }
    )

    cpu.evaluate()
}

enum class AddressingMode {
    Position,
    Immediate
}

class IntcodeComputer(private val program: Array<Int>, val inputFunction: () -> Int, val outputFunction: (Int) -> Unit) {

    private var programCounter: Int = 0

    fun evaluate(): Array<Int> {
        while (programCounter < program.size) {
            if (program[programCounter] == 99) break

            val opCode = program[programCounter]
            val args = resolveParameters(opCode)

            val preOpProgramCounter = programCounter
            when (opCode % 100) {
                1 -> program[args[2]] = args[0] + args[1]
                2 -> program[args[2]] = args[0] * args[1]
                3 -> program[args[0]] = inputFunction()
                4 -> outputFunction(args[0])
                5 -> if (args[0] != 0) programCounter = args[1]
                6 -> if (args[0] == 0) programCounter = args[1]
                7 -> program[args[2]] = if (args[0] < args[1]) 1 else 0
                8 -> program[args[2]] = if (args[0] == args[1]) 1 else 0

                else -> return program
            }

            if (programCounter == preOpProgramCounter)
                programCounter += 1 + args.size
        }

        return program
    }

    private fun resolveParameters(opCode: Int): List<Int> {
        var translateLastOperand = when (opCode % 100) {
            1, 2, 3, 7, 8 -> false
            else -> true
        }

        val rawArgs = when (opCode % 100) {
            1, 2, 7, 8 -> program.slice(programCounter + 1..programCounter + 3)
            5, 6 -> program.slice(programCounter + 1..programCounter + 2)
            3, 4 -> listOf(program[programCounter + 1])
            else -> error("Invalid opcode $opCode")
        }

        val modes = resolveAddressingModes(opCode, rawArgs.size)

        return if (!translateLastOperand) {
            arrayOf(
                rawArgs.slice(0 until rawArgs.size - 1)
                    .mapIndexed { index, it -> resolveOperand(it, modes[index]) }
                    .toTypedArray(),
                arrayOf(rawArgs[rawArgs.size - 1])
            ).flatten()
        } else {
            rawArgs.mapIndexed { index, it -> resolveOperand(it, modes[index]) }
        }
    }

    private fun resolveAddressingModes(opCode: Int, paramCount: Int): List<AddressingMode> {
        val addressingModes = opCode / 100
        return (0 until paramCount)
            .map { addressingModes / 10.0.pow(it).roundToInt() % 10 }
            .map { if (it == 1) AddressingMode.Immediate else AddressingMode.Position }
    }

    private fun resolveOperand(operand: Int, mode: AddressingMode): Int {
        if (mode == AddressingMode.Immediate) return operand
        return program[operand]
    }

}

//fun <S, T> pairLists(l1: List<S>, l2: List<T>): List<Pair<S, T>> {
//    if (l1.size != l2.size) error("Lists must be same length")
//    return l1.mapIndexed { index, item -> Pair(item, l2[index]) }
//}
