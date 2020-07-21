package dev.vrba.harold.core.commands

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class CommandHandler(client: JDA) extends ListenerAdapter {

  private val prefix = "!"

  override def onGuildMessageReceived(event: GuildMessageReceivedEvent): Unit = {

    val bot = event.getGuild.getMember(client.getSelfUser)
    val message = event.getMessage

    // Do not respond to self and other bots
    if (message.getAuthor.isBot) return

    if (message.getContentDisplay.startsWith(prefix) || message.getMentionedMembers.contains(bot)) {
      // TODO: Handle command
    }
  }
}
