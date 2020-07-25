package dev.vrba.harold.services

import dev.vrba.harold.core.HaroldModule
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.Permission
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class HaroldDiscordService @Autowired constructor(private val client: JDA, private val modules: Array<HaroldModule>) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun start() {
        logger.info("Starting the core Harold service")
        logger.info("You can invite Harold to your Discord server using this link: ${client.getInviteUrl(Permission.ADMINISTRATOR)}")
        logger.info("Found ${modules.size} modules")

        // Register all modules and let them bind event listeners/commands/whatever to the client
        modules.forEach {
            logger.info("Registering module [${it.name}]")
            it.register(client)
        }

        logger.info("Finished registering modules")
    }
}