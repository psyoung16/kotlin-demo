package org.psy.demo.infra.sticker.repository

import org.psy.demo.infra.sticker.jpaEntity.UserPrizeJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserPrizeJpaRepository : JpaRepository <UserPrizeJpaEntity, Long>{


    //    Optional<UserPrizeJpaEntity> findByPrizeIdAndMonthYearAndCreatedBy(Long prizeId, String monthYear, Long userId);
    @Query(
        value = """
            select up
            from UserPrizeJpaEntity up
            where up.monthYear = :monthYear and up.createdBy = :userId
            order by up.createdAt desc
            limit 1
"""
    )
    fun findByMonthYearAndCreatedBy(monthYear: String?, userId: Long?): UserPrizeJpaEntity?

    @Query(
        value = """
            select up
            from UserPrizeJpaEntity up
            where up.monthYear = :monthYear and up.createdBy = :userId and up.isFinalSelect = :isFinalSelect
            order by up.createdAt desc
            limit 1 
            """
    )
    fun findByMonthYearAndCreatedByAndIsFinalSelect(
        monthYear: String?,
        userId: Long?,
        isFinalSelect: Boolean
    ): UserPrizeJpaEntity?


    @Modifying
    @Query(
        value = """
            update UserPrizeJpaEntity up
            set up.isFinalSelect = true
            where up.id = :id
            """
    )
    fun finalSelectUserPrize(id: Long)


}
