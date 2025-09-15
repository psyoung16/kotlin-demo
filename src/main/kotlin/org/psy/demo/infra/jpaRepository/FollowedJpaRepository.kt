package org.psy.demo.infra.jpaRepository

import org.psy.demo.infra.jpaEntity.FollowJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Component

@Component
interface FollowedJpaRepository : JpaRepository<FollowJpaEntity, Long> {

    @Query("SELECT f FROM FollowJpaEntity f WHERE f.followerId = :followerId AND f.followingId = :followingId AND f.status = 'ACTIVE'")
    fun findFollowerIdAndFollowingId(followerId: Long, followingId: Long): FollowJpaEntity?

}
