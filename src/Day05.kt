import kotlin.math.min

fun main() {
    data class SparseMapping(
        val destStart: Long, val sourceStart: Long, val range: Long
    )

    fun SparseMapping.getDestination(source: Long): Long {
        if (source > sourceStart + (range - 1) || source < sourceStart) return -1
        return destStart + (source - sourceStart)
    }

    fun SparseMapping(input: String): SparseMapping {
        val values = input.split(' ').filter { it.isNotBlank() }.map { it.toLong() }
        return SparseMapping(values[0], values[1], values[2])
    }

    data class Almanac(
        val seedToSoilMap: List<SparseMapping>,
        val soilToFertilizerMap: List<SparseMapping>,
        val fertilizerToWaterMap: List<SparseMapping>,
        val waterToLightMap: List<SparseMapping>,
        val lightToTemperatureMap: List<SparseMapping>,
        val temperatureToHumidityMap: List<SparseMapping>,
        val humidityToLocationMap: List<SparseMapping>
    )

    fun populateSparseMaps(name: String, input: List<String>): List<SparseMapping> {
        return input.drop(input.indexOfFirst {
            it.startsWith(name)
        } + 1).takeWhile { it.isNotBlank() }.map { SparseMapping(it) }
    }

    fun getDestination(source: Long, mapping: List<SparseMapping>): Long {
        val matches = mapping.filter { it.getDestination(source) > -1 }
        return if (matches.isEmpty()) source else matches.first().getDestination(source)
    }

    fun Almanac(input: List<String>): Almanac {
        val seedToSoilMap = populateSparseMaps("seed-to-soil map", input)
        val soilToFertilizerMap = populateSparseMaps("soil-to-fertilizer", input)
        val fertilizerToWaterMap = populateSparseMaps("fertilizer-to-water map", input)
        val waterToLightMap = populateSparseMaps("water-to-light", input)
        val lightToTemperatureMap = populateSparseMaps("light-to-temperature", input)
        val temperatureToHumidityMap = populateSparseMaps("temperature-to-humidity", input)
        val humidityToLocationMap = populateSparseMaps("humidity-to-location", input)
        return Almanac(
            seedToSoilMap,
            soilToFertilizerMap,
            fertilizerToWaterMap,
            waterToLightMap,
            lightToTemperatureMap,
            temperatureToHumidityMap,
            humidityToLocationMap
        )
    }

    fun Almanac.getSeedLocation(seed: Long): Long {
        val soil = getDestination(seed, seedToSoilMap)
        val fertilizer = getDestination(soil, soilToFertilizerMap)
        val water = getDestination(fertilizer, fertilizerToWaterMap)
        val light = getDestination(water, waterToLightMap)
        val temperature = getDestination(light, lightToTemperatureMap)
        val humidity = getDestination(temperature, temperatureToHumidityMap)
        return getDestination(humidity, humidityToLocationMap)
    }

    fun Almanac.getSeedLocations(seeds: List<Long>): Map<Long, Long> {
        return seeds.associateWith { seed -> getSeedLocation(seed) }
    }

    fun part1(input: List<String>): Long {
        val seeds = input.first().substringAfter("seeds:").split(' ').filter { it.isNotBlank() }.map { it.toLong() }
        val almanac: Almanac = Almanac(input)
        return almanac.getSeedLocations(seeds).values.minOf { it }
    }

    fun part2(input: List<String>): Long {
        // Optimize me, PLEASE!
        val almanac: Almanac = Almanac(input)
        val seedRanges = input.first().substringAfter("seeds:").split(' ').filter { it.isNotBlank() }.map { it.toLong() }
        // find the min for each range
        val seeds = seedRanges.chunked(2).map { (start, range) ->
            println("range: $start to ${start + range}")
            (start..start + range).fold(Long.MAX_VALUE) { acc, seed ->
                min(acc, almanac.getSeedLocation(seed))
            }
        }
        println(seeds)
        return seeds.minOf { it } 
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    println("Test input")
    println("Part 1: " + part1(testInput))
    println("Part 2: " + part2(testInput))
    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)

    val input = readInput("Day05")
    println("Sample Input")
    print("Part 1: ")
    part1(input).println() // 525792406
    print("Part 2: ")
    part2(input).println() // 79004094
}