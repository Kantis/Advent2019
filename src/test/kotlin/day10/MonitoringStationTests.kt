package day10

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MonitoringStationTests {

    @Test
    fun `given example` (){
        val result = findBestLocation("""......#.#.
#..#.#....
..#######.
.#.#.###..
.#..#.....
..#....#.#
#..#....#.
.##.#..###
##...#..#.
.#....####""".split("\n"))


        assertEquals(5, result.location.x)
        assertEquals(8, result.location.y)
    }

    @Test
    fun `given example 2` (){
        val result = findBestLocation(""".#..#
.....
#####
....#
...##""".split("\n"))


        assertEquals(3, result.location.x)
        assertEquals(4, result.location.y)
    }

}