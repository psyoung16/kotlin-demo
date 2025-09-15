package org.psy.demo.core.sdui.mapper.factory.product

import org.psy.demo.app.sdui.translator.BaseMixin
import org.psy.demo.app.sdui.translator.SupportingMixin
import org.psy.demo.core.sdui.mapper.factory.DomainBaseMixinMapper
import org.psy.demo.core.sdui.mapper.factory.DomainSupportingMixinMapper
import org.psy.demo.core.user.domain.User

class UserMixinMapper {

    data class UserInsightWithFollowed(
        val totalFollower: Int,
        val totalFollowing: Int,
        val totalReported: Int,
        val bio: String,
        val isFollowed: Boolean?
    )


    companion object {

        val baseMixin: DomainBaseMixinMapper<User> = { product ->
            with(product) {
                BaseMixin(
                    id = userId.id,
                    title = nickname,
                    imageUrl = avatarImg
                )
            }
        }
        val supportingMixin: DomainSupportingMixinMapper<User, UserInsightWithFollowed> = { product ->
            with(product) {
                SupportingMixin(
                    UserInsightWithFollowed(
                        totalFollower = insights.totalFollower,
                        totalFollowing = insights.totalFollowing,
                        totalReported = insights.totalReported,
                        bio = insights.bio,
                        isFollowed = followedInfo.isFollowed
                    )
                )
            }

        }

    }

}
