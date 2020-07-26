package dev.vrba.harold.core.commands

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class CommandsRepository @Autowired constructor(private val commands: Array<Command>) {

    fun findCommandByName(search: String, fuzzy: Boolean = false): Command? {
        return this.commands.find {
            // If the fuzzy search is enabled, only a part of the name / alias must be matched
            if (fuzzy) it.name.contains(search) || it.aliases.any { alias -> alias.contains(search) }
            else it.name == search || it.aliases.contains(search)
        }
    }

    fun findCommandsSimilarTo(search: String, threshold: Int = 3): List<Command> {
        return this.commands
                .map { it to lowestLevenshteinDistance(search, it)}
                .filter { it.second <= threshold }
                .sortedBy { it.second }
                .map { it.first }
    }

    private fun lowestLevenshteinDistance(search: String, command: Command): Int {
        val targets = listOf(command.name, *command.aliases.toTypedArray())
        return targets.map { levenshteinDistance(search, it) } .min()!! // At least the name must be set
    }

    /**
     * Compute the levenshtein distance between two words, used when finding similar commands
     */
    private fun levenshteinDistance(first: String, second: String): Int {
        val firstLength = first.length
        val secondLength = second.length

        var cost = Array(firstLength) { it }
        var newCost = Array(firstLength) { 0 }

        for (i in 1 until secondLength) {
            newCost[0] = i

            for (j in 1 until firstLength) {
                val match = if (first[j - 1] == second[i - 1]) 0 else 1

                val costReplace = cost[j - 1] + match
                val costInsert = cost[j] + 1
                val costDelete = newCost[j - 1] + 1

                newCost[j] = costInsert
                        .coerceAtMost(costDelete)
                        .coerceAtMost(costReplace)
            }

            val swap = cost
            cost = newCost
            newCost = swap
        }

        return cost[firstLength - 1]
    }
}