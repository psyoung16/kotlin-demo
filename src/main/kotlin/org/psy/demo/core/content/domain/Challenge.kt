package org.psy.demo.core.content.domain
import com.fasterxml.jackson.annotation.JsonProperty
import org.psy.demo.common.formatLegacyDate
import org.psy.demo.core.common.domain.ManageTag
import org.psy.demo.core.post.domain.PostsSimple
import java.util.*

data class Challenge(
        @JsonProperty("id")
        val id: Long,
        @JsonProperty("title")
        val title: String?,
        @JsonProperty("startAt")
        val startAt: Date,
        @JsonProperty("endAt")
        val endAt: Date,
        @JsonProperty("linkUrl")
        val linkUrl: String?,
        @JsonProperty("numOrder")
        val numOrder: Int,
        @JsonProperty("primaryTag")
        val primaryTag: String?,
        @JsonProperty("subEventsId")
        val subEventsId: Long?,
        @JsonProperty("mainImgUrl")
        val mainImgUrl: String?,
        @JsonProperty("subImgUrl")
        val subImgUrl: String?,
        @JsonProperty("detailImgUrl")
        val detailImgUrl: String?

){
    companion object{

    }

    init {
        // 필요한 경우 추가적인 초기화 로직
    }


}

data class ChallengeResponse(
    @JsonProperty("id")
        val id: Long,
    @JsonProperty("title")
        val title: String,
    @JsonProperty("startAt")
        val startAt: String,
    @JsonProperty("endAt")
        val endAt: String,
    @JsonProperty("linkUrl")
        val linkUrl: String,
    @JsonProperty("numOrder")
        val numOrder: Int,
    @JsonProperty("files")
        val files: ImageSimple,
    @JsonProperty("files2")
        val files2: ImageSimple,
    @JsonProperty("manage_tags")
        val manage_tags: List<ManageTag>,
    @JsonProperty("posts")
        val posts: List<PostsSimple>
) {
    data class ImageSimple(
        @JsonProperty("id")
            val id : Int,
        @JsonProperty("url")
            val url : String
    )

    companion object {
        fun of(challenge: Challenge, postsSimples: List<PostsSimple> = emptyList()): ChallengeResponse {
            return with(challenge) {
                ChallengeResponse(
                    id = id,
                    title = title.orEmpty(),
                    startAt = startAt.formatLegacyDate(),
                    endAt = endAt.formatLegacyDate(),
                    linkUrl = linkUrl.orEmpty(),
                    numOrder = numOrder,
                    files =  ImageSimple(0, mainImgUrl.orEmpty()),
                    files2 =ImageSimple(0, subImgUrl.orEmpty()),
                    manage_tags = emptyList(),
                    posts = postsSimples
                )
            }
        }
    }

}

