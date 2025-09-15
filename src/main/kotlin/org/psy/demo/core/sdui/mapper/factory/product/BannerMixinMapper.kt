package org.psy.demo.core.sdui.mapper.factory.product

import org.psy.demo.core.sdui.mapper.factory.DomainBaseMixinMapper
import org.psy.demo.core.sdui.mapper.factory.DomainSupportingMixinMapper
import org.psy.demo.infra.jpaEntity.SlideJpaEntity
import org.psy.demo.core.domain.entity.Banner
import org.psy.demo.core.domain.entity.vo.ContentLinkType
import org.psy.demo.app.sdui.translator.BaseMixin
import org.psy.demo.app.sdui.translator.SupportingMixin

class BannerMixinMapper {

    data class BannerSubMixin(
        val subId: String?,
        val contentLinkType: String,
        val url: String,
    )

    companion object {

        val baseMixin: DomainBaseMixinMapper<Banner> = { product ->
            with(product) {
                BaseMixin(
                    id = id.toString(),
                    title = webViewTitle,
                    imageUrl = mainImage!!.url,
                )
            }
        }
        val supportingMixin: DomainSupportingMixinMapper<Banner, BannerSubMixin> = { product ->
            with(product) {
                SupportingMixin(
                    BannerSubMixin(
                        subId = eventsId?.toString(),
                        contentLinkType = changeEnum(type).toString(),
                        url = linkUrl,
                    )
                )
            }
        }

        private fun changeEnum(slideType: SlideJpaEntity.SlideType): ContentLinkType {
            return when (slideType) {
                SlideJpaEntity.SlideType.URL -> ContentLinkType.URL
                SlideJpaEntity.SlideType.WEB_VIEW -> ContentLinkType.WEB_VIEW
                SlideJpaEntity.SlideType.EVENT -> ContentLinkType.EVENT
                SlideJpaEntity.SlideType.CHALLENGE -> ContentLinkType.EVENT
                // 필요하다면 추가적인 처리를 여기에 구현할 수 있습니다.
            }
        }

    }

}

/*
val dd = json {
    "name" to "John"
    "age" to 30
    "address" to json {
        "city" to "New York"
        "zip" to "10001"
    }
}*/
