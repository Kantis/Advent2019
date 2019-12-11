import java.lang.RuntimeException
import kotlin.math.abs

data class Point(
    val x: Int,
    val y: Int
)

operator fun Point.plus(other: Point) = Point(x + other.x, y + other.y)

fun firstIntersection(pathOne: Set<Point>, pathTwo: Set<Point>): Point? {
    return pathOne.intersect(pathTwo)
        .minBy { p -> abs(p.x) + abs(p.y) }
}

fun toPoints(path: String): Set<Point> {
    var currentPoint = Point(0, 0)
    val points = HashSet<Point>()

    for (moves in path.split(",")) {
        val direction = translate(moves[0])
        val repetitions = moves.substring(1).toInt()

        for (i in 0 until repetitions) {
            currentPoint += direction
            points.add(currentPoint)
        }
    }

    return points
}

fun translate(direction: Char): Point {
    return when (direction) {
        'U' -> Point(0, 1)
        'D' -> Point(0, -1)
        'L' -> Point(-1, 0)
        'R' -> Point(1, 0)
        else -> throw RuntimeException("No handling for direction $direction")
    }
}