import kotlinx.coroutines.withTimeoutOrNull

fun main() {


    val conversionTable = mapOf(
            "A" to 14,
            "K" to 13,
            "Q" to 12,
            "J" to 11,
            "T" to 10,
            "9" to 9,
            "8" to 8,
            "7" to 7,
            "6" to 6,
            "5" to 5,
            "4" to 4,
            "3" to 3,
            "2" to 2,
    )

    val typesOfHands = mapOf(
            "Five of a Kind" to 7,
            "Four of a Kind" to 6,
            "Full House" to 5,
            "Three of a Kind" to 4,
            "Two Pairs" to 3,
            "One Pair" to 2,
            "High Card" to 1,
    )

    data class Hand(val cards: String, val bid: Int) {

        val values = cards.map { conversionTable[it.toString()] ?: 0 }
        var rank = 0
        var winAmount = 0
        fun typeOfHand(): Int {
            var result = 1
            // Five of a Kind
            if (values.groupBy { it }.size == 1) return 7
            // Four of a Kind
            if (values.groupBy { it }.any { it.value.size == 4 }) return 6
            // Full House
            if (values.groupBy { it }.any { it.value.size == 3 } && values.groupBy { it }.any { it.value.size == 2 }) return 5
            // Three of a Kind
            if (values.groupBy { it }.any { it.value.size == 3 }) return 4
            // Two Pairs
            if (values.groupBy { it }.filter { it.value.size == 2 }.size == 2) return 3
            // One Pair
            if (values.groupBy { it }.any { it.value.size == 2 }) return 2
            // High Card
            return 1
        }
    }

    fun calcAllPossibilities(): List<String> {
        val possibilities = mutableListOf<String>()
        for (entry1 in conversionTable) {
            for (entry2 in conversionTable) {
                for (entry3 in conversionTable) {
                    for (entry4 in conversionTable) {
                        for (entry5 in conversionTable) {
                            possibilities.add("${entry1.key}${entry2.key}${entry3.key}${entry4.key}${entry5.key}")
                        }
                    }
                }
            }
        }
        return possibilities
    }
    val allPossibilities = calcAllPossibilities()

    fun parseInput(input: List<String>): List<Hand> {
        val hands = mutableListOf<Hand>()
        for (line in input) {
            val cards = line.split(" ")[0].trim()
            val bid = line.split(" ")[1].trim().toIntOrNull() ?: 0
            hands.add(Hand(cards, bid))
        }
        return hands
    }

    fun part1(input: List<String>): Int {
        val allValues = allPossibilities
        val hands = parseInput(input)
        val handsGroupedByType = hands.groupBy { it.typeOfHand() }.toSortedMap().reverse()
        val handsGroupedByRank = handsGroupedByType.map {
            it.value.sortedBy {allPossibilities.indexOf(it.cards)}.also { println(it) }
        }.flatten()


        println("handsGroupedByType: $handsGroupedByType")
        println("handsGroupedByRank: $handsGroupedByRank")
        handsGroupedByRank.println()
        val withRanks = handsGroupedByRank.reversed().mapIndexed { index, hand ->
            hand.rank = index + 1
            hand.winAmount = hand.bid * hand.rank
            hand
        }
        withRanks.forEach { println("${it.cards} | ${it.typeOfHand()} | ${it.rank} | winAmount ${it.winAmount}") }
        println("result: $withRanks")
        return withRanks.sumOf { it.winAmount }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}

private fun <K, V> Map<K, V>.reverse(): Map<K, V> {
    return this.toList().reversed().toMap()
}
