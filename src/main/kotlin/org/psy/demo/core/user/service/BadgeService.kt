package org.psy.demo.core.user.service

import org.psy.demo.app.main.usecase.GetBadgeUseCase
import org.psy.demo.core.user.domain.Badge
import org.psy.demo.infra.repository.BadgeRepository
import org.psy.demo.core.user.domain.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
private class GetBadgeService(
    private val badgeRepository: BadgeRepository,
): GetBadgeUseCase {
    override fun getUserBadges(userId: User.UserId): List<Badge> {
        return badgeRepository.loadUserBadge(userId)
    }


}
