fun main() {
    data class Race(val time: Long, val distance: Long) {

        val possibilities = (1..time).map { boatSpeed(it) }
        val winningPossibility = possibilities.filter { it > distance }
        fun boatSpeed(holdDuration: Long): Long {
            val remainingTravelTime = (time - holdDuration)
            return remainingTravelTime * holdDuration
        }
    }

    fun parseInputPartOne(input: List<String>): List<Race> {
        val times = input[0].split(" ").mapNotNull { it.toLongOrNull() }
        val distances = input[1].split(" ").mapNotNull { it.toLongOrNull() }

        val races = mutableListOf<Race>()
        println("times: $times | distances: $distances")
        for (i in times.indices) {
            races.add(Race(times[i], distances[i]))
        }
        return races
    }

    fun Long.toList(): List<Long> {
        return listOf(this)
    }

    fun parseInputPartTwo(input: List<String>): List<Race> {
        val times = input[0].replace(" ", "").filter { it.isDigit() }.toLong().toList()
        val distances = input[1].replace(" ", "").filter { it.isDigit() }.toLong().toList()

        val races = mutableListOf<Race>()
        println("times: $times | distances: $distances")
        for (i in times.indices) {
            races.add(Race(times[i], distances[i]))
        }
        return races
    }

    fun part1(input: List<String>): Int {
        val races = parseInputPartOne(input)
        var result = 1
        for (race in races) {
            val winningPossibilities = race.winningPossibility
            winningPossibilities.println()
            result *= winningPossibilities.size
        }
        result.println()
        return result
    }


    fun part2(input: List<String>): Int {
        val races = parseInputPartTwo(input)
        var result = 1
        for (race in races) {
            val winningPossibilities = race.winningPossibility
            winningPossibilities.println()
            result *= winningPossibilities.size
        }
        result.println()
        return result    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
