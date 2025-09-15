package org.psy.demo.infra.sticker.repository

import org.psy.demo.app.sticker.response.StickerManagementResponse
import org.psy.demo.common.MetaData
import org.psy.demo.common.PageParam
import org.psy.demo.core.sticker.domain.Sticker
import org.psy.demo.core.sticker.domain.StickerManagement
import org.psy.demo.infra.sticker.jpaEntity.StickerJpaEntity
import org.psy.demo.core.user.domain.User
import org.springframework.data.domain.Page
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.YearMonth
import java.util.Date

@Repository
class StickerRepository(
    private val stickerJpaRepository: StickerJpaRepository,
    private val userStickerJpaRepository: UserStickerJpaRepository
)  {
    fun loadStickerByTagNameWithRandomUnderLineColor(tagName: String, pageParam: PageParam): Page<Sticker> {

        val stickers: Page<StickerJpaEntity> =
            tagName.let {
                if (it.isEmpty() || it == "전체") stickerJpaRepository.findByIsShow(pageParam.toPageRequest()) //4.17 기준으로 사용x, 임시로 놔둠
                else stickerJpaRepository.findByTagNameIsShow(tagName, pageParam.toPageRequest())
            }


        return stickers.map(StickerJpaEntity::mapToDomain)
    }

    fun loadStickerByStickerId(stickerId: Long?): Sticker? {
        val stickerJpaEntity: StickerJpaEntity? = stickerJpaRepository.findByStickerId(stickerId)
        return stickerJpaEntity?.mapToDomain()
    }

    fun loadStickerCountByYearMonth(monthYear: String?, userId: User.UserId?): Int {
        val yearMonth = YearMonth.parse(monthYear)

        return userStickerJpaRepository.countByUserIdAndMonth(
            LocalDate.parse("$monthYear-01"),
            LocalDate.parse(monthYear + "-" + yearMonth.lengthOfMonth()),
            userId?.id?.toLong()
        )

    }

    fun loadStickerManagementsBytagName(tagName: String?, pageParam: PageParam): StickerManagementResponse {
        val stickers: Page<StickerJpaEntity> = stickerJpaRepository.findByTagName(tagName, pageParam.toPageRequest())


        return StickerManagementResponse(
            stickers.content.map(StickerJpaEntity::mapToDomainManagement),
            MetaData.of(pageParam.page, pageParam.size, stickers.totalElements)
        )

    }

    fun loadStickerManagement(stickerId: Long?): StickerManagement? {
        val stickerJpaEntity: StickerJpaEntity? = stickerJpaRepository.findByStickerId(stickerId)
        return stickerJpaEntity?.mapToDomainManagement()
    }

    fun createStickerManagement(stickerManagement: StickerManagement) {
        /*stickerJpaRepository.save(StickerJpaEntity(
            id = stickerManagement.id ?: 0L,
            name = stickerManagement.name,
            tagName = stickerManagement.tagName,
            imgUrl = stickerManagement.imgUrl,
            status = stickerManagement.status,
            writerName = stickerManagement.writerName,
            isShow = stickerManagement.isShow,
            createdAt = stickerManagement.createdAt ?: Date().toUTCDate(),
            updatedAt = Date().toUTCDate(),
        ))*/

        stickerJpaRepository.save(
            StickerJpaEntity(
                id = stickerManagement.id, // null이면 insert, 값이 있으면 update
                name = stickerManagement.name,
                tagName = stickerManagement.tagName,
                imgUrl = stickerManagement.imgUrl,
                status = stickerManagement.status,
                writerName = stickerManagement.writerName,
                isShow = stickerManagement.isShow,
                createdAt = if (stickerManagement.id == null) Date() else stickerManagement.createdAt ?: Date(),
                updatedAt = Date()
            )
        )



    }

    fun updateStickerTagName(beforeTagName: String?, changeTagName: String?) {
        stickerJpaRepository.updateStickerTagName(beforeTagName, changeTagName, Date())
    }


}
