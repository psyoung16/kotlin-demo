package org.psy.demo.infra.jpaRepository

import org.psy.demo.infra.jpaEntity.PostInteractionJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostInteractionJpaRepository: JpaRepository<PostInteractionJpaEntity, Long> {

    fun findAllByPostIdInAndCreatedBy(postId: List<Long>, createdBy: Long): List<PostInteractionJpaEntity>
    fun findAllByCreatedByAndPostIdIn(createdBy: Long,postId: List<Long> ): List<PostInteractionJpaEntity>

}
