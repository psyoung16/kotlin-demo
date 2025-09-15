package org.psy.demo.app.sticker.response

import org.psy.demo.common.MetaData
import org.psy.demo.core.sticker.domain.Sticker

data class StickerResponse (
    val stickers: List<Sticker>,
    val metadata: MetaData
){
}