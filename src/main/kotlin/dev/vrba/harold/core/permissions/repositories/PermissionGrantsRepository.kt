package dev.vrba.harold.core.permissions.repositories

import dev.vrba.harold.core.permissions.entities.PermissionGrant
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PermissionGrantsRepository : CrudRepository<PermissionGrant, Long> {
    fun findByPermissionInAndGuild(permissions: List<String>, guild: Long): List<PermissionGrant>
}
