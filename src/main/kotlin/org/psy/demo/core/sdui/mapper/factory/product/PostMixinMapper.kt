package org.psy.demo.core.sdui.mapper.factory.product

import org.psy.demo.common.strTime
import org.psy.demo.app.sdui.translator.BaseMixin
import org.psy.demo.app.sdui.translator.SupportingMixin
import org.psy.demo.core.post.domain.Post
import org.psy.demo.core.sdui.mapper.factory.DomainBaseMixinMapper
import org.psy.demo.core.sdui.mapper.factory.DomainSupportingMixinMapper

class PostMixinMapper {

    data class PostItemSupporting(
        val description: String,
        val category: String,
        val time: String,
        val profileName: String,
        val profileImageUrl: String?,
        val isLike: Boolean,
        val isScrap: Boolean
    )

    companion object {
        val baseMixin: DomainBaseMixinMapper<Post> = { product ->
            with(product){
                BaseMixin(
                    id = postId.id,
                    title = title,
                    imageUrl = imageUrl
                )
            }
        }
        val supportingMixin: DomainSupportingMixinMapper<Post, PostItemSupporting> = { product ->
            with(product){
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
