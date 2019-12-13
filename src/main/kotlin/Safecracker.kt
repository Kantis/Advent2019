fun main() {
    val count = (248345..746315)
        .filter { isNeverDescending(it) }
        .filter { evenNumberedRepeatingDigits(it) }
        .count()
    println("Number of potential combinations: $count")
}

fun isNeverDescending(number: Int): Boolean {
    if (number < 10) return true
    return number % 10 >= (number % 100) / 10 && isNeverDescending(number / 10)
}

fun twoAdjacentAreTheSame(number: Int): Boolean {
    if (number < 10) return false
    return (number % 100) / 10 == number % 10 || twoAdjacentAreTheSame(number / 10)
}

data class Fragment(
    val digit: Int,
    val repetitions: Int
)

fun fragmentNumber(number: Int): Set<Fragment> {
    if (number <= 0) return emptySet()

    var divisor = 10
    val currentDigit = number % 10
    var repetitions = 1

    while ((number / divisor) % 10 == currentDigit) {
        divisor *= 10
        repetitions++
    }

    return listOf(Fragment(currentDigit, repetitions)).union(fragmentNumber(number / divisor))
}

fun evenNumberedRepeatingDigits(number: Int): Boolean {
    return fragmentNumber(number).any { it.repetitions == 2}
}