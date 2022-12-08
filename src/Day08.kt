import java.io.InputStream
import java.util.*
import kotlin.collections.ArrayList

enum class Direction(val horizontal: Int = 0, val vertical: Int = 0) {
    Up(vertical = -1),
    Down(vertical = 1),
    Left(horizontal = -1),
    Right(horizontal = 1)
}

fun main() {

    fun traverseDirectionAndCheckVisible(input: List<String>, direction: Direction, x: Int, y: Int, value: Int): Boolean {
        if (x <= 0 || y <= 0 || y >= input.size - 1 || x >= input[y].length - 1) { // check if we are on the edges
            return input[y][x].digitToInt() < value
        }

        return input[y][x].digitToInt() < value && // is this a short tree? otherwise keep moving
            traverseDirectionAndCheckVisible(input, direction, x + direction.horizontal, y + direction.vertical, value)
    }

    fun traverseDirectionAndCheckScenery(input: List<String>, direction: Direction, x: Int, y: Int, value: Int, score: Int = 0, blocked: Boolean = false): Int {
        if (blocked || x < 0 || y < 0 || y > input.size - 1 || x > input[y].length - 1) { // check if we are on the edges
            return score
        }

        return traverseDirectionAndCheckScenery(input, direction,
                        x + direction.horizontal, y + direction.vertical, value, score + 1, input[y][x].digitToInt() >= value)
    }

    fun part1(input: List<String>): Int {
        var visible = input.first().count() + input.last().count()
        input.forEachIndexed { lineNumber, line ->
            if (lineNumber != 0 && lineNumber < input.size - 1) {
                line.forEachIndexed { index, height ->
                    if (index == 0 || index == line.length - 1) {
                        visible++
                    } else {
                        val isVisible = traverseDirectionAndCheckVisible(input, Direction.Down, index, lineNumber + 1, input[lineNumber][index].digitToInt())
                                || traverseDirectionAndCheckVisible(input, Direction.Up, index, lineNumber - 1, input[lineNumber][index].digitToInt())
                                || traverseDirectionAndCheckVisible(input, Direction.Left, index - 1, lineNumber, input[lineNumber][index].digitToInt())
                                || traverseDirectionAndCheckVisible(input, Direction.Right, index + 1, lineNumber, input[lineNumber][index].digitToInt())
                        if (isVisible) {
                            visible++
                        }
                    }
                }
            }
        }

        println("found $visible visible trees")

        return visible
    }

    fun part2(input: List<String>): Int {
        var score = 0
        input.forEachIndexed { lineNumber, line ->
            line.forEachIndexed { index, height ->
                val downScore = traverseDirectionAndCheckScenery(input, Direction.Down, index, lineNumber + 1, height.digitToInt())
                val upScore = traverseDirectionAndCheckScenery(input, Direction.Up, index, lineNumber - 1, height.digitToInt())
                val leftScore = traverseDirectionAndCheckScenery(input, Direction.Left, index - 1, lineNumber, height.digitToInt())
                val rightScore = traverseDirectionAndCheckScenery(input, Direction.Right, index + 1, lineNumber, height.digitToInt())

                val currentScore = downScore * upScore * leftScore * rightScore
                if (currentScore > 0) {
                    println("checking $height @ ($index, $lineNumber), score = (up = $upScore, down = $downScore, left = $leftScore, right = $rightScore), total = $currentScore")
                }
                if (currentScore > score) {
                    score = currentScore
                }
            }
        }

        println("max score = $score")
        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
//    println("Part 1:")
//    println(part1(input))
    println("Part 2:")
    println(part2(input))
}
