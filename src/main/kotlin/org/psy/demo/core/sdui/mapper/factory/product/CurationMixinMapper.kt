package org.psy.demo.core.sdui.mapper.factory.product

import com.fasterxml.jackson.annotation.JsonProperty
import org.psy.demo.common.strTime
import org.psy.demo.app.sdui.translator.BaseMixin
import org.psy.demo.app.sdui.translator.SupportingMixin
import org.psy.demo.core.post.domain.Post
import org.psy.demo.core.sdui.mapper.factory.DomainBaseMixinMapper
import org.psy.demo.core.sdui.mapper.factory.DomainSupportingMixinMapper

class CurationMixinMapper {

    data class PostItemSupporting(

        @JsonProperty("description")
        val description: String,
        @JsonProperty("category")
        val category: String,
        @JsonProperty("time")
        val time: String,
        @JsonProperty("profileName")
        val profileName: String,
        @JsonProperty("profileImageUrl")
        val profileImageUrl: String?,
        @JsonProperty("isLike")
        val isLike: Boolean,
        @JsonProperty("isScrap")
        val isScrap: Boolean
    )

    companion object {
        val baseMixin: DomainBaseMixinMapper<Post> = { product ->
            with(product) {
                BaseMixin(
                    id = postId.id,
                    title = title,
                    imageUrl = when(imageUrl.isEmpty()) {
                        true -> postCreatedInfo.profileImageUrl
                        false -> imageUrl
                    }
                )
            }
        }
        val supportingMixin: DomainSupportingMixinMapper<Post, PostItemSupporting> = { product ->
            with(product) {
                SupportingMixin(
                    PostItemSupporting(
                        description = description,
                        category = category.name,
                        time = time.strTime(),
                        profileName = postCreatedInfo.profileName,
                        profileImageUrl = postCreatedInfo.profileImageUrl,
                        isLike = postInteraction.isLike,
                        isScrap = postInteraction.isScrap
                    )
                )
            }
        }
    }

}
