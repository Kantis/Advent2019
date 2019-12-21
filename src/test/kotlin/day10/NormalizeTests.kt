package day10

import Point
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.math.MathContext

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NormalizeTests {
    @Test
    fun `decimals required`() {
        val expected = Vector2(0.25.toBigDecimal(), 1.toBigDecimal())
        assertEquals(1, Point(4, 1).normalize().length())
    }
}
