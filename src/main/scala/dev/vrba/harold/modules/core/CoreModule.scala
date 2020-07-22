package dev.vrba.harold.modules.core

import dev.vrba.harold.core.HaroldModule
import dev.vrba.harold.core.commands.CommandHandler
import net.dv8tion.jda.api.JDA
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CoreModule @Autowired()(private val handler: CommandHandler) extends HaroldModule {
  override val name: String = "core"

  override def register(client: JDA): Unit = {
    client.addEventListener(handler)
  }
}
