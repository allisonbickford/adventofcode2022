import java.util.*
import kotlin.collections.ArrayList

fun main() {
    fun part1(input: List<String>): String {
        val stacks = input.count { it.contains("[") }
        val crates = input.take(stacks)
        val columns = input[stacks]
        val amountOfColumns = columns.trim().split(" ").last().toInt()
        val crateLists = ArrayList<Stack<String>>(amountOfColumns)

        val windowSize = 4
        crates.forEach { line ->
            for ((column, i) in (0..line.length step windowSize).withIndex()) {
                val windowEnd = if ((i + windowSize) > line.length) line.length else i + windowSize
                val crate = line.substring(i, windowEnd).trim()

                if (crateLists.size <= column) {
                    crateLists.add(Stack())
                }

                if (crate.isNotEmpty()) {
                    crateLists[column].push(crate)
                }
            }
        }
        crateLists.forEach { it.reverse() }

        val instructions = input.filter { it.contains("move") }

        instructions.forEach { instr ->
            //move 1 from 2 to 1
            val pieces = instr.split(" ")
            val amount = pieces[1].toInt()
            val startingCol = pieces[3].toInt() - 1
            val endingCol = pieces[5].toInt() - 1

            for (i in 0 until amount) {
                val crate = crateLists[startingCol].pop()
                crateLists[endingCol].push(crate)
            }
        }

        var result = ""
        for (i in 0 until amountOfColumns) {
            result += crateLists[i].pop().replace("[", "").replace("]", "").trim()
        }
        return result
    }

    fun part2(input: List<String>): String {
        val stacks = input.count { it.contains("[") }
        val crates = input.take(stacks)
        val columns = input[stacks]
        val amountOfColumns = columns.trim().split(" ").last().toInt()
        val crateLists = ArrayList<Stack<String>>(amountOfColumns)

        val windowSize = 4
        crates.forEach { line ->
            for ((column, i) in (0..line.length step windowSize).withIndex()) {
                val windowEnd = if ((i + windowSize) > line.length) line.length else i + windowSize
                val crate = line.substring(i, windowEnd).trim()

                if (crateLists.size <= column) {
                    crateLists.add(Stack())
                }

                if (crate.isNotEmpty()) {
                    crateLists[column].push(crate)
                }
            }
        }
        crateLists.forEach { it.reverse() }

        val instructions = input.filter { it.contains("move") }

        instructions.forEach { instr ->
            //move 1 from 2 to 1
            val pieces = instr.split(" ")
            val amount = pieces[1].toInt()
            val startingCol = pieces[3].toInt() - 1
            val endingCol = pieces[5].toInt() - 1

            val cratesToMove = ArrayList<String>()
            for (i in 0 until amount) {
                val crate = crateLists[startingCol].pop()
                cratesToMove.add(crate)
            }

            cratesToMove.reversed().forEach { crateLists[endingCol].push(it) }
        }

        var result = ""
        for (i in 0 until amountOfColumns) {
            result += crateLists[i].pop().replace("[", "").replace("]", "").trim()
        }
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}
