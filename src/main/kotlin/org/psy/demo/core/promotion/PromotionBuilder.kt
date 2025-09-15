package org.psy.demo.core.promotion

import org.psy.demo.core.content.domain.Banner
import org.psy.demo.infra.jpaEntity.SlideJpaEntity.SlideType
import org.psy.demo.core.user.domain.User
import org.springframework.stereotype.Component
import org.psy.demo.core.vo.SlideExposure
import org.psy.demo.core.vo.SlidePosition
import org.psy.demo.core.vo.SlideSimpleImageResponse
import org.psy.demo.core.vo.SlideStatus

interface PromotionBuilder {
    val position: PromotionPosition
    fun build(user: User.UserId, request: PromotionDirectorRequest): Promotion
}
// Route 전략 인터페이스
interface PromotionRouteStrategy {
    fun get(promotionRouteRequest: PromotionRouteRequest): RouteInfo
}
// Image 전략 인터페이스
interface PromotionImageStrategy {
    fun get(
        promotionImageRequest: PromotionImageRequest
    ): ImageUrl
}

@Component
class MainPromotionBuilder(
    private val imageStrategy: PromotionImageStrategy,
    private val routeStrategy: PromotionRouteStrategy
) : PromotionBuilder {
    override val position: PromotionPosition = PromotionPosition.MAIN_BANNER

    override fun build(user: User.UserId, request: PromotionDirectorRequest): MainPopup {
        return MainPopup(
            id = request.id,
            title = request.title,
            imgUrl = imageStrategy.get(request.imageRequest),
            routeInfo = routeStrategy.get(request.routeRequest),
            position = position
        )
    }
}

//이미지 제너레이터
//이미지는 팝업/배너에 따라달라지고 그 중에서도 position,

object mock{
    fun banner() : Banner {
        return Banner(
            id = 375,
            type = SlideType.EVENT,
            webViewTitle = "최애브랜드 챌린지 당첨자 발표",
            linkUrl = "",
            mainImage = SlideSimpleImageResponse(
                id = 0,
                url = "https://dftgic7kmxqhe.cloudfront.net/SLIDE/9d0377a4-f090-4a93-967a-ea9751f1aa81.png",
                filename = ""
            ),
            subImage = SlideSimpleImageResponse(
                id = 0,
                url = "https://dftgic7kmxqhe.cloudfront.net/SLIDE/9d0377a4-f090-4a93-967a-ea9751f1aa81.png",
                filename = ""
            ),
            popupImage = null,
            numOrder = 10015,
            createdBy = 4541,
            startAt = "2024-07-28T15:00:00.000Z",
            endAt = "2024-08-10 T15:00:00.000Z",
            position = SlidePosition.MAIN,
            exposure = SlideExposure.BANNER,
            eventsId = 302,
            status = SlideStatus.ACTIVE,
            createdAt = "2024-07-29T01:26:15.000Z",
            updatedAt = "2024-07-29T01:26:44.000Z"
        )
    }

}

