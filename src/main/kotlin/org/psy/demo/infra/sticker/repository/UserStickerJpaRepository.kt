package org.psy.demo.infra.sticker.repository

import org.psy.demo.infra.sticker.jpaEntity.UserStickerJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface UserStickerJpaRepository : JpaRepository<UserStickerJpaEntity, Long> {

    //    UserStickerJpaEntity
    @Query(
        value = """
            select 
            new UserStickerJpaEntity (
                us.id,
                us.stickerId,
                us.title,
                us.description,
                us.createdBy,
                us.createdAt,
                us.updatedAt,
                us.date,
                s.imgUrl,
                s.tagName,
                us.underLineColor
            )
            from UserStickerJpaEntity us
            join StickerJpaEntity s on us.stickerId = s.id
            and us.date >= :startAt and us.date <= :endAt
            where us.createdBy = :userId
"""
    )
    fun findAllByUserIdAndMonth(userId: Long?, startAt: LocalDate?, endAt: LocalDate?): List<UserStickerJpaEntity?>?

    @Query(
        value =  """
            select 
            new UserStickerJpaEntity (
                us.id,
                us.stickerId,
                us.title,
                us.description,
                us.createdBy,
                us.createdAt,
                us.updatedAt,
                us.date,
                s.imgUrl,
                s.tagName,
                us.underLineColor
            )
            from UserStickerJpaEntity us
            join StickerJpaEntity s on us.stickerId = s.id
            where us.createdBy = :userId and us.date = :date 
            order by us.createdAt desc
            limit 1
"""
    )
    fun findOneByUserIdAndDate(userId: Long, date: LocalDate): UserStickerJpaEntity?
    @Query(
        value =  """
            select 
            new UserStickerJpaEntity (
                us.id,
                us.stickerId,
                us.title,
                us.description,
                us.createdBy,
                us.createdAt,
                us.updatedAt,
                us.date,
                s.imgUrl,
                s.tagName,
                us.underLineColor
            )
            from UserStickerJpaEntity us
            join StickerJpaEntity s on us.stickerId = s.id
            where us.createdBy = :userId and us.id= :id 
            order by us.createdAt desc
            limit 1
"""
    )
    fun findOneByUserIdAndId(userId: Long, id: Long): UserStickerJpaEntity?

    @Query(
        value =  """
            select 
            new UserStickerJpaEntity (
                us.id,
                us.stickerId,
                us.title,
                us.description,
                us.createdBy,
                us.createdAt,
                us.updatedAt,
                us.date,
                s.imgUrl,
                s.tagName,
                us.underLineColor
            )
            from UserStickerJpaEntity us
            join StickerJpaEntity s on us.stickerId = s.id
            where us.createdBy = :userId and us.date >= :startAt and us.date <= :endAt
            order by us.createdAt desc
            limit 1
           
"""
    )
    fun findOneByUserIdAndMonthYear(
        startAt: LocalDate?,
        endAt: LocalDate?,
        userId: Long?
    ): UserStickerJpaEntity?

    @Query(
        value =  """
        select count(us) from UserStickerJpaEntity us
        where us.date >= :startAt 
        and us.date <= :endAt 
        and us.createdBy = :userId
"""
    )
    fun countByUserIdAndMonth(startAt: LocalDate?, endAt: LocalDate?, userId: Long?): Int


    @Modifying
    @Query(value = """
        UPDATE UserStickerJpaEntity us
        SET us.title = :#{#userStickerJpaEntity.title},
            us.description = :#{#userStickerJpaEntity.description},
            us.stickerId = :#{#userStickerJpaEntity.stickerId},
            us.updatedAt = :#{#userStickerJpaEntity.updatedAt},
            us.underLineColor = :#{#userStickerJpaEntity.underLineColor}
        WHERE us.id = :#{#userStickerJpaEntity.id}
    """)
    fun updateUserSticker1(userStickerJpaEntity: UserStickerJpaEntity)
}
