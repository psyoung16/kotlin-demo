package org.psy.demo.infra.mapper

import org.psy.demo.core.post.domain.Post
import org.psy.demo.core.post.domain.PostInteraction
import org.psy.demo.infra.jpaEntity.PostInteractionJpaEntity
import org.psy.demo.core.user.domain.User

object PostInteractionMapper {
    fun mapToDomain(entity: PostInteractionJpaEntity) = PostInteraction(
        postId = Post.PostId(entity.getPostId().toString()),
        userId = User.UserId(entity.getCreatedBy().toString()),
        isLike = entity.getIsLike(),
        isScrap = entity.getIsScrap()
    )
}
