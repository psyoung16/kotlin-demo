package org.psy.demo.core.user.service

import org.psy.demo.app.main.usecase.GetUserUseCase
import org.psy.demo.core.user.domain.Follow
import org.psy.demo.infra.repository.FollowedRepository
import org.psy.demo.infra.repository.UserRepository
import org.psy.demo.core.user.domain.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
private class UserService(
    private val userRepository: UserRepository,
    private val followedRepository: FollowedRepository,
) : GetUserUseCase {
    override fun getMyProfile(requestUserId: User.UserId, targetUserId: User.UserId): User {
        return when (requestUserId) {
            targetUserId -> userRepository.loadActiveUser(targetUserId)
            else -> {
                val loadedUser = userRepository.loadActiveUserExcludingBlocked(requestUserId, targetUserId)
                val follow: Follow? = followedRepository.loadFollowed(requestUserId, targetUserId)
                loadedUser.withIsFollowed(follow != null)
            }
        }
    }
}
