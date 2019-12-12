import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

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

}