package dev.vrba.harold.core

import net.dv8tion.jda.api.JDA
import org.springframework.stereotype.Component

@Component
interface HaroldModule {
    val name: String
    fun register(client: JDA)
}