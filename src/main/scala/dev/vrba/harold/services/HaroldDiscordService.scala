package dev.vrba.harold.services

import dev.vrba.harold.core.HaroldModule
import net.dv8tion.jda.api.{JDA, Permission}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class HaroldDiscordService @Autowired()(private val client: JDA, private val modules: Array[HaroldModule]) {

  private val logger = LoggerFactory.getLogger(classOf[HaroldDiscordService])

  def start(): Unit = {
    logger.info("Starting the core Harold service")
    logger.info(s"You can invite Harold to your Discord server using this link: ${client.getInviteUrl(Permission.ADMINISTRATOR)}");

    logger.info(s"Found ${modules.length} modules")

    // Register all modules and let them bind event listeners/commands/whatever to the client
    modules.foreach(module => {
      logger.info(s"Registering module [${module.name}]")
      module.register(client)
    })

    logger.info("Finished registering modules")
  }
}
