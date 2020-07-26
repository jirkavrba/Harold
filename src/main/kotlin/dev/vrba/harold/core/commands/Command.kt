package dev.vrba.harold.core.commands

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.springframework.stereotype.Component

@Component
interface Command {
    // Name of the command that can be used with a prefix (either full-match or fuzzy) to trigger the command
    val name: String

    // Aliases which can also be used to trigger this command
    val aliases: List<String>

    // Array of required permissions that the caller needs in order to run this command
    val permissions: List<String>

    // Scope in which the command can be triggered
    val scope: CommandScope

    // TODO: Add application context
    fun execute(event: MessageReceivedEvent): CommandExecutionResult
}