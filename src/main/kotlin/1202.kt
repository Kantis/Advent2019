

fun Intcode(program: MutableList<Int>) : List<Int> {

    for (programCounter in program.indices step 4) {
        if (program[programCounter] == 99) break

        val operand1pos = program[programCounter + 1]
        val operand2pos = program[programCounter + 2]
        val resultPos = program[programCounter + 3]

        val operation = program[programCounter]
        if (operation == 1) {
            program[resultPos] = program[operand1pos] + program[operand2pos]
        } else if (operation == 2) {
            program[resultPos] = program[operand1pos] * program[operand2pos]
        } else {
            return program
        }
    }

    return program
}