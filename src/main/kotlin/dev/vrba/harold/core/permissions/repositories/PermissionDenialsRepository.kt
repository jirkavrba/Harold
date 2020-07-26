package dev.vrba.harold.core.permissions.repositories

import dev.vrba.harold.core.permissions.entities.PermissionDenial
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PermissionDenialsRepository : CrudRepository<PermissionDenial, Long> {
    fun findByPermissionInAndGuild(permissions: List<String>, guild: Long): List<PermissionDenial>
}
