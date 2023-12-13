enum class RelativeStrength(val uniquCardCounts: List<Int>) {
    HIGH_CARD(listOf(1, 1, 1, 1, 1)), ONE_PAIR(listOf(1, 1, 1, 2)), TWO_PAIR(listOf(1, 2, 2)), THREE_OF_A_KIND(listOf(1, 1, 3)), FULL_HOUSE(
        listOf(
            2, 3
        )
    ),
    FOUR_OF_A_KIND(listOf(1, 4)), FIVE_OF_A_KIND(listOf(5));

    companion object {
        private val map = RelativeStrength.values().associateBy(RelativeStrength::uniquCardCounts)
        fun fromListOfInt(type: List<Int>) = map[type]
    }
}

fun main() {

    val cardIndex1 = listOf('1', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')
    val cardIndex2 = listOf('J', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A')

    data class Hand(val cards: String, val groups: Map<Char, Int>, val bid: Int, val part2: Boolean)

    fun Hand(input: String, part2: Boolean = false): Hand {
        val (cards, bid) = input.split(" ")
        val groups = if (part2 && cards.contains('J')) {
            val highCard = if (cards == "JJJJJ") 'A'
            else cards.replace("J", "").toList().sortedWith(compareBy<Char> { cardIndex2.indexOf(it) }).groupingBy { it }.eachCount()
                .maxBy { it.value }.key
            cards.replace('J', highCard).toList().groupingBy { it }.eachCount()
        } else {
            cards.toList().groupingBy { it }.eachCount()
        }
        return Hand(cards, groups, bid.toInt(), part2)
    }

    fun Hand.getRelativeStrength(): RelativeStrength {
        return RelativeStrength.Companion.fromListOfInt(this.groups.values.sorted())!!
    }

    val handComparator = Comparator { hand1: Hand, hand2: Hand ->
        val relativeComparison = hand1.getRelativeStrength().compareTo(hand2.getRelativeStrength())
        if (relativeComparison != 0) relativeComparison else {
            val cardIndex = if (hand1.part2) cardIndex2 else cardIndex1
            val p = hand1.cards.toList().zip(hand2.cards.toList()).find { (c1, c2) -> cardIndex.indexOf(c1).compareTo(cardIndex.indexOf(c2)) != 0 }
            val bychar = cardIndex.indexOf(p!!.first).compareTo(cardIndex.indexOf(p.second))
            bychar
        }
    }

    fun part1(input: List<String>): Int {
        val hands = input.map { Hand(it) }.sortedWith(handComparator)
        val winnings = hands.foldIndexed(0) { idx, acc, hand -> acc + hand.bid * (idx + 1) }
        return winnings
    }

    fun part2(input: List<String>): Int {
        val hands = input.map { Hand(it, true) }.sortedWith(handComparator)
        val winnings = hands.foldIndexed(0) { idx, acc, hand -> acc + hand.bid * (idx + 1) }
        return winnings
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)
    part1(testInput).println()
    check(part2(testInput) == 5905)
    part2(testInput).println()

    val input = readInput("Day07")
    part1(input).println() // 248113761
    part2(input).println() // 246986313 is too high
}