package org.psy.demo.core.post.domain

import com.fasterxml.jackson.annotation.JsonProperty
import org.psy.demo.core.user.domain.User

class PostInteraction (
    @JsonProperty("postId")
    val postId: Post.PostId,
    @JsonProperty("userId")
    val userId: User.UserId,
    @JsonProperty("like")
    val isLike: Boolean,
    @JsonProperty("scrap")
    val isScrap: Boolean
){


}