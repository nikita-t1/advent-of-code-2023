
fun main() {

    fun Int.isPositiveOrElse(orElse: Int): Int {
        return if (this > 0) this else orElse
    }

    fun Int.isInRangeOrElse(start: Int, end: Int, orElse: Int): Int {
        return if (this in start..end) this else orElse
    }

    fun checkSurrounding(input: List<String>, lineNumber: Int, numberOffset: Int, numberLength: Int): Boolean {
        var check = false

        val padWithDots = {length: Int -> ".".repeat(length)}

        val topLine: String = input.getOrElse(lineNumber-1) { padWithDots(input[0].length) }
        val currentLine: String = input.getOrNull(lineNumber)!!
        val bottomLine: String = input.getOrElse(lineNumber+1) { padWithDots(input[0].length) }

        // println("numberOffset: $numberOffset, numberLength: $numberLength")
        val start = (numberOffset-numberLength - 1).isPositiveOrElse(0)
        val end = (numberOffset+1).isInRangeOrElse(0, currentLine.length, currentLine.length-1)

        // search top line
        if (lineNumber > 0) {
            for(i in start until end) {
                    val topLineChar = topLine[i]
                    if (topLineChar != '.' && topLineChar.isDigit().not()) {
                        check = true
                    }
            }
        }

        // search current line
        if (currentLine[start] != '.' && currentLine[start].isDigit().not()) check = true
        if (currentLine[end-1] != '.' && currentLine[end-1].isDigit().not()) check = true

        // search bottom line
        if (lineNumber < input.size) {
            for(i in start until end) {
                    val bottomLineChar = bottomLine[i]
                    if (bottomLineChar != '.' && bottomLineChar.isDigit().not()) {
                        check = true
                    }
            }
        }

        return check
    }

    fun part1(input: List<String>): Int {
        val inputWithDots = input.map { ".$it." }
        var sum = 0
        for ((index, line) in inputWithDots.withIndex()) {
            var number = ""
            var numberOffset = 0
            for (char in line) {
                numberOffset++
                if (char.isDigit()) {
                    number += char.toString()
                } else if (number.isNotEmpty()) {
                    println("number: $number")
                    val check = checkSurrounding(inputWithDots, index, numberOffset-1, number.length)
                    if (check) {
                        sum += number.toInt()
                    }
                    number = ""
                }
            }
        }
        println("sum: $sum")
        return sum
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)

    val input = readInput("Day03")
    part1(input).println()
//    part2(input).println()
}
