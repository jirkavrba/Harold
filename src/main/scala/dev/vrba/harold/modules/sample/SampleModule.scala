package dev.vrba.harold.modules.sample

import dev.vrba.harold.core.HaroldModule
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Activity
import org.springframework.stereotype.Service

@Service
class SampleModule extends HaroldModule {
  override def name: String = "Sample module"

  override def register(client: JDA): Unit = {
    // Just so it is obvious that the module gets registered...
    client.getPresence.setActivity(Activity.playing("with the sample module"))
  }
}
