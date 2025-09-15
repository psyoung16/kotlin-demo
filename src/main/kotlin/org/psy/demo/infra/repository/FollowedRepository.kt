package org.psy.demo.infra.repository

import org.psy.demo.infra.jpaEntity.FollowJpaEntity
import org.psy.demo.infra.mapper.FollowMapper
import org.psy.demo.core.user.domain.Follow
import org.psy.demo.core.user.domain.User
import org.psy.demo.infra.jpaRepository.FollowedJpaRepository
import org.springframework.stereotype.Component

@Component
class FollowedRepository(
    private val followedJpaRepository: FollowedJpaRepository
) {
    fun loadFollowed(followerId: User.UserId, followingId: User.UserId): Follow? {
        val follows: FollowJpaEntity? = followedJpaRepository.findFollowerIdAndFollowingId(followerId.id.toLong(), followingId.id.toLong())

        return follows?.let { FollowMapper.mapToDomain(it) }
    }

}
