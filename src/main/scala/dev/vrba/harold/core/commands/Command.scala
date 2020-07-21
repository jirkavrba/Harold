package dev.vrba.harold.core.commands

import net.dv8tion.jda.api.entities.User
import org.springframework.stereotype.Component

@Component
trait Command {
  val name: String
  val aliases: Array[String]

  // TODO: Command context?
  def execute(sender: User): CommandResult
}
