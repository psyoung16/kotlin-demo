package org.psy.demo.core.sdui.mapper.factory.product

import org.psy.demo.common.ImgUtil
import org.psy.demo.common.exception.NOT_AVALIVABLE_DATA
import org.psy.demo.app.sdui.translator.BaseMixin
import org.psy.demo.app.sdui.translator.SupportingMixin
import org.psy.demo.core.sdui.mapper.factory.DomainBaseMixinMapper
import org.psy.demo.core.sdui.mapper.factory.DomainSupportingMixinMapper
import org.psy.demo.core.sticker.domain.StickerCalendar

class StickerMixinMapper{

    data class IsHasSticker(
        val time: String,
        val isHasSticker: Boolean = false
    )

    companion object {
        val baseMixin: DomainBaseMixinMapper<StickerCalendar> = { product ->
            with(product){
                BaseMixin(
                    id = userSticker?.id ?: NOT_AVALIVABLE_DATA,
                    title = userSticker?.title ?: "오늘 하루는 어땠나요?",
                    imageUrl = userSticker?.imgUrl ?: ImgUtil.getImgUrl("/PROFILE/none_sticker_image_1.png")
                )
            }

        }
        val supportingMixin: DomainSupportingMixinMapper<StickerCalendar, IsHasSticker> = { product ->
            with(product){
                SupportingMixin(
                    IsHasSticker(
                        "${String.format("%02d", fullDate.monthValue)}.${String.format("%02d", fullDate.dayOfMonth)}",
                        userSticker != null
                    )
                )
            }
        }

    }

}
