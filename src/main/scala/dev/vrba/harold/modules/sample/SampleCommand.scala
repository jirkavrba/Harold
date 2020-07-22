package dev.vrba.harold.modules.sample

import dev.vrba.harold.core.commands.{Command, CommandResult, Success}
import net.dv8tion.jda.api.entities.Message

class SampleCommand extends Command {
  override val name: String = "sample"
  override val aliases: Array[String] = Array("samplealias")

  override def execute(message: Message): CommandResult = {
    message.addReaction("☺").queue()
    Success()
  }
}
