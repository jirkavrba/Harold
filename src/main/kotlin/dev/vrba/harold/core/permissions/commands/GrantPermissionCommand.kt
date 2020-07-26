package dev.vrba.harold.core.permissions.commands

import dev.vrba.harold.core.commands.Command
import dev.vrba.harold.core.commands.CommandExecutionResult
import dev.vrba.harold.core.commands.CommandExecutionResult.Success
import dev.vrba.harold.core.commands.CommandScope
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.springframework.stereotype.Component

@Component
class GrantPermissionCommand : Command {
    override val name = "grant"

    override val aliases = listOf("allow")

    override val permissions = listOf("acl:manage")

    override val scope = CommandScope.GuildsOnly

    override fun execute(event: MessageReceivedEvent): CommandExecutionResult {
        event.channel.sendMessage("TODO: Create & store permission grant with given parameters.")
        return Success
    }
}