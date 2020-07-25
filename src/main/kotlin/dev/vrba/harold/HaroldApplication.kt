package dev.vrba.harold

import dev.vrba.harold.services.HaroldDiscordService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HaroldApplication @Autowired constructor(private val service: HaroldDiscordService) : CommandLineRunner {
    override fun run(vararg args: String?) = service.start()
}


fun main(args: Array<String>) {
    runApplication<HaroldApplication>(*args)
}