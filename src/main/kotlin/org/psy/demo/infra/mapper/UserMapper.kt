package org.psy.demo.infra.mapper

import org.psy.demo.infra.jpaEntity.UserJpaEntity
import org.psy.demo.core.user.domain.AccountType
import org.psy.demo.core.user.domain.User

object UserMapper {
    fun mapToDomain(entity: UserJpaEntity) = User(
        userId = User.UserId(entity.getId().toString()),
        accountType = entity.getAccountType() ?: AccountType.NORMAL,
        nickname = entity.getNickname(),
        bio = entity.getBio() ?: "",
        avatarImg = entity.getNewAvatarImgUrl(),
        backgroundImg = null,
        insights = User.UserInsights(
            totalFollower = entity.getTotalFollower(),
            totalFollowing = entity.getTotalFollowing(),
            totalReported = entity.getTotalReported(),
            bio = entity.getBio() ?: ""
        ),
        linkShare = entity.getLinkShare() ?: "",
        followedInfo = User.UserInteractionStatus(isFollowed = null)
    )

}
