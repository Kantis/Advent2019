import java.nio.file.Files
import java.nio.file.Paths

data class Node(
    val name: String,
    val children: MutableList<Node> = mutableListOf()
)

fun main() {
    println(calculateRequiredJumps(loadMap(""), "YOU", "SAN"))
}

fun sumOrbits(currentNode: Node, currentDistance: Int): Int {
    return currentDistance + currentNode.children.map { sumOrbits(it, currentDistance + 1) }.sum()
}

fun loadMap(path: String): Node {
    val input = Files.readAllLines(Paths.get("C:/dev/inputs/orbits.txt"))
    return parseMap(input)
}

fun parseMap(input: List<String>): Node {
    val root = Node("COM")
    val nodes = HashMap<String, Node>()
    nodes[root.name] = root

    for (line in input) {
        val parts = line.split(")")

        val parentName = parts[0]
        val parent = nodes.getOrPut(parentName) { Node(parentName) }

        val childName = parts[1]
        val child = nodes.getOrPut(childName) { Node(childName) }

        parent.children += child
    }

    return root
}

fun calculateRequiredJumps(root: Node, fromNode: String, toNode: String): Int {
    val path1 = pathFromCenter(root, fromNode)!!
    val path2 = pathFromCenter(root, toNode)!!

    if (path1.size < path2.size) {
        return calculateRequiredJumps(root, toNode, fromNode)
    }

    for (i in path1.indices) {
        if (path2[i] != path1[i])
            return path1.size - i + path2.size - i - 2
    }

    return 0
}

fun pathFromCenter(currentNode: Node, targetNode: String): List<Node>? {
    if (currentNode.name == targetNode) return listOf(currentNode)
    if (currentNode.children.isEmpty()) return null

    val firstOrNull = currentNode.children
        .map { pathFromCenter(it, targetNode) }
        .firstOrNull { it?.isNotEmpty() ?: false }

    if (firstOrNull != null)
        return listOf(currentNode) + firstOrNull

    return null
}

fun findNode(name: String, currentNode: Node): Node? {
    if (currentNode.name == name) return currentNode
    if (currentNode.children.isEmpty()) return null

    return currentNode.children
        .map { findNode(name, it) }
        .firstOrNull { it != null }
}
