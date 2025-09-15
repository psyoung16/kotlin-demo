package org.psy.demo.infra.repository

import org.psy.demo.core.vo.NotificationType
import org.psy.demo.core.user.domain.User
import org.psy.demo.infra.jpaRepository.NotiListJpaRepository
import org.springframework.stereotype.Component

@Component
class NotiListRepository (
        private val notiListJpaRepository: NotiListJpaRepository
) {
    fun loadisNotiRead(userId: User.UserId?, notificationType: NotificationType?): Boolean {
        return notiListJpaRepository.findOneByUserIdAndType(userId?.id?.toLong(), notificationType) == null
    }


}
