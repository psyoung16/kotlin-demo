package org.psy.demo.infra.sticker.repository

import org.psy.demo.core.sticker.domain.Prize
import org.psy.demo.core.sticker.domain.UserPrize
import org.psy.demo.infra.sticker.jpaEntity.PrizeJpaEntity
import org.psy.demo.infra.sticker.jpaEntity.UserPrizeJpaEntity
import org.psy.demo.core.user.domain.User
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import java.util.Date

@Component
class PrizesRepository(
    private val prizeJpaRepository: PrizeJpaRepository,
    private val userPrizeJpaRepository: UserPrizeJpaRepository,
) {
    fun loadPrizes(monthYear: String?, userId: User.UserId?): List<Prize> {
        val prizes: List<PrizeJpaEntity> = prizeJpaRepository.findByMonthYear(monthYear, PageRequest.of(0, 4))
        return prizes.map(PrizeJpaEntity::mapToDomain)
    }

    fun loadUserPrize(monthYear: String?, userId: User.UserId?): UserPrize? {

        val userPrize: UserPrizeJpaEntity? = userPrizeJpaRepository.findByMonthYearAndCreatedBy(
            monthYear, userId?.id?.toLong()
        )

        return userPrize?.mapToDomain()
    }

    fun loadSelectFinalUserPrize(monthYear: String?, userId: User.UserId?): UserPrize? {
        val userPrize: UserPrizeJpaEntity? = userPrizeJpaRepository.findByMonthYearAndCreatedByAndIsFinalSelect(
            monthYear, userId?.id?.toLong(), true
        )

        return userPrize?.mapToDomain()
    }

    fun loadUserPrizeAndIsFinalCheck(monthYear: String?, userId: User.UserId?): UserPrize? {
        val userPrize: UserPrizeJpaEntity? = userPrizeJpaRepository.findByMonthYearAndCreatedByAndIsFinalSelect(monthYear, userId?.id?.toLong(), true)
        return userPrize?.mapToDomain()
    }

    fun selectUserPrize(userPrize: UserPrize) {
        val existingId = userPrize.id.toLongOrNull()
        val userJpaEntity: UserPrizeJpaEntity = UserPrizeJpaEntity(
            id = existingId, // 있으면 update, null이면 insert
            monthYear = userPrize.monthYear,
            createdBy = userPrize.createdBy,
            prizeId = userPrize.prizeId.toLongOrNull() ?: 0L,
            isFinalSelect = false,
            createdAt = if (existingId == null) Date() else null,
            updatedAt = Date()
        )
        userPrizeJpaRepository.save(userJpaEntity)
    }

    fun finalSelectUserPirze(userPrizeId: Long) {
        userPrizeJpaRepository.finalSelectUserPrize(userPrizeId)
    }
}
