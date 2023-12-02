fun main() {

    fun part1(input: List<String>): Int {
        var sum = 0
        val condition = mapOf(
                "red" to 12,
                "green" to 13,
                "blue" to 14
        )
        for ((index, line) in input.withIndex()) {
            // remove game id
            // 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
            val s = line.substringAfter(":")

            // split into sets
            // 3 blue, 4 red
            val sets = s.split(";")
            println("sets: ${sets.map {  "$it | " }}")

            var validSet = true
            for (set in sets) {
                val cubes = set.split(",")
                cubes.forEach {
                    val split = it.trim().split(" ")
                    val amount = split[0].toInt()
                    val color = split[1]
                    println("amount: $amount, color: $color")
                    if (condition[color]!! < amount) {
                        validSet = false
                    }
                }

            }
            if (validSet) {
                sum += index+1
            }
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        val condition = mapOf(
                "red" to 12,
                "green" to 13,
                "blue" to 14
        )
        for ((index, line) in input.withIndex()) {
            // remove game id
            // 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
            val s = line.substringAfter(":")

            // split into sets
            // 3 blue, 4 red
            val sets = s.split(";")
            println("sets: ${sets.map {  "$it | " }}")

            var green = 0
            var red = 0
            var blue = 0
            for (set in sets) {
                val cubes = set.split(",")
                cubes.forEach {
                    val split = it.trim().split(" ")
                    val amount = split[0].toInt()
                    val color = split[1]
                    println("amount: $amount, color: $color")
                    if (color == "green" && green < amount) green = amount
                    if (color == "red" && red < amount) red = amount
                    if (color == "blue" && blue < amount) blue = amount
                }

            }
            sum += (green * red * blue)
        }

        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
