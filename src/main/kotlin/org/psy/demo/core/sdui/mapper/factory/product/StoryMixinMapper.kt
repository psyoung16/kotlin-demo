package org.psy.demo.core.sdui.mapper.factory.product

import org.psy.demo.app.sdui.translator.BaseMixin
import org.psy.demo.app.sdui.translator.SupportingMixin
import org.psy.demo.core.post.domain.Story
import org.psy.demo.core.sdui.mapper.factory.DomainBaseMixinMapper
import org.psy.demo.core.sdui.mapper.factory.DomainSupportingMixinMapper

class StoryMixinMapper {


    companion object {
        val baseMixin: DomainBaseMixinMapper<
                Story> = { product ->
            with(product){
                BaseMixin(
                    id = storyId.id,
                    title = title,
                    imageUrl = thumbnailUrl
                )
            }
        }
        val supportingMixin: DomainSupportingMixinMapper<Story, Unit> = {
            SupportingMixin(
                Unit
            )
        }
    }

}
