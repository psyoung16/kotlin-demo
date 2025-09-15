package org.psy.demo.core.content.domain

import org.psy.demo.common.MetaData
import org.psy.demo.core.vo.SlideExposure
import org.psy.demo.core.vo.SlidePosition
import org.psy.demo.core.vo.SlideSimpleImageResponse
import org.psy.demo.core.vo.SlideStatus
import org.psy.demo.core.promotion.ImageGroupData
import org.psy.demo.core.promotion.PromotionImageRequest
import org.psy.demo.core.promotion.PromotionRouteRequest
import org.psy.demo.infra.jpaEntity.SlideJpaEntity

data class Banner(
    val id: Long,
//    val bannerId: BannerId,
    var type: SlideJpaEntity.SlideType,
    val webViewTitle: String,
    var linkUrl: String,

    val mainImage: SlideSimpleImageResponse?,
    val subImage: SlideSimpleImageResponse?,
    val popupImage: SlideSimpleImageResponse?,

    val numOrder: Int,
    val createdBy: Long,

    val startAt: String,
    val endAt: String,
    val position: SlidePosition,
    val exposure: SlideExposure,
    val eventsId: Long?,

    val status: SlideStatus,
    val createdAt: String,
    val updatedAt: String,
) {


    fun getPromotionImageRequest(): PromotionImageRequest {
        return PromotionImageRequest(
            position = position,
            exposure = exposure,
            imageGroupData = ImageGroupData(
                mainImage = mainImage?.url ?: "",
                subImage = subImage?.url ?: "",
                popupImage = popupImage?.url ?: ""
            )
        )
    }

    fun getPromotionRouteRequest(): PromotionRouteRequest {
        return PromotionRouteRequest(
            exposure = exposure,
            type = type,
            url = linkUrl,
            eventsId = eventsId
        )
    }
}

data class BannerWithPagination(
    val slides: List<Banner>,
    val metadata: MetaData
) {}
