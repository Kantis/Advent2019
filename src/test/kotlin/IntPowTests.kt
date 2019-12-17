import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IntPowTests {


    @ParameterizedTest
    @CsvSource(
        "1", "2", "3", "-1"
    )
    fun `anything to exp 0 is 1`(base: Int) {
        assertEquals(1, intPow(base, 0))
    }


    @ParameterizedTest
    @CsvSource(
        "5,1,5",
        "2,2,4",
        "10,2,100",
        "10,3,1000",
        "5,3,125"
    )
    fun `Some expected values`(base: Int, exp: Int, expected: Int) {
        assertEquals(expected, intPow(base, exp))
    }



}
