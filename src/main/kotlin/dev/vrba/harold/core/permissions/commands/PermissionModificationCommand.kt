package dev.vrba.harold.core.permissions.commands

import dev.vrba.harold.core.permissions.repositories.PermissionEntriesRepository
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.Role
import net.dv8tion.jda.api.entities.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PermissionModificationCommand {

    @Autowired
    protected lateinit var repository: PermissionEntriesRepository

    protected data class ParsedParameters(
            val permissions: List<String>,
            val members: List<User>,
            val roles: List<Role>
    )

    protected fun parseParameters(message: Message): ParsedParameters {
        val users = message.mentionedUsers
        val roles = message.mentionedRoles

        // First 2 words will be either h:allow or h:deny
        val permissions = message.contentRaw
                .split(" ")
                .drop(2)
                .filterNot {
                    users.any { user -> "<@!${user.idLong}>" == it || "<@${user.idLong}>" == it } ||
                    roles.any { role -> "<@&${role.idLong}>" == it }
                }

        return ParsedParameters(permissions, users, roles)
    }
}