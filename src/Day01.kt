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
        var sum = 0
        val lettersToDigits = mapOf(
                "one" to "1",
                "two" to "2",
                "three" to "3",
                "four" to "4",
                "five" to "5",
                "six" to "6",
                "seven" to "7",
                "eight" to "8",
                "nine" to "9",
                "zero" to "0"
        )
        for (line in input) {
            println("line -> $line")

            var forward: String = ""
            for (value in line.indices){
                forward += line[value]
                val key: String? = lettersToDigits.keys.firstOrNull { key -> forward.contains(key) }
                if (key != null) {
                    val new = forward.replace(key, lettersToDigits[key]!!)
                    forward = line.replaceRange(0, forward.length, new)
                    break
                }
            }
            println("forward -> $forward")

            var backward: String = ""
            for (i in line.length-1 downTo 0){
                backward = line[i] + backward
                val key: String? = lettersToDigits.keys.firstOrNull { key -> backward.contains(key) }
                if (key != null) {
                    val new = backward.replace(key, lettersToDigits[key]!!)
                    backward = line.replaceRange(line.length-backward.length, line.length, new)
                    break
                }
            }

            println("backward -> $backward")

            val firstDigit = (forward.firstOrNull { it.digitToIntOrNull() != null } ?: '0')
            val lastDigit = (backward.lastOrNull { it.digitToIntOrNull() != null } ?: '0')
            val lineValue = (firstDigit.toString() + lastDigit.toString()).toInt()
            println("$firstDigit + $lastDigit = $lineValue")
            println("-----")
            sum += lineValue
        }
        println(sum)
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)
    check(part2(readInput("Day01_test2")) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
