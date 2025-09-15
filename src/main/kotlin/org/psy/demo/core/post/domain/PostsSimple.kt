package org.psy.demo.core.post.domain

import com.fasterxml.jackson.annotation.JsonProperty
import org.psy.demo.core.vo.PostInfoType

//유저 정보x
data class PostsSimple(

    @JsonProperty("id")
    val id: Long,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("thumbnailImage")
    val thumbnailImage: String,
    @JsonProperty("postInfoType")
    val postInfoType: PostInfoType

) {

}
