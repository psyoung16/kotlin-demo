package org.psy.demo.core.user.domain

import org.psy.demo.infra.jpaEntity.BadgeType

data class Badge (

    val badgeId: BadgeId,
    val badgeType: BadgeType,
    val ownerId: User.UserId,

    val badgeImg: String,
    val badgeName: String,

) {
    data class BadgeId(val id: String) {
        companion object {
            fun of(id: String): BadgeId = BadgeId(id)
        }
    }
}
