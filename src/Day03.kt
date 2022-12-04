
fun main() {
    fun part1(input: List<String>): Int {
        val shared = arrayListOf<Char>()
        input.forEach { line ->
            val firstCompartmentItems = line.take(line.length / 2)
            val secondCompartmentItems = line.takeLast(line.length / 2)

            val commonItem = firstCompartmentItems.asIterable().intersect(secondCompartmentItems.toSet())
            shared.add(commonItem.first())
        }

        return shared.sumOf { character ->
            if (character.isLowerCase()) {
                character.code - 96
            } else {
                character.code - 64 + 26
            }
        }
    }

    fun part2(input: List<String>): Int {
        val badges = arrayListOf<Char>()
        for (i in input.indices step 3) {
            val commonItem = input[i].asIterable()
                    .intersect(input[i + 1].toSet())
                    .intersect(input[i + 2].toSet())
            badges.add(commonItem.first())
        }

        return badges.sumOf { character ->
            if (character.isLowerCase()) {
                character.code - 96
            } else {
                character.code - 64 + 26
            }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}
