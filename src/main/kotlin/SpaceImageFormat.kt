import java.nio.file.Files
import java.nio.file.Paths

data class Dimension(val width: Int, val height: Int) {
    fun pixelCount(): Int {
        return width * height
    }
}

data class SpaceImage(val dimension: Dimension, val layers: List<List<Int>>)

fun main() {
    val image = parseImage("C:/dev/inputs/image.txt", Dimension(25, 6))
    val fewestZeroDigitsLayer = image.layers.minBy { it.count { pixel -> pixel == 0 } }
    println(fewestZeroDigitsLayer!!.count { it == 1 } * fewestZeroDigitsLayer!!.count { it == 2 })
    printImage(image)
}

fun printImage(image: SpaceImage) {
    for (j in 0 until image.dimension.height) {
        for (i in 0 until image.dimension.width) {
            for (x in image.layers.indices) {
                val pos = j * image.dimension.width + i
                val content = image.layers[x][pos]
                if (content != 2) {
                    val output = when (image.layers[x][pos]) {
                        0 -> '`'
                        1 -> '#'
                        else -> error("Invalid layer value $content")
                    }
                    print(output)
                    break
                }
            }
        }
        println()
    }
}

fun parseImage(path: String, dimension: Dimension): SpaceImage {
    val txt = Files.readAllLines(Paths.get(path))[0]

    val layers = mutableListOf<List<Int>>()
    for (i in txt.indices step dimension.pixelCount()) {
        layers.add(txt.slice(i until i + dimension.pixelCount()).mapIndexed { index, c -> c.toInt() - 48 })
    }

    return SpaceImage(dimension, layers)
}

