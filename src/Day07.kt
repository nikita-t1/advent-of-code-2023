fun main() {


    val conversionTableOne = mutableMapOf(
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

    val conversionTableTwo = conversionTableOne.apply {
        // remove J and add it to the end of the map to make it the lowest value
        remove("J")
        put("J", 1)
    }

    data class Hand(val cards: String, val bid: Int) {

        val values = cards.map { conversionTableOne[it.toString()] ?: 0 }
        val values2 = cards.map { conversionTableTwo[it.toString()] ?: 0 }
        var rank = 0
        var winAmount = 0

        fun typeOfHand(cards: List<Int> = values): Int {
            var result = 1
            // Five of a Kind
            if (cards.groupBy { it }.size == 1) return 7
            // Four of a Kind
            if (cards.groupBy { it }.any { it.value.size == 4 }) return 6
            // Full House
            if (cards.groupBy { it }.any { it.value.size == 3 } && cards.groupBy { it }.any { it.value.size == 2 }) return 5
            // Three of a Kind
            if (cards.groupBy { it }.any { it.value.size == 3 }) return 4
            // Two Pairs
            if (cards.groupBy { it }.filter { it.value.size == 2 }.size == 2) return 3
            // One Pair
            if (cards.groupBy { it }.any { it.value.size == 2 }) return 2
            // High Card
            return 1
        }

        fun typeOfHandWithJoker(): Int {
            var typeOfHand = typeOfHand(values2)

            val getLabelFromNumber = fun(number: Int): String {
                return conversionTableTwo.filter { it.value == number }.keys.first()
            }

            val jokerIndexes = values2.mapIndexed { index, i -> if (i == 1) index else -1 }.filter { it != -1 }
            println("jokerIndexes: $jokerIndexes")
            var cards = cards

            for (i in conversionTableTwo.values) {
                if (jokerIndexes.isEmpty()) continue
                cards = cards.replaceRange(jokerIndexes[0], jokerIndexes[0] + 1, getLabelFromNumber(i))
                println("ccccards: $cards | $i")

                for (j in conversionTableTwo.values) {
                    if (jokerIndexes.size < 2) continue
                    cards = cards.replaceRange(jokerIndexes[1], jokerIndexes[1] + 1, getLabelFromNumber(j))

                    for (k in conversionTableTwo.values) {
                        if (jokerIndexes.size < 3) continue
                        cards = cards.replaceRange(jokerIndexes[2], jokerIndexes[2] + 1, getLabelFromNumber(k))

                        for (l in conversionTableTwo.values) {
                            if (jokerIndexes.size < 4) continue
                            cards = cards.replaceRange(jokerIndexes[3], jokerIndexes[3] + 1, getLabelFromNumber(l))

                            for (m in conversionTableTwo.values) {
                                if (jokerIndexes.size < 5) continue
                                cards = cards.replaceRange(jokerIndexes[4], jokerIndexes[4] + 1, getLabelFromNumber(m))
                                val newTypeOfHand = typeOfHand(cards.map { conversionTableTwo[it.toString()] ?: 0 })
                                if (newTypeOfHand > typeOfHand) typeOfHand = newTypeOfHand

                            }
                            val newTypeOfHand = typeOfHand(cards.map { conversionTableTwo[it.toString()] ?: 0 })
                            if (newTypeOfHand > typeOfHand) typeOfHand = newTypeOfHand

                        }
                        val newTypeOfHand = typeOfHand(cards.map { conversionTableTwo[it.toString()] ?: 0 })
                        if (newTypeOfHand > typeOfHand) typeOfHand = newTypeOfHand

                    }
                    val newTypeOfHand = typeOfHand(cards.map { conversionTableTwo[it.toString()] ?: 0 })
                    if (newTypeOfHand > typeOfHand) typeOfHand = newTypeOfHand

                }
                val newTypeOfHand = typeOfHand(cards.map { conversionTableTwo[it.toString()] ?: 0 })
                if (newTypeOfHand > typeOfHand) typeOfHand = newTypeOfHand

            }
            return typeOfHand

        }
    }

    fun calcAllPossibilities(conversionTable: Map<String, Int>): List<String> {
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

    val allPossibilitiesOne = calcAllPossibilities(conversionTableOne)
    val allPossibilitiesTwo = calcAllPossibilities(conversionTableTwo)

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
        val hands = parseInput(input)
        val handsGroupedByType = hands.groupBy { it.typeOfHand() }.toSortedMap().reverse()
        val handsGroupedByRank = handsGroupedByType.map {
            it.value.sortedBy { allPossibilitiesOne.indexOf(it.cards) }.also { println(it) }
        }.flatten()


        println("handsGroupedByType: $handsGroupedByType")
        println("handsGroupedByRank: $handsGroupedByRank")
        handsGroupedByRank.println()
        val withRanks = handsGroupedByRank.reversed().mapIndexed { index, hand ->
            hand.rank = index + 1
            hand.winAmount = hand.bid * hand.rank
            hand
        }
        withRanks.forEach { println("cards ${it.cards} | type ${it.typeOfHand()} | rank ${it.rank} | bid ${it.bid} | winAmount ${it.winAmount}") }
        println("result: $withRanks")
        return withRanks.sumOf { it.winAmount }
    }

    fun part2(input: List<String>): Int {
        val hands = parseInput(input)
        val handsGroupedByType = hands.groupBy { it.typeOfHandWithJoker() }.toSortedMap().reverse()
        val handsGroupedByRank = handsGroupedByType.map {
            it.value.sortedBy { allPossibilitiesTwo.indexOf(it.cards) }.also { println(it) }
        }.flatten()


        println("handsGroupedByType: $handsGroupedByType")
        println("handsGroupedByRank: $handsGroupedByRank")
        val withRanks = handsGroupedByRank.reversed().mapIndexed { index, hand ->
            hand.rank = index + 1
            hand.winAmount = hand.bid * hand.rank
            hand
        }
        withRanks.forEach { println("cards ${it.cards} | type ${it.typeOfHand()} | rank ${it.rank} | bid ${it.bid} | winAmount ${it.winAmount}") }
        println("result: $withRanks")
        val result = withRanks.sumOf { it.winAmount }
        println("result2: $result")
        return result
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)
    check(part2(testInput) == 5905)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}

private fun <K, V> Map<K, V>.reverse(): Map<K, V> {
    return this.toList().reversed().toMap()
}
