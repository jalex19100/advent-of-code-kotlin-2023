import kotlin.math.max

fun main() {
    // return the max number of cubes, by type, from all sets
    fun getMaxCubes(sets: String): Map<String, Int> {
        val red = Regex(" (\\d+) red").findAll(sets).map { it.groupValues[1].toInt() }.max() ?: 0
        val green = Regex(" (\\d+) green").findAll(sets).map { it.groupValues[1].toInt() }.max() ?: 0
        val blue = Regex(" (\\d+) blue").findAll(sets).map { it.groupValues[1].toInt() }.max() ?: 0
        return mapOf("red" to red, "green" to green, "blue" to blue)
    }

    fun part1(input: List<String>): Int {
        if (input.isEmpty()) return 0
        val maxValues = mapOf("red" to 12, "green" to 13, "blue" to 14)
        val (game, sets) = input[0].split(":")
        val gameNum = game.split(" ").last().toInt()
        val maxCubes = getMaxCubes(sets)
        maxValues.forEach { entry ->
            if (entry.value < maxCubes[entry.key]!!) return 0 + part1(input.drop(1))
        }
        return gameNum + part1(input.drop(1))
    }

    fun part2(input: List<String>): Int {
        if (input.isEmpty()) return 0
        val maxCubes = getMaxCubes(input[0].split(":")[1])
        val power = maxCubes.values.reduce { accumulator, element -> accumulator * element }
        return power + part2(input.drop(1))
    }

    // Test input
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    // Data input
    val input = readInput("Day02")
    part1(input).println() // 2239
    part2(input).println() // 83435
}