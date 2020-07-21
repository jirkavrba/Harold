package dev.vrba.harold.core.entities

import javax.persistence.{Entity, GeneratedValue, GenerationType, Id}

sealed trait ACLEntryType

case class DenyRole(roleId: Int) extends ACLEntryType
case class AllowRole(roleId: Int) extends ACLEntryType

case class DenyUser(userId: Int) extends ACLEntryType
case class AllowUser(userId: Int) extends ACLEntryType

@Entity
case class ACLEntry (
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  id: Int,
  guild: Int,
  entryType: ACLEntryType,
)
