package dev.vrba.harold.modules.sample.commands

import dev.vrba.harold.core.commands.Command
import dev.vrba.harold.core.commands.CommandExecutionResult
import dev.vrba.harold.core.commands.CommandExecutionResult.Success
import dev.vrba.harold.core.commands.CommandScope
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.springframework.stereotype.Component

@Component
class SampleCommand : Command {
    override val name = "sample"

    override val aliases = listOf("samplealias")

    override val scope = CommandScope.GuildsOnly

    override val permissions = emptyList<String>()

    override fun execute(event: MessageReceivedEvent): CommandExecutionResult {
        event.channel.sendMessage("Executed the sample command")
        return Success
    }
}