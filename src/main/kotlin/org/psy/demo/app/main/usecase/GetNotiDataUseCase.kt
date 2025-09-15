package org.psy.demo.app.main.usecase

import org.psy.demo.core.common.domain.NotiDataResponse
import org.psy.demo.core.user.domain.User

interface GetNotiDataUseCase {
    fun getMainNotiData(userId: User.UserId): NotiDataResponse
}
