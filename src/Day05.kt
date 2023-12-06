fun main() {

    fun parseInputMaps(input: List<String>): Map<String, List<String>> {
        val map = mutableMapOf<String, MutableList<String>>()
        var currentFromTo = ""
        for (line in input) {
            if (line.isBlank()) {
                currentFromTo = ""
                continue
            }
            if (line.contains("map")) {
                currentFromTo = line.split("map")[0].trim()
            }
            if (line.any { it.isDigit() }) {
                map.getOrPut(currentFromTo) { mutableListOf() }.add(line)
            }
        }
        map.remove("")
        return map
    }

    fun parseSeeds(input: List<String>): List<Long> {
        return input.first().split(" ").mapNotNull { it.toLongOrNull() }
    }

    fun part1(input: List<String>): Long {
        val seeds = parseSeeds(input)
        val map = parseInputMaps(input)
        var lowest = Long.MAX_VALUE
        for (seed in seeds) {
            var numberInNextCategory = seed
            for ((key, value) in map) {
                for (line in value) {
                    val (destination, source, range) = line.split(" ").mapNotNull { it.toLongOrNull() }
                    val end = source + range
                    val longRange = source..end
                    if (numberInNextCategory in longRange) {
                        print("key: $key [$numberInNextCategory ")
                        numberInNextCategory = destination + longRange.indexOf(numberInNextCategory)
                        print("-> $numberInNextCategory] | ")

                        break
                    }
                }
            }
            println("")
            if (numberInNextCategory < lowest) {
                lowest = numberInNextCategory
            }

        }
        println("lowest: $lowest")

        return lowest
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
