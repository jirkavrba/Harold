package dev.vrba.harold.core.commands

import org.springframework.stereotype.Repository

@Repository
class CommandRepository(private val commands: Array[Command]) {

  def findCommand(name: String): Option[Command] =
    commands.find(command => command.name == name || command.aliases.contains(name))
}
