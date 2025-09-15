package org.psy.demo.infra.jpaRepository

import org.psy.demo.infra.jpaEntity.PostTagJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PostTagJpaRepository : JpaRepository<PostTagJpaEntity, Long> {


    @Query("""
        select pt from PostTagJpaEntity pt
        join TagJpaEntity t on pt.tagId = t.id
        where t.tagName = :tag and t.status = 'ACTIVE'
        
    """)
    fun findByTag(tag: String): List<PostTagJpaEntity>

}
