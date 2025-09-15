package org.psy.demo.infra.jpaRepository

import org.psy.demo.infra.jpaEntity.GoodsManagingJpaEntity
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Date

@Repository
interface GoodsManagingJpaRepository : JpaRepository<GoodsManagingJpaEntity, Long> {
    @Query(value = """
        select gm from GoodsManagingJpaEntity gm
        where gm.type = :type and gm.startAt <= :startAt and gm.endAt >= :endAt
        order by gm.orderNum DESC
""")
    fun findByType(startAt: Date, endAt: Date, pageRequest: PageRequest?, type: String?): List<GoodsManagingJpaEntity>


}