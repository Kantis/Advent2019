import java.lang.RuntimeException
import java.util.*
import kotlin.collections.HashSet

data class Path (
    val visitedPoints: Set<Point>,
    val stepsToPoint: Map<Point, Int>
)

data class Point(
    val x: Int,
    val y: Int
)

public operator fun Point.plus(other: Point) = Point(x + other.x, y + other.y)

public operator fun Point.minus(other: Point) = Point(x - other.x, y - other.y)


fun minStepsToIntersect(pathOne: Path, pathTwo: Path): Int {
    val intersectingPoints = pathOne.visitedPoints.intersect(pathTwo.visitedPoints)
    var minDistance: Int? = null

    for (i in intersectingPoints) {
        val pointDistance = pathOne.stepsToPoint[i]!! + pathTwo.stepsToPoint[i]!!
        if (minDistance == null || minDistance > pointDistance) {
            minDistance = pointDistance
        }
    }

    return minDistance ?: 0
}

fun toPath(path: String): Path {
    var currentPoint = Point(0, 0)
    val points = HashSet<Point>()
    val pointDistances = HashMap<Point, Int>()
    var stepsUsed = 0

    for (moves in path.split(",")) {
        val direction = resolveOperand(moves[0])
        val repetitions = moves.substring(1).toInt()

        for (i in 0 until repetitions) {
            stepsUsed++
            currentPoint += direction

            if (!pointDistances.contains(currentPoint))
                pointDistances[currentPoint] = stepsUsed

            points.add(currentPoint)
        }
    }

    return Path(points, pointDistances)
}

fun resolveOperand(direction: Char): Point {
    return when (direction) {
        'U' -> Point(0, 1)
        'D' -> Point(0, -1)
        'L' -> Point(-1, 0)
        'R' -> Point(1, 0)
        else -> throw RuntimeException("No handling for direction $direction")
    }
}