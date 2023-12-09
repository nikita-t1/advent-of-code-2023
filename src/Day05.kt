import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicReference
import kotlin.system.measureTimeMillis

fun main() {

    data class MapRange(val source: LongRange, val destination: LongRange)
    data class MapRangeBlock(var name: String, var ranges: List<MapRange>)
    class SeedLocationPair(val seedRange: LongRange, val locationRange: LongRange)

    fun parseInputMaps(input: List<String>): List<MapRangeBlock> {
        val almanac = mutableListOf<MapRangeBlock>()
        var mapRangeBlock = MapRangeBlock("", listOf())
        for (line in input) {
            if (line.isBlank()) {
                almanac.add(mapRangeBlock.copy())
                mapRangeBlock = MapRangeBlock("", listOf())
                continue
            }
            if (line.contains("map")) {
                mapRangeBlock.name = line.split("map")[0].trim()
            }
            if (line.any { it.isDigit() }) {
                val (destination, source, range) = line.split(" ").mapNotNull { it.toLongOrNull() }
                val sourceRange = source..(source + range)
                val destinationRange = destination..<(destination + range)
                val pair = MapRange(sourceRange, destinationRange)
                mapRangeBlock.ranges += pair
            }
        }
        // add last block
        almanac.add(mapRangeBlock.copy())
        mapRangeBlock = MapRangeBlock("", listOf())
        return almanac.filter { it.name.isNotBlank() }
    }

    fun LongRange.indexOf(number: Long): Long {
        val index = this.firstNotNullOf { sourceNumber ->
            if (sourceNumber == number) {
                return@firstNotNullOf sourceNumber
            } else {
                return@firstNotNullOf null
            }
        }
        return index - this.first
    }

    fun seedToLocation(seed: Long, almanac: List<MapRangeBlock>): Long {
        var numberInNextCategory = seed
        for (block in almanac) {
            for (pair in block.ranges) {
                if (numberInNextCategory in pair.source) {
                    val index = pair.source.indexOf(numberInNextCategory)
                    numberInNextCategory = pair.destination.first + index
                    break
                }
            }
        }

        return numberInNextCategory
    }

    fun locationToSeed(location: Long, almanac: List<MapRangeBlock>): Long {
        var numberInNextCategory = location
        for (block in almanac.reversed()) {
            for (pair in block.ranges) {
                if (numberInNextCategory in pair.destination) {
                    val index = pair.destination.indexOf(numberInNextCategory)
                    numberInNextCategory = pair.source.first + index
                    break
                }
            }
        }

        return numberInNextCategory
    }

    fun parseInputSeedsPartOne(input: List<String>): List<Long> {
        return input.first().split(" ").mapNotNull { it.toLongOrNull() }
    }


    fun parseInputSeedsPartTwo(input: List<String>): Set<LongRange> {
        return input.first().substringAfter(":").trim().split(" ")
                .map { it.toLong() }.chunked(2).map {
                    it.first()..<it.first() + it.last()
                }.toSet()
    }

    fun part1(input: List<String>): Long {
        val seeds = parseInputSeedsPartOne(input)
        val map = parseInputMaps(input)
        var lowest = Long.MAX_VALUE
        for (seed in seeds) {
            val numberInNextCategory = seedToLocation(seed, map)
            if (numberInNextCategory < lowest) {
                lowest = numberInNextCategory
            }
        }
        return lowest
    }

    fun findMutualRange(seed: Long, location: Long, almanac: List<MapRangeBlock>): SeedLocationPair {
        // binary search
        var low = seed
        var high = Long.MAX_VALUE
        while (low <= high) {
//            println("low: $low | high: $high")
            val mid = (low + high) / 2
            val loc = seedToLocation(seed + mid, almanac)
            if (loc == location + mid) {
                low = mid + 1
            } else {
                high = mid - 1
            }
        }
        val rangeCheck = low - 1
        println("rangeCheck: $rangeCheck")
        // RETURN PAIR OF RANGE [SEED, LOCATION]
        return SeedLocationPair((seed..(seed + rangeCheck)), location..(location + rangeCheck))
    }

    fun part2(input: List<String>): Long {
        val seedsRanges = parseInputSeedsPartTwo(input)
        val almanac = parseInputMaps(input)
        val reversedRange = almanac.map { range -> range.ranges.map { MapRange(it.destination, it.source) } }.reversed()
        return generateSequence(0L) { it.inc() }
                .first { location ->
                    val seed = reversedRange.fold(location) { accumulator, ranges ->
                        ranges
                                .firstOrNull { accumulator in it.source }
                                ?.let {
                                    it.destination.first + (accumulator - it.source.first)
                                } ?: accumulator
                    }
                    seedsRanges.any { seedRange -> seed in seedRange }
                }
    }
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
