package org.psy.demo.infra.jpaRepository

import org.psy.demo.infra.jpaEntity.UserJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserJpaRepository  : JpaRepository<UserJpaEntity, Long>{
    fun findByIdIn(ids: List<Long>): List<UserJpaEntity>

    @Query("SELECT u FROM UserJpaEntity u WHERE u.id = :id")
    fun findId(id: Long): UserJpaEntity?

    @Query("SELECT u FROM UserJpaEntity u WHERE u.id = :id and u.status = 'ACTIVE'")
    fun findIdAndStatus(id: Long): UserJpaEntity?

    @Query("""
        SELECT u FROM UserJpaEntity u
        WHERE u.id = :targetUserId
        and u.id not in (
            select b.blockedPersonId from BlockJpaEntity b
            where b.blockerId = :requestUserId
            and b.blockedPersonId = :targetUserId
            and b.status = 'ACTIVE'
        )
    """)
    fun findUserExcludingBlocked(requestUserId: Long, targetUserId: Long): UserJpaEntity?

}
