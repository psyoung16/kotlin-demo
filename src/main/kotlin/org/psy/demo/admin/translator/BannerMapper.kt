package org.psy.demo.admin.translator

import org.psy.demo.core.domain.Banner
import org.psy.demo.core.promotion.ImageGroupData
import org.psy.demo.core.promotion.PromotionImageRequest
import org.psy.demo.core.promotion.PromotionRouteRequest
import org.springframework.stereotype.Component

@Component
class BannerMapper {
    fun toPromotionImageRequest(banner: Banner): PromotionImageRequest {
        return PromotionImageRequest(
            position = banner.position,
            exposure = banner.exposure,
            imageGroupData = ImageGroupData(
                mainImage = banner.mainImage?.url ?: "",
                subImage = banner.subImage?.url ?: "",
                popupImage = banner.popupImage?.url ?: ""
            )
        )
    }

    fun toPromotionRouteRequest(banner: Banner): PromotionRouteRequest {
        return PromotionRouteRequest(
            exposure = banner.exposure,
            type = banner.type,
            url = banner.linkUrl,
            eventsId = banner.eventsId
        )
    }
}
