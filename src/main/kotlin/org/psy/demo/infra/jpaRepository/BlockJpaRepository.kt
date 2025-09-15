package org.psy.demo.infra.jpaRepository

import org.psy.demo.infra.jpaEntity.BlockJpaEntity
import org.psy.demo.infra.jpaEntity.BlockStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BlockJpaRepository : JpaRepository<BlockJpaEntity, Long> {

    fun findByBlockerIdAndStatus(blockerId: Long, status: BlockStatus): List<BlockJpaEntity>

}