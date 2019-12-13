import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SafecrackerTests {

    @Test
    fun `two adjacent are the same`() {
        assertTrue(twoAdjacentAreTheSame(111111))
    }

    @Test
    fun `two adjacent are NOT the same`() {
        assertFalse(twoAdjacentAreTheSame(123789))
    }

    @Test
    fun `never descending`() {
        assertTrue(isNeverDescending(111111))
    }

    @Test
    fun `has descending digits`() {
        assertFalse(isNeverDescending(223450))
    }

    @ParameterizedTest
    @ValueSource(
        ints = [11, 12344, 1122, 111122]
    )
    fun `even number of repeating digits`(value: Int) {
        assertTrue(evenNumberedRepeatingDigits(value))
    }

    @ParameterizedTest
    @ValueSource(
        ints = [1, 111, 1111, 11111, 123444]
    )
    fun `odd number of repeating digits`(value: Int) {
        assertFalse(evenNumberedRepeatingDigits(value))
    }

    @Test
    fun fragment() {
        assertEquals(setOf(Fragment(1, 3)), fragmentNumber(111))
    }

}