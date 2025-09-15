package org.psy.demo.core.promotion

import org.psy.demo.core.vo.SlideExposure
import org.psy.demo.core.vo.SlidePosition
import org.springframework.stereotype.Component

typealias RouteImg = String
typealias ImageUrl = String
@Component
class PromotionImageGenerator(
) : PromotionImageStrategy {
    override fun get(imageGroupData: PromotionImageRequest): ImageUrl {

        return when {
            imageGroupData.position == SlidePosition.MAIN && imageGroupData.exposure == SlideExposure.BANNER -> imageGroupData.imageGroupData.mainImage
            imageGroupData.position == SlidePosition.SHOP_MAIN && imageGroupData.exposure == SlideExposure.BANNER -> imageGroupData.imageGroupData.subImage
            imageGroupData.position == SlidePosition.MAIN && imageGroupData.exposure == SlideExposure.POPUP -> imageGroupData.imageGroupData.popupImage
            else -> ""
        }
    }
}

data class PromotionImageRequest(
    val position: SlidePosition,
    val exposure: SlideExposure,
    val imageGroupData: ImageGroupData
)

//이미지들
data class ImageGroupData(
    val mainImage: String,
    val subImage: String,
    val popupImage: String
)
