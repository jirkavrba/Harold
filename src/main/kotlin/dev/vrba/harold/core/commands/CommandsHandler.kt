package dev.vrba.harold.core.commands

import dev.vrba.harold.core.commands.CommandExecutionResult.Failure
import dev.vrba.harold.core.commands.CommandExecutionResult.Success
import dev.vrba.harold.core.commands.CommandScope.DirectMessagesOnly
import dev.vrba.harold.core.permissions.PermissionTarget.Role
import dev.vrba.harold.core.permissions.PermissionTarget.User
import dev.vrba.harold.core.permissions.repositories.PermissionDenialsRepository
import dev.vrba.harold.core.permissions.repositories.PermissionGrantsRepository
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.awt.Color

@Service
class CommandsHandler @Autowired constructor(
        private val commandsRepository: CommandsRepository,
        private val permissionsGrantRepository: PermissionGrantsRepository,
        private val permissionsDenialsRepository: PermissionDenialsRepository
) : ListenerAdapter() {

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

        val command = this.commandsRepository.findCommandByName(name, prefix.second == PrefixSearchPolicy.FUZZY)

        if (command == null) {
            this.sendCommandNotFoundError(name, event)
            return
        }

        // Check whether the command was executed in a correct scope
        if (!this.checkCommandExecutionScope(command, event)) {
            this.sendInvalidCommandScopeError(command, event)
            return
        }

        // Check ACL whether user has all the required permissions
        if (!this.checkUserEligibilityToRunCommand(command, event)) {
            this.sendUserNotEligibleError(command, event)
            return
        }

        when (command.execute(event)) {
            Success -> event.message.addReaction("☑").queue()
            Failure -> {
                // TODO: properly handle errors during command execution
            }
        }
    }

    private fun checkCommandExecutionScope(command: Command, event: MessageReceivedEvent): Boolean {

        // Check command scope in which it can be executed
        return when (command.scope) {
            CommandScope.DirectMessagesOnly -> !event.isFromGuild
            CommandScope.GuildsOnly -> event.isFromGuild
            CommandScope.Everywhere -> true
        }
    }

    private fun checkUserEligibilityToRunCommand(command: Command, event: MessageReceivedEvent): Boolean {
        // Commands triggered within private messages cannot be restricted by design
        if (!event.isFromGuild)
            return true

        val member = event.member ?: return false

        // If the sender is admin of the guild always allow running
        if (member.hasPermission(Permission.MANAGE_SERVER))
            return true

        // Permission denial has a higher priority so it is resolved earlier
        val denials = this.permissionsDenialsRepository.findByPermissionInAndGuild(command.permissions, event.guild.idLong)
        val denied = denials.any {
            return when (it.type) {
                Role -> member.roles.any { role -> role.idLong == it.targetId }
                User -> member.idLong == it.targetId
            }
        }

        if (denied) {
            // TODO: Report denied command triggers?
            return false
        }

        val grants = this.permissionsGrantRepository.findByPermissionInAndGuild(command.permissions, event.guild.idLong)
        val appliedGrants = grants.filter {
            return when (it.type) {
                Role -> member.roles.any { role -> role.idLong == it.targetId }
                User -> member.idLong == it.targetId
            }
        }
                .map { it.permission }
                .toSet()

        // User has all required permissions
        return appliedGrants.containsAll(command.permissions)
    }

    private fun sendCommandNotFoundError(name: String, event: MessageReceivedEvent) {
        val similar = this.commandsRepository.findCommandsSimilarTo(name)
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

    private fun sendInvalidCommandScopeError(command: Command, event: MessageReceivedEvent) {
        val embed = EmbedBuilder()
                .setTitle("Command `${command.name}` cannot be run within this scope!")
                .setDescription(
                        if (command.scope == DirectMessagesOnly) "This command can be only used in private messages."
                        else "This command can be only used in guilds."
                )
                .setColor(Color.RED)
                .setFooter("Command issued by" + event.author.name, event.author.avatarUrl)
                .setTimestamp(event.message.timeCreated)
                .build()

        event.channel.sendMessage(embed).queue()
    }

    private fun sendUserNotEligibleError(command: Command, event: MessageReceivedEvent) {
        val embed = EmbedBuilder()
                .setTitle("You don't have permissions required to run `${command.name}`!")
                .setDescription("Required permissions are: " + command.permissions.joinToString("`, `", "`", "`"))
                .setColor(Color.RED)
                .setFooter("Command issued by" + event.author.name, event.author.avatarUrl)
                .setTimestamp(event.message.timeCreated)
                .build()

        event.channel.sendMessage(embed).queue()
    }
}