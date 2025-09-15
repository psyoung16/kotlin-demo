package org.psy.demo.infra.sticker.repository

import org.psy.demo.core.user.domain.User
import jakarta.transaction.Transactional
import org.psy.demo.core.sticker.domain.StickerCalendar
import org.psy.demo.core.sticker.domain.UserSticker
import org.psy.demo.infra.sticker.jpaEntity.UserStickerJpaEntity
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.YearMonth
import java.util.*

@Component
@Transactional
class UserStickerRepository (
    private val userStickerJpaRepository: UserStickerJpaRepository
){
    fun loadUserStickerByDate(date: String, userId: User.UserId): UserSticker? {
        val userStickerJpaEntity: UserStickerJpaEntity? = userStickerJpaRepository.findOneByUserIdAndDate(userId.id.toLong(), LocalDate.parse(date))
        return userStickerJpaEntity?.mapToDomain()
    }

    fun loadUserStickerById(id: String, userId: User.UserId): UserSticker? {
        val userStickerJpaEntity: UserStickerJpaEntity? = userStickerJpaRepository.findOneByUserIdAndId(userId.id.toLong(), id.toLong())
        return userStickerJpaEntity?.mapToDomain()
    }

    fun loadUserStickerLastDate(year: Int, month: Int, userId: User.UserId): UserSticker? {

        val userStickerJpaEntity : UserStickerJpaEntity? =
            userStickerJpaRepository.findOneByUserIdAndMonthYear(
                LocalDate.of(year, month, 1),
                LocalDate.of(year, month, YearMonth.of(year, month).lengthOfMonth()),
                userId.id.toLong()
            )

        return userStickerJpaEntity?.mapToDomain()
    }

    fun loadUserStickerByMonth(year: Int, month: Int, userId: User.UserId): List<StickerCalendar> {
        val userStickers: List<UserStickerJpaEntity?>? = userStickerJpaRepository.findAllByUserIdAndMonth(
            userId.id.toLong(),
            LocalDate.of(year, month, 1),
            LocalDate.of(year, month, YearMonth.of(year, month).atEndOfMonth().dayOfMonth)
        )

        return generateDateList(year, month).map { tempDate ->
            val userSticker = userStickers?.find { it?.date == tempDate.fullDate }?.mapToDomain()

            StickerCalendar(
                date = tempDate.date,
                fullDate = tempDate.fullDate,
                userSticker = userSticker
            )
        }
    }

    fun generateDateList(year: Int, month: Int): List<TempDate> {
        val dateList = mutableListOf<TempDate>()
        val start = LocalDate.of(year, month, 1)
        val end = YearMonth.of(year, month).atEndOfMonth()

        var date = start
        while (!date.isAfter(end)) {
            dateList.add(TempDate(date.dayOfMonth, date))
            date = date.plusDays(1)
        }

        return dateList
    }
    data class TempDate(val date: Int, val fullDate: LocalDate)


    fun loadStickerCalendarResponseByDate(date: String?, userId: User.UserId?): StickerCalendar {
        val userStciekr : UserStickerJpaEntity? = userStickerJpaRepository.findOneByUserIdAndDate(userId!!.id.toLong(), LocalDate.parse(date))

        return StickerCalendar(
            date = LocalDate.parse(date).dayOfMonth,
            fullDate = LocalDate.parse(date),
            userSticker = userStciekr?.mapToDomain()
        )
    }

    fun createUserSticker(userSticker: UserSticker) {
        //ok
        userStickerJpaRepository.save(
            with(userSticker){
                UserStickerJpaEntity(
                    id = id?.toLong(),
                    title = title,
                    description = description.orEmpty(),
                    stickerId = stickerId.toLong(),
                    createdBy = userId,
                    date = LocalDate.parse(date),
                    underLineColor = underLineColor,
                    createdAt = Date(),
                    updatedAt = Date()
                )
            }
        )
    }
    fun updateUserSticker(userSticker: UserSticker) {
        //ok
        userStickerJpaRepository.updateUserSticker1(
            with(userSticker){
                UserStickerJpaEntity(
                    id = id?.toLong(),
                    title = title,
                    description = description.orEmpty(),
                    stickerId = stickerId.toLong(),
                    createdBy = userId,
                    date = LocalDate.parse(date),
                    underLineColor = underLineColor,
                    updatedAt = Date()
                )
            }
        )
    }


}
