package day10

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.math.PI

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AngleTests {
//
//    @ParameterizedTest
//    @CsvSource(
//        "0.0,1.0,0.0",
//        "1.0,1.0,${PI/4}",
//        "1.0,0.0,${PI/2}",
//        "0.0,-1.0,${PI}",
//        "-1.0,-1.0,${5*PI/4}",
//        "-0.1,1.0,${3*PI/2}"
//    )
//    fun angles(x: Double, y: Double, expected: Double) {
//        assertEquals(expected, angle(Vector2(x, y)))
//    }
//
//    @Test
//    fun rotation() {
//        assertEquals(Vector2(-1.0, -1.0), rot90(Vector2(-1.0, 1.0)))
//    }

}
