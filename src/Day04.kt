fun main() {

    fun part1(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            val winningNumbers = line.substringBefore('|').trim().split(" ").mapNotNull { it.toIntOrNull() }
            val myNumbers = line.substringAfter('|').trim().split(" ").mapNotNull { it.toIntOrNull() }

            val matches = winningNumbers.filter { myNumbers.contains(it) }
            var cardSum = 0
            for (match in matches) {
                cardSum *= 2
                if (cardSum == 0) cardSum += 1
            }
            sum += cardSum
        }
        println("sum: $sum")
        return sum
    }

    var recursionCount = 0
    fun scratch(input: List<String>, index: Int, addToSum: () -> Int) {
        recursionCount++
        // visualise recursion
        println("-----|".repeat(recursionCount) + "scratch: ${index + 1}")

        val card = input[index]
        val winningNumbers = card.substringBefore('|').trim().split(" ").mapNotNull { it.toIntOrNull() }
        val myNumbers = card.substringAfter('|').trim().split(" ").mapNotNull { it.toIntOrNull() }

        val matches = winningNumbers.filter { myNumbers.contains(it) }
        for (i in 1 until matches.size+1) {
            scratch(input, index + i, addToSum)
        }

        addToSum()
        recursionCount--
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        val addToSum = fun () = sum++
        for ((index, _) in input.withIndex()) {
            scratch(input, index, addToSum)
        }
        println("sum: $sum")
        return sum
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test").map { it.substringAfter(':') }
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
