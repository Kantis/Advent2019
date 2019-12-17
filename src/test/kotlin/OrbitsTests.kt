import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrbitsTests {

    @Test
    fun `path from center`() {
        val map = Node(
            "COM",
            children = mutableListOf(
                Node("A"),
                Node("B")
            )
        )

        assertEquals(listOf(map, map.children.first { it.name == "A" }), pathFromCenter(map, "A"))
    }

    @Test
    fun `required jumps`() {
        val map = parseMap(
            """COM)B
B)C
C)D
D)E
E)F
B)G
G)H
D)I
D)YOU
E)J
J)K
K)L
I)SAN""".split("\n")
        )

        assertEquals(1, calculateRequiredJumps(map, "YOU", "SAN"))
    }

}
