
fun main() {
    fun getRangeFromText(input: String): IntRange {
        return input.split("-").first().toInt().rangeTo(
                input.split("-").last().toInt()
        )
    }

    fun part1(input: List<String>): Int {
        val contained = input.count { line ->
            val firstStart = line.split(",").first()
            val secondStart = line.split(",").last()

            val firstRange = getRangeFromText(firstStart)
            val secondRange = getRangeFromText(secondStart)

            (firstRange.first <= secondRange.first && firstRange.last >= secondRange.last) ||
                (secondRange.first <= firstRange.first && secondRange.last >= firstRange.last)
        }

        return contained
    }

    fun part2(input: List<String>): Int {
        val contained = input.count { line ->
            val firstStart = line.split(",").first()
            val secondStart = line.split(",").last()

            val firstRange = getRangeFromText(firstStart)
            val secondRange = getRangeFromText(secondStart)

            firstRange.intersect(secondRange).isNotEmpty()
        }

        return contained
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}
