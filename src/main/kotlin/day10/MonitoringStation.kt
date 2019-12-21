package day10

import Point
import intPow
import java.lang.Math.*
import java.math.BigDecimal
import java.math.BigDecimal.*
import java.math.MathContext
import java.math.RoundingMode
import java.math.RoundingMode.HALF_UP

data class Vector2(
    val x: BigDecimal,
    val y: BigDecimal
)

val mathContext = MathContext.DECIMAL128

fun main() {
    val input = """...###.#########.####
.######.###.###.##...
####.########.#####.#
########.####.##.###.
####..#.####.#.#.##..
#.################.##
..######.##.##.#####.
#.####.#####.###.#.##
#####.#########.#####
#####.##..##..#.#####
##.######....########
.#######.#.#########.
.#.##.#.#.#.##.###.##
######...####.#.#.###
###############.#.###
#.#####.##..###.##.#.
##..##..###.#.#######
#..#..########.#.##..
#.#.######.##.##...##
.#.##.#####.#..#####.
#.#.##########..#.##.
""".split("\n")

    val station = findBestLocation(input)
    println("Located station nat ${station.location}")
    doTheLaserThing(station)
}

data class MonitoringStation(
    val location: Point,
    val directions: MutableMap<Vector2, List<Point>>
)

operator fun Point.minus(other: Point) = Point(x - other.x, y - other.y)
fun doTheLaserThing(station: MonitoringStation) {
    val sortedKeys = station.directions.keys.sortedBy { angle(it) }
    var i = 0;

    while (i < 210) {
        for (entry in sortedKeys) {
            val sorted = station.directions[entry]!!.sortedBy { (station.location - it).distanceFromCenter() }
            val toRemove = sorted.firstOrNull() ?: continue

            station.directions[entry] = sorted - toRemove
            i++
            println("$i - $entry: Removed $toRemove")
        }
    }
}

fun findBestLocation(input: List<String>): MonitoringStation {
    val asteroidMap = parseAsteroidMap(
        input
    )

    var bestResult = 0
    var result: MonitoringStation? = null

    for (asteroid in asteroidMap) {
        val normalized = asteroidMap
            .filter { it != asteroid }
            .groupBy { it.minus(asteroid).normalize() }

        val visibleAsteroids = normalized.keys.size

//        println("Normalized vectors from $asteroid: ($visibleAsteroids)")
//        normalized.forEach { println("        $it") }
//        println("$asteroid: $visibleAsteroids")

        if (visibleAsteroids > bestResult) {
            bestResult = visibleAsteroids
            result = MonitoringStation(asteroid, normalized.toMutableMap())
        }
    }

    println("Locating station at ${result!!.location}")

    return result!!
}

fun angle(v: Vector2): Double {
    val vect = v.normalize()

    val angle = acos(vect.dotProduct(Vector2(ZERO.setScale(128), -ONE.setScale(128))).toDouble())
    if (quadrant(vect) > 1)
        return 2 * PI - angle
    return angle
}

fun Vector2.dotProduct(other: Vector2): BigDecimal {
    return x * other.x + y * other.y
}

fun rot90(v: Vector2): Vector2 {
    return Vector2(-v.y, v.x)
}

fun quadrant(v: Vector2): Int {
    if (v.x >= ZERO && v.y > ZERO) return 0
    if (v.x >= ZERO && v.y <= ZERO) return 1
    return if (v.x <= ZERO && v.y < ZERO) 2
    else 3
}

fun parseAsteroidMap(input: List<String>): Set<Point> {
    val result = mutableSetOf<Point>()
    for (line in input.indices) {
        for (char in input[line].indices) {
            if (input[line][char] == '#') {
                result.add(Point(char, line))
            }
        }
    }
    return result
}

operator fun Vector2.minus(other: Vector2): Vector2 {
    return Vector2(other.x - this.x, other.y - this.y)
}

fun Vector2.normalize(): Vector2 {
    val length = this.length()
    return Vector2(
        (x / length).setScale(5, HALF_UP),
        (y / length).setScale(5, HALF_UP)
    )
}

fun Point.normalize(): Vector2 {
    val length = distanceFromCenter()
    return Vector2(
        x.toBigDecimal(mathContext).setScale(128).div(length).setScale(5, HALF_UP),
        y.toBigDecimal(mathContext).setScale(128).div(length).setScale(5, HALF_UP)
    )
}

fun Point.distanceFromCenter(): BigDecimal {
    return (intPow(x, 2) + intPow(y, 2)).toBigDecimal(mathContext).setScale(128).sqrt(mathContext)
}

fun Vector2.length(precision: Int = 30): BigDecimal {
    val ctx = MathContext(precision)
    val bd = x.pow(2) + y.pow(2)
    return bd.sqrt(ctx)
}
