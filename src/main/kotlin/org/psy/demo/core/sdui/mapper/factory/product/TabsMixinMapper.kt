package org.psy.demo.core.sdui.mapper.factory.product

import org.psy.demo.app.sdui.translator.BaseMixin
import org.psy.demo.app.sdui.translator.SupportingMixin
import org.psy.demo.core.sdui.mapper.factory.DomainBaseMixinMapper
import org.psy.demo.core.sdui.mapper.factory.DomainSupportingMixinMapper
import org.psy.demo.app.sdui.translator.Tabs

class TabsMixinMapper {

    companion object {
        val baseMixin: DomainBaseMixinMapper<Tabs> = { product ->
            with(product){
                BaseMixin(
                    id = id.name,
                    title = title,
                    imageUrl = null,
                )
            }
        }
        val supportingMixin: DomainSupportingMixinMapper<Tabs, Unit> = {
            SupportingMixin(
                Unit
            )
        }
    }

}
