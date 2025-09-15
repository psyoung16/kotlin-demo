package org.psy.demo.app.sticker.usecase

import org.psy.demo.common.PageParam
import org.psy.demo.core.goods.domain.Goods
import org.psy.demo.core.common.domain.ManageTag
import org.psy.demo.core.vo.TagType
import org.psy.demo.core.sticker.domain.Sticker
import org.psy.demo.core.sticker.domain.StickerCalendar
import org.psy.demo.core.user.domain.User
import org.springframework.data.domain.Page

interface GetStickerUseCase {

    fun getStickers(tagName: String, pageParam: PageParam): Page<Sticker>
    fun getCaledarUserStickersByDate(date: String?, userId: User.UserId): StickerCalendar

    fun getCalendarUserStickers(year: Int, month: Int,  userId: User.UserId): List<StickerCalendar>
    fun getCalendarGoods(year: Int, month: Int, userId: User.UserId): List<Goods>
    fun getManageTagBySticker(type: TagType): GetManageTagByStickerResponse

    fun getSticker(stickerId: Long): Sticker

}
data class GetManageTagByStickerResponse(val tags: List<ManageTag>)
