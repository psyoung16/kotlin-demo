package org.psy.demo.infra.repository

import org.psy.demo.infra.mapper.BadgeMapper
import org.psy.demo.core.user.domain.Badge
import org.psy.demo.core.user.domain.User
import org.psy.demo.infra.jpaRepository.BadgeJpaRepository
import org.springframework.stereotype.Component


@Component
class BadgeRepository (
    private val badgeJpaRepository: BadgeJpaRepository
) {

    fun loadUserBadge(userId: User.UserId): List<Badge> {
        return badgeJpaRepository.findOwnerId(userId.id.toLong())
            .map { BadgeMapper.mapToDomain(it) }
    }

}
