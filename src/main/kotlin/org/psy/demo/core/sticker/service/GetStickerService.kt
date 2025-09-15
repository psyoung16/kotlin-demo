package org.psy.demo.core.sticker.service

import org.psy.demo.app.sticker.usecase.GetManageTagByStickerResponse
import org.psy.demo.app.sticker.usecase.GetStickerUseCase
import org.psy.demo.common.PageParam
import org.psy.demo.core.goods.domain.Goods
import org.psy.demo.core.common.domain.ManageTag
import org.psy.demo.core.vo.TagType
import org.psy.demo.core.sticker.domain.Sticker
import org.psy.demo.core.sticker.domain.StickerCalendar
import org.psy.demo.core.sticker.domain.UserSticker
import org.psy.demo.core.user.domain.User
import org.psy.demo.infra.repository.GoodsRepository
import org.psy.demo.infra.repository.ManageTagRepository
import org.psy.demo.infra.sticker.repository.StickerRepository
import org.psy.demo.infra.sticker.repository.UserStickerRepository
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
private class StickerService(
    private val loadStickerPort: StickerRepository,
    private val userStickerRepository: UserStickerRepository,
    private val manageTagRepository: ManageTagRepository,
    private val goodsRepository: GoodsRepository
) : GetStickerUseCase {

    override fun getStickers(tagName: String, pageParam: PageParam): Page<Sticker> {
        return loadStickerPort.loadStickerByTagNameWithRandomUnderLineColor(
            tagName,
            pageParam
        )
    }

    override fun getCaledarUserStickersByDate(date: String?, userId: User.UserId): StickerCalendar {
        return userStickerRepository.loadStickerCalendarResponseByDate(date, userId)
    }

    override fun getCalendarUserStickers(year: Int, month: Int, userId: User.UserId): List<StickerCalendar> {
        return userStickerRepository.loadUserStickerByMonth(year, month, User.UserId(userId.id))
    }

    override fun getCalendarGoods(year: Int, month: Int, userId: User.UserId): List<Goods> {

        //해당 월에 선택한 스티커의 여부
        val userSticker: UserSticker? = userStickerRepository.loadUserStickerLastDate(year, month, userId)
        return userSticker
            ?.let { us -> loadStickerPort.loadStickerByStickerId(us.stickerId.toLong()) }
            ?.let { sticker -> goodsRepository.loadRandomGoodsByStickerCategoryByWriterName(sticker.writerName) }
            ?: goodsRepository.loadRandomGoodsByStickerCategory() // If null, load random 12 goods.
    }

    override fun getManageTagBySticker(type: TagType): GetManageTagByStickerResponse {
        val manageTagList: List<ManageTag?> = manageTagRepository.loadManageTag(TagType.STICKER)
        return GetManageTagByStickerResponse(manageTagList.filterNotNull())
    }

    override fun getSticker(stickerId: Long): Sticker {
        return loadStickerPort.loadStickerByStickerId(stickerId) ?: throw IllegalArgumentException("Sticker not found.")
    }


}
