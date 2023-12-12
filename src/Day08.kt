import sun.nio.ch.Net

fun main() {

    data class Node(val element: String, val left: String, val right: String)

    fun Node.turn(direction: Char): String {
        return if (direction == 'R') this.right else this.left
    }

    data class Network(val nodes: Map<String, Node>)

    fun Network(input: List<String>): Network {
        val regex = Regex("(\\w+)")
        val nodes = input.associate {
            val groupValues = regex.findAll(it).toList().map { it.groupValues[1] }
            groupValues[0] to Node(groupValues[0], groupValues[1], groupValues[2])
        }
        return Network(nodes)
    }

    fun part1(input: List<String>): Int {
        val instructions = input[0].repeat(150).iterator()
        val network = Network(input.drop(2))
        fun Network.getSteps1(): Sequence<Node> = generateSequence(seedFunction = { this.nodes["AAA"] }, nextFunction = {
            if (it.element == "ZZZ") null else {
                this.nodes[it.turn(instructions.next())]
            }
        })

        return network.getSteps1().count() - 1
    }

    fun part2(input: List<String>): Int {
        val instructions = input[0].repeat(500000 ).iterator()
        val network = Network(input.drop(2))
        fun Network.getSteps2(): Sequence<Collection<Node>> = generateSequence(
            seedFunction = { this.nodes.filter { (k, _) -> k.endsWith("A") }.values },
            nextFunction = {
                val nextTurn = instructions.next()
                if (it.all { node -> node.element.endsWith("Z") }) null
                else this.nodes.filter { (_, v) ->
                    it.map { node -> this.nodes[node.turn(nextTurn)] }.contains(v)
                }.values
            })

        val moves = network.getSteps2()
        return moves.count() - 1
    }

    val testInput = readInput(                                                                                       ju"Day08_test")
    val testInput2 = readInput("Day08_test2")
    part1(testInput).println()
    check(part1(testInput) == 6)
    part2(testInput2).println()
    check(part2(testInput2) == 6)

    val input = readInput("Day08")
    part1(input).println() // 18727
    part2(input).println()
}