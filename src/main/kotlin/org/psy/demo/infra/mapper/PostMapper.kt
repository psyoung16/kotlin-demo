package org.psy.demo.infra.mapper

import org.psy.demo.infra.jpaEntity.PostJpaEntity
import org.psy.demo.app.sdui.translator.items.PostCategory
import org.psy.demo.core.post.domain.Post
import org.psy.demo.core.post.domain.PostInteraction
import org.psy.demo.core.user.domain.User
import java.text.SimpleDateFormat

object PostMapper{
    fun mapToDomain(entity: PostJpaEntity) = Post(
        postId = Post.PostId(entity.getId().toString()),
        description = entity.getDescription(),
        title = entity.getTitle(),
        category = PostCategory.of(entity.getPostInfoId().toString()),
        imageUrl = entity.getThumbnailUrl(),
        time = SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(entity.getCreatedAt()), //entity.getCreatedAt().toString(), //yyyy.mm.dd
        createdBy = User.UserId(entity.getCreatedBy().toString()),
        postInteraction = PostInteraction(
            postId = Post.PostId(entity.getId().toString()),
            userId = User.UserId(entity.getCreatedBy().toString()),
            isLike = false,
            isScrap = false
        ),
        postCreatedInfo = Post.PostCreatedInfo(
            profileName = entity.getCreatedBy().toString(),
            profileImageUrl = entity.getThumbnailUrl()
        )
    )

}
