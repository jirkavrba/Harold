package dev.vrba.harold.core.permissions.entities

import dev.vrba.harold.core.permissions.PermissionEntryTarget
import dev.vrba.harold.core.permissions.PermissionEntryType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class PermissionEntry(
        @Id
        @GeneratedValue
        val id: Long,

        val type: PermissionEntryType,

        val target: PermissionEntryTarget,

        val targetId: Long,

        val permission: String
)
