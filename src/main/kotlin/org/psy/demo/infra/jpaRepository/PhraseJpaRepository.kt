package org.psy.demo.infra.jpaRepository

import org.psy.demo.infra.jpaEntity.PhraseJpaEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Date

interface PhraseJpaRepository : JpaRepository<PhraseJpaEntity, Long> {


    fun findByPosition(position: String?, request: PageRequest?): Page<PhraseJpaEntity>

    @Query(value = """
                select p
                    from PhraseJpaEntity p
                    where p.position = :position
                    order by p.id desc
                    limit 1
            
            """)
    fun findOneByPosition(position: String?): PhraseJpaEntity?


    @Query(value = """
                select p
                    from PhraseJpaEntity p
                    where p.position = :position
                    and p.startAt <= :startAt and p.endAt >= :endAt
                    order by p.id desc
            """)
    fun findAllByPosition(startAt: Date, endAt: Date, position: String?): List<PhraseJpaEntity>


    @Query(value = """
                select p
                    from PhraseJpaEntity p
                    where p.position = :position
                    order by p.id desc
            """)
    fun findAllByPositionNotDate(position: String?): List<PhraseJpaEntity>




}