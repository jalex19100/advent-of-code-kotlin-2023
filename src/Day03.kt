import kotlin.math.max
import kotlin.math.min

fun main() {

    fun part1(input: List<String>): Int {
        val lineLength = input[0].length
        // println("Line length: $lineLength")
        val numRegex = Regex("\\d+")
        val symbolRegex = Regex("[^\\d.]")
        val text = input.joinToString("")
        val sum = numRegex.findAll(text).fold(0) { acc: Int, matchResult ->
            // println("Match: ${matchResult.value}, from ${matchResult.range.start} to ${matchResult.range.endInclusive}")
            val matchStart = max(0, matchResult.range.start - 1)
            val matchEnd = min(text.length - 1, matchResult.range.endInclusive + 1)
            // println("matchStart: $matchStart, matchEnd: $matchEnd")
            val above = if (matchStart < lineLength - 1) "" else text.subSequence(
                matchStart - lineLength, matchEnd - lineLength + 1
            )
            val around = text.subSequence(matchStart, matchEnd + 1)
            val below = if (text.length - matchEnd < lineLength - 1) "" else text.subSequence(
                matchStart + lineLength, matchEnd + lineLength + 1
            )
            // println("above: \"$above\", around: \"$around\", below: \"$below\"")
            val toAdd =
                if (symbolRegex.containsMatchIn(above) || symbolRegex.containsMatchIn(around) || symbolRegex.containsMatchIn(below)) matchResult.value.toInt() else 0
            acc + toAdd
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val lineLength = input[0].length
        val gearRegex = Regex("\\*")
        val numRegex = Regex("\\d+")
        val text = input.joinToString("")
        val gearRatios = gearRegex.findAll(text).fold(0) { acc: Int, matchResult ->
            println("Match: ${matchResult.value}, from ${matchResult.range.start} to ${matchResult.range.endInclusive}")
            val matchStart = max(0, matchResult.range.start - 1)
            val matchEnd = min(text.length - 1, matchResult.range.endInclusive + 1)
            println("matchStart: $matchStart, matchEnd: $matchEnd")
            val aboveRange = if (matchStart < lineLength - 1) null else IntRange(matchStart - lineLength, matchEnd - lineLength)
            val aroundRange = IntRange(matchStart, matchEnd)
            val belowRange = if (text.length - matchEnd < lineLength - 1) null else IntRange(matchStart + lineLength, matchEnd + lineLength)
            println("above: \"${aboveRange}\", around: \"${aroundRange}\", below: \"${belowRange}\"")
            val potentialGears = numRegex.findAll(text).filter { matchResult ->
                matchResult.range.any { position: Int ->
                    aboveRange?.contains(position) == true || aroundRange.contains(position) || belowRange?.contains(position) == true
                }
            }
            println("Potential Gears: ${potentialGears.toList()}")
            val gearRatio =
                if (potentialGears.count() == 2) (potentialGears.toList()[0].value.toInt() * potentialGears.toList()[1].value.toInt()) else 0
            acc + gearRatio
        }
        return gearRatios
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    part1(testInput).println()
    check(part1(testInput) == 4361)
    part2(testInput).println()
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    check(part1(input) == 532445)
    part2(input).println()
    check(part1(input) == 79842967)
}
