package dev.vrba.harold.core.permissions.commands

import dev.vrba.harold.core.commands.Command
import dev.vrba.harold.core.commands.CommandExecutionResult
import dev.vrba.harold.core.commands.CommandExecutionResult.Success
import dev.vrba.harold.core.commands.CommandScope
import dev.vrba.harold.core.permissions.PermissionEntryTarget
import dev.vrba.harold.core.permissions.PermissionEntryTarget.Role
import dev.vrba.harold.core.permissions.PermissionEntryTarget.User
import dev.vrba.harold.core.permissions.PermissionEntryType
import dev.vrba.harold.core.permissions.PermissionEntryType.Grant
import dev.vrba.harold.core.permissions.entities.PermissionEntry
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.springframework.stereotype.Component

@Component
class GrantPermissionCommand : PermissionModificationCommand(), Command {
    override val name = "grant"

    override val aliases = listOf("allow", "acl:grant", "acl:allow")

    override val permissions = listOf("acl:manage")

    override val scope = CommandScope.GuildsOnly

    override fun execute(event: MessageReceivedEvent): CommandExecutionResult {

        val parameters = this.parseParameters(event.message)

        val entries = parameters.roles.flatMap { role -> parameters.permissions.map { PermissionEntry(0L, Grant, Role, role.idLong, it) } } +
                      parameters.members.flatMap { user -> parameters.permissions.map { PermissionEntry(0L, Grant, User, user.idLong, it) } }

        this.repository.saveAll(entries)

        return Success
    }
}