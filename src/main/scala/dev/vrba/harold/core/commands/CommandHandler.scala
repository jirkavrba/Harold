package dev.vrba.harold.core.commands

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CommandHandler @Autowired() (private val repository: CommandRepository ) extends ListenerAdapter {

  private val prefix = "~"

  override def onGuildMessageReceived(event: GuildMessageReceivedEvent): Unit = {
    val message = event.getMessage

    // Do not respond to self and other bots
    if (message.getAuthor.isBot) return

    if (message.getContentDisplay.startsWith(prefix)) {
      val words = message.getContentDisplay.split(" ")

      repository.findCommand(words.head) match {
        case Some(command) => command.execute(message) // TODO: Handle Error being returned as command execution result
        case None => // Ignore unknown commands that might belong to another bot
      }
    }
  }
}
