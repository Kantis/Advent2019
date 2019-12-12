fun main() {
    val count = (248345..746315).filter { twoAdjacentAreTheSame(it) }
        .filter { isNeverDescending(it) }
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