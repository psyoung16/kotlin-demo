package org.psy.demo.infra.jpaRepository

import org.psy.demo.infra.jpaEntity.EventJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Date

@Repository
interface EventJpaRepository : JpaRepository<EventJpaEntity, Long> {

    @Query(value = """
        select new EventJpaEntity (
            e.id, e.title, e.type, e.createdBy, 
            e.createdAt, e.updatedAt, e.contentLinkType,
            e.linkUrl, e.numOrder, e.status, e.primaryTag,
            e.contents, e.checkStartAt, e.checkEndAt, e.subEventsId,
            e.startAt, e.endAt, 
            f.url, f1.url, f2.url
        )from EventJpaEntity e 
        left join FileJpaEntity f on f.id = e.imageUrlId
        left join FileJpaEntity f1 on f1.id = e.imageUrlId2
        left join FileJpaEntity f2 on f2.id = e.imageUrlId3
        where e.status = 'ACTIVE' and e.type = 'CHALLENGE'
        and e.startAt <= :startAt
        and e.endAt >= :endAt

""")
    fun findByChallenge(startAt: Date, endAt: Date): List<EventJpaEntity>


    @Query("""
        select new EventJpaEntity (
            e.id, e.title, e.type, e.createdBy, 
            e.createdAt, e.updatedAt, e.contentLinkType,
            e.linkUrl, e.numOrder, e.status, e.primaryTag,
            e.contents, e.checkStartAt, e.checkEndAt, e.subEventsId,
            e.startAt, e.endAt, 
            f.url, f1.url, f2.url
        )from EventJpaEntity e 
        left join FileJpaEntity f on f.id = e.imageUrlId
        left join FileJpaEntity f1 on f1.id = e.imageUrlId2
        left join FileJpaEntity f2 on f2.id = e.imageUrlId3
        where e.status = 'ACTIVE'
        and e.id = :eventsId
    """)
    fun findId(eventsId: Long) : EventJpaEntity

}