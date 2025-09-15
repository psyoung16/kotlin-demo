package org.psy.demo.infra.mapper

import org.psy.demo.infra.jpaEntity.FollowJpaEntity
import org.psy.demo.core.user.domain.Follow
import org.psy.demo.core.user.domain.User

object FollowMapper {

    fun mapToDomain(entity: FollowJpaEntity): Follow {
        return Follow(
            followId = Follow.FollowId(entity.getId().toString()),
            followerId = User.UserId(entity.getFollowerId().toString()),
            followingId = User.UserId(entity.getFollowingId().toString())
        )
    }

}
