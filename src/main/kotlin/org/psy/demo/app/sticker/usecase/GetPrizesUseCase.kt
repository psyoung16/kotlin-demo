package org.psy.demo.app.sticker.usecase

import org.psy.demo.core.sticker.domain.Prize
import org.psy.demo.core.user.domain.User

interface GetPrizesUseCase {
    fun getPrizes(monthYear: String, userId: User.UserId?): List<Prize>
}
