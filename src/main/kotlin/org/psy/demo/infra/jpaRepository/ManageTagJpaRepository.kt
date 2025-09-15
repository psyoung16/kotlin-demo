package org.psy.demo.infra.jpaRepository

import org.psy.demo.core.vo.TagType
import org.psy.demo.infra.jpaEntity.ManageTagJpaEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Date

@Repository
interface ManageTagJpaRepository : JpaRepository<ManageTagJpaEntity, Long> {

    //홈 화면은 작은 숫자가 맨 위로 -> 추후 변경될 예정
    @Query(
        value = """
        select mt from ManageTagJpaEntity mt
        where mt.status = 'ACTIVE' and mt.startAt <= :startAt and mt.endAt >= :endAt
        and mt.type = :position
        order by mt.numOrder asc
"""
    )
    fun findByManagTag(position: TagType, startAt: Date, endAt: Date, pageRequest: PageRequest?): List<ManageTagJpaEntity>

    //스티커 리스트 쪽은 숫자가 큰 거 위로
    //startAt, endAt 조건 x
    @Query(
        value = """
        select mt from ManageTagJpaEntity mt
        where mt.status = 'ACTIVE'
        and mt.type = :position 
        and mt.tagName in (select s.tagName from StickerJpaEntity s where s.status = 'ACTIVE')
         order by mt.numOrder desc 
"""
    )
    fun findManageTagByAvailableProductsNotConsiderDate(position: TagType, pageRequest: PageRequest? ): List<ManageTagJpaEntity>

    @Query(
        value = """
        select mt from ManageTagJpaEntity mt
        where mt.status = 'ACTIVE' and mt.eventsId = :challengeId
"""
    )
    fun findManagTagByChallengeId(pageRequest: PageRequest?, challengeId: Long?): List<ManageTagJpaEntity>


    @Query("""
        select mt from ManageTagJpaEntity mt
        where mt.status = 'ACTIVE' and mt.id = :id
    """)
    fun findByManageTagManagementId(id: Long?): ManageTagJpaEntity?

    @Query(
        value = """
        select mt from ManageTagJpaEntity mt
        where mt.status = 'ACTIVE'
        and mt.type = :type

"""
    )
    fun findByTypeAndNotTimeCheck(type: TagType?, pageRequest: PageRequest?): Page<ManageTagJpaEntity>


    @Query(
        value = """
        select max(mt.numOrder) from ManageTagJpaEntity mt
        where mt.status = 'ACTIVE' and mt.type = :type
        """
    )
    fun maxOrderNumByType(type: TagType)
//    int


}