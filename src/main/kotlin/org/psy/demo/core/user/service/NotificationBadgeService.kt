package org.psy.demo.core.user.service

import org.psy.demo.app.main.usecase.GetNotificationBadeUseCase
import org.psy.demo.core.vo.NotificationType
import org.psy.demo.infra.repository.NotiListRepository
import org.psy.demo.core.user.domain.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
private class GetNotificatinBadgeService (
    private val notiListRepository: NotiListRepository
) : GetNotificationBadeUseCase{
    override fun getNotificationBadge(
        userId: User.UserId,
        type: NotificationType
    ): Boolean {
        return notiListRepository.loadisNotiRead(userId, type)
    }
}
