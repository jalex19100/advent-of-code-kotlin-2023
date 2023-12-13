import kotlin.math.pow

fun main() {
    fun findMatches(s: String): Set<String> {
        val numRegex = Regex("\\d+")
        val winningNumbers = numRegex.findAll(s.split(":")[1].split("|")[0]).map { it.value }.toList()
        val scratchedNumbers = numRegex.findAll(s.split(":")[1].split("|")[1]).map { it.value }.toList()
        // println("winningNumbers: $winningNumbers, scratchNumbers: $scratchedNumbers")
        return winningNumbers.intersect(scratchedNumbers.toSet())
    }

    fun part1(input: List<String>): Int {
        if (input.isEmpty()) return 0
        val matches = findMatches(input[0])
        // println("matches: $matches")
        val baseInt = 2
        val power = matches.count() - 1
        return baseInt.toDouble().pow(power.toDouble()).toInt() + part1(input.drop(1))
    }

    fun part2(input: List<String>, wonCopyCounters: Map<Int, Int> = emptyMap()): Int {
        if (input.isEmpty()) return 0
        val matchCount = findMatches(input[0]).count()
        val scratchCardCount = 1 + wonCopyCounters.keys.sum()
        val addedCopyCounters = if (matchCount > 0) mapOf(scratchCardCount to matchCount) else emptyMap()
        // println("matchCount: ${matchCount}, wonCopies: $wonCopyCounters, cards: $scratchCardCount")
        val decrementedWonCopyCounters =
            (if (wonCopyCounters.isNotEmpty()) wonCopyCounters.mapValues { it.value - 1 } else wonCopyCounters).filter { it.value > 0 }
        return scratchCardCount + part2(input.drop(1), decrementedWonCopyCounters + addedCopyCounters)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    part1(testInput).println()
    check(part1(testInput) == 13)
    part2(testInput).println()
    check(part2(testInput) == 30)

    println()
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}