import java.nio.file.Files
import java.nio.file.Paths

fun calculateFuel(mass: Int): Int {
    val myFuel = mass / 3 - 2

    if (myFuel <= 0) return 0

    return myFuel + calculateFuel(myFuel)
}

fun main() {
//    val client = HttpClient.newHttpClient()
//    val request = HttpRequest.newBuilder()
//        .uri(URI.create("https://adventofcode.com/2019/day/1/input"))
//        .GET()
//        .build()
//
//    val input = client.send(request, HttpResponse.BodyHandlers.ofLines())


    val input = Files.readAllLines(Paths.get("C:/dev/inputs/input1.txt"))

    print(
        input.map { it.toInt() }
            .map { calculateFuel(it) }
            .sum()
    )

}

