package dev.vrba.harold.core

import net.dv8tion.jda.api.JDA
import org.springframework.stereotype.Component

@Component
trait HaroldModule {
  def name: String
  def register(client: JDA): Unit
}
