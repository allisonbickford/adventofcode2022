fun main() {
    fun part1(input: List<String>): Int {
        var amount = 0
        var max = 0
        for (i in input) {
            if (i.isEmpty()) {
                if (amount > max) {
                    max = amount
                }
                amount = 0
            } else if (i.isNotEmpty()) {
                amount += i.toInt()
            }
        }

        return max
    }

    fun part2(input: List<String>): Int {
        val amounts = arrayListOf<Int>()
        var current = 0
        for (i in input) {
            if (i.isEmpty()) {
                amounts.add(current)
                current = 0
            } else {
                current += i.toInt()
            }
        }

        return amounts.sortedDescending().take(3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day1test")
    check(part1(testInput) == 24000)

    val input = readInput("Day01")
    println("Part 1:")
    println(part1(input))
    println("Part 2:")
    println(part2(input))
}
