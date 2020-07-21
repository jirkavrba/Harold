package dev.vrba.harold

import dev.vrba.harold.services.HaroldDiscordService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.{CommandLineRunner, SpringApplication}
import org.springframework.boot.autoconfigure.SpringBootApplication

object Application {
  @SpringBootApplication
  private[this] class HaroldApplication @Autowired() (private val service: HaroldDiscordService) extends CommandLineRunner {
    // The only application entry point at this moment is the discord service
    override def run(args: String*): Unit = service.start()
  }

  def main(arguments: Array[String]): Unit = SpringApplication.run(classOf[HaroldApplication], arguments: _*)
}