package dev.vrba.harold.core.commands

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.springframework.stereotype.Component

@Component
interface Command {
    val name: String
    val aliases: List<String>

    // TODO: Add application context
    fun execute(event: MessageReceivedEvent): CommandExecutionResult
}