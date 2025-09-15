package org.psy.demo.infra.sticker.repository

import org.psy.demo.infra.sticker.jpaEntity.StickerJpaEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface StickerJpaRepository : JpaRepository<StickerJpaEntity, Long>{


    @Query(value = """
        select s from StickerJpaEntity s
        where s.status = 'ACTIVE' and s.tagName = :tagName
        and s.isShow = true 
        and s.imgUrl is not null and s.imgUrl != ''
        order by s.orderNum desc, s.createdAt desc
        """)
    fun findByTagNameIsShow(tagName: String?, pageRequest: PageRequest?): Page<StickerJpaEntity>
    @Query(value = """
        select s from StickerJpaEntity s
        where s.status = 'ACTIVE'
        and s.isShow = true 
        and s.imgUrl is not null and s.imgUrl != ''
        order by s.createdAt desc
        """)
    fun findByIsShow(pageRequest: PageRequest?): Page<StickerJpaEntity>

    fun findByTagName(tagName: String?, pageRequest: PageRequest?): Page<StickerJpaEntity>

    @Query("""
        select sj from StickerJpaEntity sj where sj.id = :stickerId
    """)
    fun findByStickerId(stickerId: Long?): StickerJpaEntity?

    @Query(value ="""
        select s from StickerJpaEntity s
        where s.status = 'ACTIVE'
        and s.isShow = true
        order by s.createdAt desc
        """)
    fun findAllByIsShow(pageRequest: PageRequest?): Page<StickerJpaEntity>


    @Modifying
    @Query("UPDATE StickerJpaEntity s SET s.tagName = :changeTagName, s.updatedAt = :updatedAt where s.tagName =:beforeTagName")
    fun updateStickerTagName(beforeTagName: String?, changeTagName: String?, updatedAt: Date?)


}
