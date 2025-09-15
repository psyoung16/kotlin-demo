package org.psy.demo.infra.jpaRepository

import org.psy.demo.infra.jpaEntity.SlideJpaEntity
import org.psy.demo.core.vo.SlidePosition
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface SlideJpaRepository : JpaRepository<SlideJpaEntity, Long>{


    //추후 db쪽 건드리면서 수정 필요
    @Query(
        value= """
                        select new SlideJpaEntity ( 
                        s.id, s.webViewTitle, s.createdAt, s.updatedAt, s.status, 
                        s.numOrder, s.type, s.createdBy, s.startAt, s.endAt, 
                        s.eventsId, s.position, s.mainImage, s.exposure, s.linkUrl,
                        f.url ) from SlideJpaEntity s 
                        join FileJpaEntity f on s.subImage = f.id
                        where ((s.status = 'ACTIVE' 
                            and s.startAt <= :startAt 
                            and s.endAt >= :endAt ) or (s.status = 'ALWAYS'))
                            and s.position = :position
                            and s.exposure = 'BANNER'
                    """
    )
    fun getMainBannerListByPostitionIng(startAt: Date, endAt: Date, position: SlidePosition?, pageRequest: PageRequest?): List<SlideJpaEntity>

    @Query(
        value= """
                        select new SlideJpaEntity ( 
                        s.id, s.webViewTitle, s.createdAt, s.updatedAt, s.status, 
                        s.numOrder, s.type, s.createdBy, s.startAt, s.endAt, 
                        s.eventsId, s.position, s.subImage, s.exposure, s.linkUrl,
                        f.url ) from SlideJpaEntity s 
                        join FileJpaEntity f on s.popupImage = f.id
                        where ((s.status = 'ACTIVE' 
                            and s.startAt  <= :startAt 
                            and s.endAt >= :endAt ) or (s.status = 'ALWAYS'))
                            and s.position = :position
                            and s.exposure = 'POPUP'
                    """
    )
    fun getMainPopupListByPostitionIng(startAt: Date, endAt: Date, position: SlidePosition?, pageRequest: PageRequest?): List<SlideJpaEntity>

    @Query(
        value= """
                        select new SlideJpaEntity ( 
                        s.id, s.webViewTitle, s.createdAt, s.updatedAt, s.status, 
                        s.numOrder, s.type, s.createdBy, s.startAt, s.endAt, 
                        s.eventsId, s.position, s.subImage, s.exposure, s.linkUrl,
                        f.url ) from SlideJpaEntity s 
                        join FileJpaEntity f on s.popupImage = f.id
                        where ((s.status = 'ACTIVE' 
                            and s.startAt <= sysdate() 
                            and s.endAt >= sysdate()) or (s.status = 'ALWAYS'))
                            and s.position = :position
                            and s.exposure = 'POPUP'
                    """
    )
    fun findCommunityBanner(position: SlidePosition?, pageRequest: PageRequest?): List<SlideJpaEntity>


}
