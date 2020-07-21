package dev.vrba.harold.core

import net.dv8tion.jda.api.JDA
import org.springframework.stereotype.Component

@Component
trait HaroldModule {
  val name: String
  def register(client: JDA): Unit
}
