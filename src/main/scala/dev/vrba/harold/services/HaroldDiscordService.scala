package dev.vrba.harold.services

import net.dv8tion.jda.api.{JDA, Permission}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class HaroldDiscordService @Autowired()(private val client: JDA) {

  private val logger = LoggerFactory.getLogger(classOf[HaroldDiscordService])

  def start(): Unit = {
    logger.info("Starting the core Harold service.")
    logger.info(s"You can invite Harold to your Discord server using this link: ${client.getInviteUrl(Permission.ADMINISTRATOR)}");

    // TODO: Register and load all modules
  }
}
