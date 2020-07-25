package dev.vrba.harold.modules.sample

import dev.vrba.harold.core.HaroldModule
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Activity
import org.springframework.stereotype.Component

@Component
class SampleModule : HaroldModule {
    override val name = "Sample module"

    override fun register(client: JDA) {
        client.presence.activity = Activity.playing("with the sample module")
    }
}