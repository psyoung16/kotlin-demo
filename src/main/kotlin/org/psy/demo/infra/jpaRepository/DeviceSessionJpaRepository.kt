package org.psy.demo.infra.jpaRepository

import org.psy.demo.infra.jpaEntity.DeviceSessionJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface DeviceSessionJpaRepository : JpaRepository<DeviceSessionJpaEntity, Long> {

    @Query("""
        SELECT ds
        FROM DeviceSessionJpaEntity ds
        WHERE ds.id = :deviceId and ds.status = 'ACTIVE'
    """)
    fun findAndDeviceId(deviceId: Long): DeviceSessionJpaEntity?



}