package org.psy.demo.app.main.usecase

import org.psy.demo.core.vo.NotificationType
import org.psy.demo.core.user.domain.User

interface GetNotificationBadeUseCase {
    fun getNotificationBadge(
        userId: User.UserId,
        type: NotificationType
    ): Boolean
}
