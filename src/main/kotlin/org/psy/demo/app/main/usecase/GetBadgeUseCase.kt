package org.psy.demo.app.main.usecase

import org.psy.demo.core.user.domain.Badge
import org.psy.demo.core.user.domain.User

interface GetBadgeUseCase {

    fun getUserBadges(userId:  User.UserId): List<Badge>

}
