import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AmplifierTests {

    @Test
    suspend fun `given input 1`() {
        val result = optimizePhaseSettings(arrayOf(3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,
            27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5))
        assertEquals(139629729, result.first)
        assertEquals(listOf(9,8,7,6,5), result.second)
    }

    @Test
    suspend fun `given input 2`() {
        val result = optimizePhaseSettings(arrayOf(3,23,3,24,1002,24,10,24,1002,23,-1,23,
            101,5,23,23,1,24,23,23,4,23,99,0,0))
        assertEquals(54321, result.first)
        assertEquals(listOf(0,1,2,3,4), result.second)
    }

    @Test
    suspend fun `given input 3`() {
        val result = optimizePhaseSettings(arrayOf(3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,
            1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0))
        assertEquals(65210, result.first)
        assertEquals(listOf(1,0,4,3,2), result.second)
    }

}
