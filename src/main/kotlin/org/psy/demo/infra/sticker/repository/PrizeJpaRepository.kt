package org.psy.demo.infra.sticker.repository

import org.psy.demo.infra.sticker.jpaEntity.PrizeJpaEntity
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PrizeJpaRepository : JpaRepository<PrizeJpaEntity, Long> {

    @Query(
        value = """ 
            select p from PrizeJpaEntity p
            where p.monthYear = :monthYear 
            and p.status = 'ACTIVE'
            order by p.id asc

"""
    )
    fun findByMonthYear(monthYear: String?, pageRequest: PageRequest?): List<PrizeJpaEntity>
}
