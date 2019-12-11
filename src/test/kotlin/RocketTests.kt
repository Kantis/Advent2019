import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RocketTests {

    @ParameterizedTest(name = "{1} requires {0} fuel")
    @CsvSource(
        "12,        2",
        "14,        2",
        "1969,      966",
        "100756,    50346"
    )
    fun `given inputs` (mass: Int, fuelRequirement: Int) {
        assertEquals(fuelRequirement, calculateFuel(mass))
    }
}
