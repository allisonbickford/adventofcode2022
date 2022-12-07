import java.io.InputStream
import java.util.*
import kotlin.collections.ArrayList

fun main() {
    fun part1(input: List<String>): Int {
        val data = input.first().toList()

        data.windowed(4).forEachIndexed { index, window ->
            if (window.distinct() == window) {
                return index + window.size
            }
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        val data = input.first().toList()
        println("got input $data")

        data.windowed(14).forEachIndexed { index, window ->
            if (window.distinct() == window) {
                return index + window.size
            }
        }
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    val input = readInput("Day06")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}
