package org.psy.demo.infra.jpaRepository

import org.psy.demo.infra.jpaEntity.BadgeJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BadgeJpaRepository : JpaRepository<BadgeJpaEntity, Long> {

    @Query("""
        SELECT b FROM BadgeJpaEntity b
        WHERE b.ownerId = :ownerId
        order by b.ownerId desc
    """)
    fun findOwnerId(ownerId: Long): List<BadgeJpaEntity>
}