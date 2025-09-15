package org.psy.demo.infra.jpaRepository

import org.psy.demo.core.vo.NotificationType
import org.psy.demo.infra.jpaEntity.NotiListJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface NotiListJpaRepository : JpaRepository<NotiListJpaEntity, Long> {

    @Query(value = """
        select n from NotiListJpaEntity n where n.createdBy = :userId and 
        n.type = :notificationType 
        order by n.createdAt desc
        limit 1
    
    """)
    fun findOneByUserIdAndType(userId: Long?, notificationType: NotificationType?): NotiListJpaEntity?


}