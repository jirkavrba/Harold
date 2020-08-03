package dev.vrba.harold.core.permissions.commands

import dev.vrba.harold.core.commands.Command
import dev.vrba.harold.core.commands.CommandExecutionResult
import dev.vrba.harold.core.commands.CommandScope
import dev.vrba.harold.core.permissions.PermissionEntryTarget
import dev.vrba.harold.core.permissions.PermissionEntryType
import dev.vrba.harold.core.permissions.entities.PermissionEntry
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.springframework.stereotype.Component

@Component
class DenyPermissionCommand : PermissionModificationCommand(), Command {

    override val name: String = "deny"

    override val aliases: List<String> = listOf("acl:deny")

    override val permissions: List<String> = listOf("acl:manage")

    override val scope: CommandScope = CommandScope.GuildsOnly

    override fun execute(event: MessageReceivedEvent): CommandExecutionResult {

        val parameters = this.parseParameters(event.message)

        val entries = parameters.roles.flatMap { role -> parameters.permissions.map { PermissionEntry(0L, PermissionEntryType.Denial, PermissionEntryTarget.Role, role.idLong, it) } } +
                parameters.members.flatMap { user -> parameters.permissions.map { PermissionEntry(0L, PermissionEntryType.Denial, PermissionEntryTarget.User, user.idLong, it) } }

        this.repository.saveAll(entries)

        return CommandExecutionResult.Success
    }
}