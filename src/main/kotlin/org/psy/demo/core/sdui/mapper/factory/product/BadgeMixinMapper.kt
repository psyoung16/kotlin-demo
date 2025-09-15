package org.psy.demo.core.sdui.mapper.factory.product

import org.psy.demo.app.sdui.translator.BaseMixin
import org.psy.demo.app.sdui.translator.SupportingMixin
import org.psy.demo.core.sdui.mapper.factory.DomainBaseMixinMapper
import org.psy.demo.core.sdui.mapper.factory.DomainSupportingMixinMapper
import org.psy.demo.core.user.domain.Badge

class BadgeMixinMapper {
    companion object {
        val baseMixin: DomainBaseMixinMapper<Badge> = { product ->
            with(product){
                BaseMixin(
                    id = badgeId.id,
                    title = badgeName,
                    imageUrl = badgeImg,
                )
            }
        }
        val supportingMixin: DomainSupportingMixinMapper<Badge, Unit> = {
            SupportingMixin(
                Unit
            )
        }
    }
}
