package org.psy.demo.core.post.domain

import com.fasterxml.jackson.annotation.JsonProperty
import org.psy.demo.app.sdui.translator.items.PostCategory
import org.psy.demo.core.user.domain.User

//유저정보 o
data class Post(
    @JsonProperty("postId")
    val postId: PostId,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("imageUrl")
    val imageUrl: String,

    @JsonProperty("description")
    val description: String,
    @JsonProperty("category")
    val category: PostCategory,
    @JsonProperty("time")
    val time: String,
    @JsonProperty("createdBy")
    val createdBy: User.UserId,

    @JsonProperty("postInteraction")
    val postInteraction: PostInteraction,
    @JsonProperty("postCreatedInfo")
    val postCreatedInfo: PostCreatedInfo

    ){
    data class PostId(
        @JsonProperty("id")
        val id: String
    )

    data class PostCreatedInfo(
        @JsonProperty("profileName")
        val profileName: String,
        @JsonProperty("profileImageUrl")
        val profileImageUrl: String?
    )

    fun withPostInteraction(postInteraction: PostInteraction?): Post {
        val interaction = postInteraction ?: PostInteraction(postId, userId = this.createdBy, isLike = false, isScrap = false)
        return this.copy(postInteraction = interaction)
    }

    fun withPostCreatedInfo(postCreatedInfo: User?): Post {

        //이미지를 어떻게 할까...
        //User쪽에서 가져오면 좋을텐데..
        val createInfo = PostCreatedInfo(
            profileName = postCreatedInfo?.nickname.orEmpty(),
            profileImageUrl = postCreatedInfo?.avatarImg
        )
        return this.copy(postCreatedInfo = createInfo)
    }


}