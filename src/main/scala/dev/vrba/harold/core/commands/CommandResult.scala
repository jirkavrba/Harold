package dev.vrba.harold.core.commands

sealed trait CommandResult

case class Success() extends CommandResult
case class Error(message: Option[String]) extends CommandResult
