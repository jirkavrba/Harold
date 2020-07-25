package dev.vrba.harold

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class ApplicationContextBeansResolver {
    @Bean
    fun defaultJDAClient(@Value("\${DISCORD_TOKEN}") token: String): JDA = JDABuilder.createDefault(token).build()
}