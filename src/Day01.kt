fun main() {
    // retain before/after character sequences while adding the numerical replacement
    val numMap =
        mapOf("one" to "one1one", "two" to "two2two", "three" to "three3three", "four" to "four4four",
            "five" to "five5five", "six" to "six6six", "seven" to "seven7seven", "eight" to "eight8eight",
            "nine" to "nine9nine")

    fun part1(input: List<String>): Int {
        if (input.isNotEmpty()) {
            val digits = input[0].toCharArray().filter() { it.isDigit() }
            return "${digits.first()}${digits.last()}".toInt() + part1(input.drop(1))
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        val updatedInput = input.map() { line ->
            numMap.entries.fold(line) { accumulator, (key, value) -> accumulator.replace(key, value) }
        }
        return part1(updatedInput)
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)
    val testInput2 = readInput("Day01_test2")
    check(part2(testInput2) == 281)


    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
