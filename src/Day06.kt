fun main() {

    data class Race(val timeLimit: Long, val recordDistance: Long)

    fun Race.getValidScenarios(): Map<Long, Long> {
        val scenarios = (1..<timeLimit).associateWith { chargeTime ->
            chargeTime * (timeLimit - chargeTime)
        }.filter { it.value > recordDistance }
        return scenarios
    }

    fun Race.getNumberOfValidScenarios(): Long {
        val minChargeTime = (1..<timeLimit).first { chargeTime ->
            (chargeTime * (timeLimit - chargeTime)) > recordDistance
        }
        val maxChargeTime = (1..<timeLimit).reversed().first { chargeTime ->
            (chargeTime * (timeLimit - chargeTime)) > recordDistance
        }
        return maxChargeTime - minChargeTime + 1
    }

    fun part1(input: List<String>): Int {
        val times = input[0].substringAfter("Time:").split(" ").filter { it.isNotBlank() }.map { it.toLong() }
        val distances = input[1].substringAfter("Distance:").split(" ").filter { it.isNotBlank() }.map { it.toLong() }
        val races = times.zip(distances).map { (time, distance) -> Race(time, distance) }
        return races.fold(1) { acc, race -> acc * race.getValidScenarios().count() }
    }

    fun part2(input: List<String>): Long {
        val time = input[0].substringAfter("Time:").replace(" ", "").toLong()
        val distance = input[1].substringAfter("Distance:").replace(" ", "").toLong()
        val race = Race(time, distance)
        return race.getNumberOfValidScenarios()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    println("part1: ${part1(testInput)}")
    println("part2: ${part2(testInput)}")
    check(part1(testInput) == 288)
    check(part2(testInput) == 71503L)

    val input = readInput("Day06")
    println("part1: ${part1(input)}")
    println("part2: ${part2(input)}")
}