package dev.vrba.harold.core.permissions.repositories

import dev.vrba.harold.core.permissions.entities.PermissionEntry
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PermissionEntriesRepository : CrudRepository<PermissionEntry, Long> {

    fun findAllByPermissionIn(permissions: List<String>): List<PermissionEntry>
}