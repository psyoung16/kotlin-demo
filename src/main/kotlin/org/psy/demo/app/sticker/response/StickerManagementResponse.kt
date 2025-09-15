package org.psy.demo.app.sticker.response

import org.psy.demo.common.MetaData
import org.psy.demo.core.sticker.domain.StickerManagement

data class StickerManagementResponse(
    val stickerManagementList : List<StickerManagement>,
    val metadata: MetaData
)