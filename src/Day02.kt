
enum class Scoring(val value: Int) {
    Lose(0),
    Draw(3),
    Win(6)
}

fun main() {
    fun part1(input: List<String>): Int {
        val shapes = mapOf(
                'A' to 1, // rock
                'B' to 2, // paper
                'C' to 3 // scissors
        )
        val responses = mapOf(
                'X' to 1, // rock
                'Y' to 2, // paper
                'Z' to 3 // scissors
        )
        val wins = mapOf(
                1 to 3,
                2 to 1,
                3 to 2,
        )

        var total = 0
        input.forEach { line ->
            val played = shapes[line.first()]
            val response = responses[line.trim().last()]

            val outcome: Scoring = when {
                played == response -> Scoring.Draw
                wins.entries.find { it.key == response && it.value == played } != null -> Scoring.Win
                else -> Scoring.Lose
            }

            total += (response ?: 0) + outcome.value
        }

        return total
    }

    fun part2(input: List<String>): Int {
        val shapes = mapOf(
                'A' to 1, // rock
                'B' to 2, // paper
                'C' to 3 // scissors
        )
        val results = mapOf(
                'X' to Scoring.Lose,
                'Y' to Scoring.Draw,
                'Z' to Scoring.Win
        )
        val wins = mapOf(
                1 to 3,
                2 to 1,
                3 to 2,
        )

        var total = 0
        input.forEach { line ->
            val played = shapes[line.first()]
            val desiredResult = results[line.trim().last()]

            val shape = when(desiredResult) {
                Scoring.Draw -> played
                Scoring.Lose -> wins.entries.find { it.key == played }?.value
                Scoring.Win -> wins.entries.find { it.value == played }?.key
                else -> 0
            }

            total += (shape ?: 0) + (desiredResult?.value ?: 0)
        }

        return total
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}
