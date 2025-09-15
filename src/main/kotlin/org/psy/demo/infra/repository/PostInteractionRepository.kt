package org.psy.demo.infra.repository

import org.psy.demo.core.post.domain.Post
import org.psy.demo.core.post.domain.PostInteraction
import org.psy.demo.infra.mapper.PostInteractionMapper
import org.psy.demo.core.user.domain.User
import org.psy.demo.infra.jpaRepository.PostInteractionJpaRepository
import org.springframework.stereotype.Component

@Component
class PostInteractionRepository(
    private val postInterJpaRepository: PostInteractionJpaRepository
) {
    fun loadPostInteractions(postId: List<Post.PostId>, userId: User.UserId?): List<PostInteraction> {

        return if (userId == null) {
            emptyList()
        }else{
            postInterJpaRepository.findAllByCreatedByAndPostIdIn(userId.id.toLong(), postId.map { it.id.toLong() }).map(PostInteractionMapper::mapToDomain)
        }
    }

}
