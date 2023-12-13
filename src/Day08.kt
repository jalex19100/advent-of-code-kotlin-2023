fun main() {

    data class Node(val element: String, val left: String, val right: String)

    fun Node.turn(direction: Char): String {
        return if (direction == 'R') this.right else this.left
    }

    data class Network(val nodes: Map<String, Node>)

    fun Network(input: List<String>): Network {
        val regex = Regex("(\\w+)")
        val nodes = input.associate {
            val groupValues = regex.findAll(it).toList().map { match -> match.groupValues[1] }
            groupValues[0] to Node(groupValues[0], groupValues[1], groupValues[2])
        }
        return Network(nodes)
    }

    fun Network.getSteps(instructions: CharIterator, startElement: String = "AAA", endSuffix: String = "ZZZ"): Sequence<Node> =
        generateSequence(seedFunction = { this.nodes[startElement] }, nextFunction = {
            if (it.element.endsWith(endSuffix)) null else {
                this.nodes[it.turn(instructions.next())]
            }
        })

    fun part1(input: List<String>): Int {
        val instructions = input[0].repeat(150).iterator()
        val network = Network(input.drop(2))
        return network.getSteps(instructions, "AAA", "ZZZ").count() - 1
    }

    fun part2(input: List<String>): Long {
        val instructions = input[0].repeat(500).iterator()
        val network = Network(input.drop(2))
        val startingNodes = network.nodes.filter { (k, _) -> k.endsWith("A") }.values
        val stepCountsByNode = startingNodes.associate { node ->
            node.element to network.getSteps(instructions, node.element, "Z").count() - 1
        }.values
        return stepCountsByNode.fold(stepCountsByNode.max().toLong()) { result, number -> lcm(result.toLong(), number.toLong()) }
    }

    val testInput = readInput("Day08_test")
    val testInput2 = readInput("Day08_test2")
    part1(testInput).println()
    check(part1(testInput) == 6)
    part2(testInput2).println()
    check(part2(testInput2) == 6L)

    val input = readInput("Day08")
    part1(input).println() // 18727
    part2(input).println() // 18024643846273
}