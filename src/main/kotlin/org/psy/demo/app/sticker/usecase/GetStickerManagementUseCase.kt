package org.psy.demo.app.sticker.usecase

import org.psy.demo.app.sticker.response.StickerManagementResponse
import org.psy.demo.common.PageParam

interface GetStickerManagementUseCase {

    fun getStickers(tagName: String?, pageParam: PageParam): StickerManagementResponse

}
