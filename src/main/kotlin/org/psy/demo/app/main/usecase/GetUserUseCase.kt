package org.psy.demo.app.main.usecase

import org.psy.demo.core.user.domain.User

interface GetUserUseCase {

    fun getMyProfile(requestUserId: User.UserId, targetUserId: User.UserId): User




}
