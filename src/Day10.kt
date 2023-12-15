fun main() {

    fun getSurroundingTiles

    fun buildLoop(grid: String, len: Int, s: Int): List<Int> {

        return listOf(0)
    }

    fun part1(input: List<String>): Int {
        val lineLength = input[0].length - 1
        val grid = input.joinToString("")
        val initialPosition = grid.indexOf('S')
        val loop = buildLoop(grid, lineLength, initialPosition)
        return loop.count()/2
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    measureTimeMillis({ time -> println("Part1 ($time ms):") }) {
        part1(testInput)
    }.println()
    measureTimeMillis({ time -> println("Part2 ($time ms):") }) {
        part2(testInput)
    }.println()

    val input = readInput("Day10")
    measureTimeMillis({ time -> println("Part1 ($time ms):") }) {
        part1(input)
    }.println()
    measureTimeMillis({ time -> println("Part2 ($time ms):") }) {
        part2(input)
    }.println()
}
