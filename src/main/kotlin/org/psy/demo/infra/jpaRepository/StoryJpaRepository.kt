package org.psy.demo.infra.jpaRepository

import org.psy.demo.infra.jpaEntity.StoryJpaEntity
import org.psy.demo.infra.jpaEntity.StoryStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface StoryJpaRepository : JpaRepository<StoryJpaEntity, Long>{

    @Query("""
        SELECT s
        FROM StoryJpaEntity s
        WHERE s.createdBy = :creatdBy
        AND s.status = :status
        order by s.createdAt desc
    """)
    fun findCreatedByAndStatus(creatdBy: Long, status: StoryStatus): List<StoryJpaEntity>

}
