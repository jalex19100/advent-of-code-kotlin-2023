fun main() {

    fun extendSequence(history: List<Long>): List<Long> {
        if (history.all { it == 0L }) return history + 0
        val differences = history.windowed(2) { (x, y) -> y - x }
        return history + (history.last() + extendSequence(differences).last())
    }

    fun part1(input: List<String>): Long {
        return input.map { line ->
            val firstHistory = line.split(" ").map { it.toLong() }
            extendSequence(firstHistory).last()
        }.reduce { acc, n -> acc + n }
    }

    fun part2(input: List<String>): Long {
        return input.map { line ->
            val firstHistory = line.split(" ").map { it.toLong() }.reversed()
            extendSequence(firstHistory).last()
        }.reduce { acc, n -> acc + n }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    measureTimeMillis({ time -> println("Part1 ($time ms):") }) {
        part1(testInput)
    }.println() //114
    measureTimeMillis({ time -> println("Part2 ($time ms):") }) {
        part2(testInput)
    }.println()

    val input = readInput("Day09")
    measureTimeMillis({ time -> println("Part1 ($time ms):") }) {
        part1(input)
    }.println()
    measureTimeMillis({ time -> println("Part2 ($time ms):") }) {
        part2(input)
    }.println()
}
