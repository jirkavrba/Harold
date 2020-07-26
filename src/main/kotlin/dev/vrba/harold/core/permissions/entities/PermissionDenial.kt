package dev.vrba.harold.core.permissions.entities

import dev.vrba.harold.core.permissions.PermissionTarget
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class PermissionDenial(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        // Guild ID
        val guild: Long,

        // Type of the target (user x role)
        val type: PermissionTarget,

        // Either user ID or role ID
        val targetId: Long,

        // Permission identification
        val permission: String
)
