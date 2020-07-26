package dev.vrba.harold.core.commands

import dev.vrba.harold.core.commands.CommandExecutionResult.Failure
import dev.vrba.harold.core.commands.CommandExecutionResult.Success
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.awt.Color

@Service
class CommandsHandler @Autowired constructor(private val repository: CommandsRepository) : ListenerAdapter() {

    private enum class PrefixSearchPolicy {
        FULL_MATCH,
        FUZZY,
    }

    // TODO: Move this to a config file
    // The longest prefixes need to be put first
    private val prefixes = mapOf(
            "harold:" to PrefixSearchPolicy.FULL_MATCH,
            "h:" to PrefixSearchPolicy.FUZZY
    )

    override fun onMessageReceived(event: MessageReceivedEvent) {

        if (event.author.isBot) return

        val prefix = this.prefixes
                .toList()
                .firstOrNull { event.message.contentDisplay.startsWith(it.first) } ?: return

        val content = event.message.contentDisplay.removePrefix(prefix.first)
        val name = content.split(" ")[0]

        val command = this.repository.findCommandByName(name, prefix.second == PrefixSearchPolicy.FUZZY)

        if (command == null) {
            // TODO: Show an error with a list of similar commands
            this.showCommandNotFound(name, event)
            return
        }

        when (command.execute(event)) {
            Success -> event.message.addReaction("☑").queue()
            Failure -> {
                // TODO: properly handle errors during command execution
            }
        }
    }

    private fun showCommandNotFound(name: String, event: MessageReceivedEvent) {
        val similar = this.repository.findCommandsSimilarTo(name)
        val embed = EmbedBuilder()
                .setTitle("Command `$name` not found!")
                .setDescription(
                        if (similar.isEmpty()) "There are no similar commands."
                        else "There are similar commands, did you mean to use one of them?"
                )
                .setColor(Color.RED)
                .setFooter("Command issued by " + event.author.name, event.author.avatarUrl)
                .setTimestamp(event.message.timeCreated)

        // List similar commands
        similar.forEach {
            embed.addField(
                    "`${it.name}`",
                    it.aliases.joinToString(", ") { alias -> "`${alias}`" },
                    false
            )
        }


        event.channel.sendMessage(embed.build()).queue()
    }
}