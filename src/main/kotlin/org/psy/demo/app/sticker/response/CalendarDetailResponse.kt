package org.psy.demo.app.sticker.response

import org.psy.demo.core.goods.domain.Goods
import org.psy.demo.core.sticker.domain.StickerCalendar

data class CalendarDetailResponse (
    val calendars : List<StickerCalendar>,
    val goodsList : List<Goods>,
    val isShowPrizeButton : Boolean = false
) {
}