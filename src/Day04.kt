import kotlin.math.log

fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            val winningNumbers= line.substringBefore('|').trim().split(" ").mapNotNull { it.toIntOrNull() }
            val myNumbers = line.substringAfter('|').trim().split(" ").mapNotNull { it.toIntOrNull() }

            val matches = winningNumbers.filter { myNumbers.contains(it) }
            var cardSum = 0
            for (match in matches) {
                cardSum *= 2
                if (cardSum == 0) cardSum+=1
            }
            sum += cardSum

        }
        println("sum: $sum")
        return sum
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test").map { it.substringAfter(':') }
    check(part1(testInput) == 13)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
