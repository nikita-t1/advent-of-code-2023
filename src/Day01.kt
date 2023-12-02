fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEach { line ->
            val lineWithoutLetters = line.filter { it.digitToIntOrNull() != null }
            val lineValue = (lineWithoutLetters.first().toString() + lineWithoutLetters.last().toString()).toInt()
            sum += lineValue
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 1)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
