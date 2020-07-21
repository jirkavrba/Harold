package dev.vrba.harold

import org.springframework.boot.{CommandLineRunner, SpringApplication}
import org.springframework.boot.autoconfigure.SpringBootApplication

object Application {
  @SpringBootApplication
  private[this] class HaroldApplication extends CommandLineRunner {
    override def run(args: String*): Unit = {
      println("Hide the pain, Harold.")
    }
  }

  // The application entry point
  def main(arguments: Array[String]): Unit = SpringApplication.run(classOf[HaroldApplication], arguments: _*)
}