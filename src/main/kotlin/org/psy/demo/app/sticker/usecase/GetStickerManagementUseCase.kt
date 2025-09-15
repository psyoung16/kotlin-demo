package org.psy.demo.app.sticker.usecase

import org.psy.demo.app.sticker.response.StickerManagementResponse
import org.psy.demo.common.PageParam
import org.psy.demo.sticker.domain.entity.StickerManagementResponse

interface GetStickerManagementUseCase {

    fun getStickers(tagName: String?, pageParam: PageParam): StickerManagementResponse

}
