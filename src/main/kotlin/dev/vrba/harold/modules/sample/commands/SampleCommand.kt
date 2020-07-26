package dev.vrba.harold.modules.sample.commands

import dev.vrba.harold.core.commands.Command
import dev.vrba.harold.core.commands.CommandExecutionResult
import dev.vrba.harold.core.commands.CommandExecutionResult.Success
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.springframework.stereotype.Service

@Service
class SampleCommand : Command {
    override val name  = "sample"
    override val aliases = listOf("samplealias")

    override fun execute(event: MessageReceivedEvent): CommandExecutionResult {
        event.channel.sendMessage("Executed the sample command")
        return Success
    }
}