package dev.vrba.harold.core.permissions

import dev.vrba.harold.core.commands.Command
import dev.vrba.harold.core.permissions.PermissionEntryTarget.*
import dev.vrba.harold.core.permissions.PermissionEntryType.Grant
import dev.vrba.harold.core.permissions.entities.PermissionEntry
import dev.vrba.harold.core.permissions.repositories.PermissionEntriesRepository
import net.dv8tion.jda.api.entities.Member
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PermissionsResolver @Autowired constructor(private val repository: PermissionEntriesRepository) {

    private fun PermissionEntry.matchesMember(member: Member): Boolean =
            when (this.target) {
                Everyone -> true
                User -> member.idLong == this.targetId
                Role -> member.roles.any { it.idLong == this.targetId }
            }

    fun isEligibleToExecute(member: Member, command: Command): Boolean = isEligibleToExecute(member, command.permissions)

    fun isEligibleToExecute(member: Member, permissions: List<String>): Boolean {
        val relevantEntries = repository.findAllByPermissionIn(permissions)
        val (grants, denials) = relevantEntries.partition { it.type == Grant }

        // If there is an explicit denial for the given member or any of his roles
        if (denials.any { it.matchesMember(member) }) {
            return false
        }

        // If the given member has all required permissions allow the command execution
        return permissions.all { permission ->
            grants.count { it.permission == permission && it.matchesMember(member) } > 0
        }
    }
}