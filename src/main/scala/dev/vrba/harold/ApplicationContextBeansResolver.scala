package dev.vrba.harold

import net.dv8tion.jda.api.{JDA, JDABuilder}
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

// A helper object that resolves some beans for the application context
@Component
class ApplicationContextBeansResolver {

  @Bean
  def JDAClientBean(@Value("${DISCORD_TOKEN}") token: String): JDA = JDABuilder.createDefault(token).build()
}
