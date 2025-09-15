package org.psy.demo.app.sticker.response

import org.psy.demo.core.sticker.domain.Prize

data class PrizesResponse(
    val prizes: List<Prize>,
    val selectUserPrizeApply: Boolean
)