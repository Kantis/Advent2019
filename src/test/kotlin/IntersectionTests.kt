import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IntersectionTests  {

    @Test
    fun `given example`() {
        val pathOne = toPath("R75,D30,R83,U83,L12,D49,R71,U7,L72")
        val pathTwo = toPath("U62,R66,U55,R34,D71,R55,D58,R83")

        val firstIntersection = minStepsToIntersect(pathOne, pathTwo)
        assertEquals(610, firstIntersection)
    }
}
