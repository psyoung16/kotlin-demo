package org.psy.demo.core.content.service

import org.psy.demo.admin.translator.BannerMapper
import org.psy.demo.app.main.usecase.GetPromotionUseCase
import org.psy.demo.core.promotion.MainPopup
import org.psy.demo.core.promotion.Promotion
import org.psy.demo.core.promotion.PromotionRequest
import org.psy.demo.core.promotion.PromotionDirector
import org.psy.demo.core.promotion.PromotionDirectorRequest
import org.psy.demo.core.promotion.mock
import org.psy.demo.core.user.domain.User
import org.springframework.stereotype.Service

// 사용 예시
@Service
private class GetPromotionService(
    private val promotionDirector: PromotionDirector,
    private val bannerMapper: BannerMapper
) : GetPromotionUseCase {
    fun createPromotion(user: User.UserId): MainPopup {
        // 라우트 전략도 비슷하게 설정할 수 있습니다
        //slide -> popup으로 만드는건데 문제는....

        // 라우트 전략도 비슷하게 설정할 수 있습니다
        //slide -> popup으로 만드는건데 문제는....
        val banner = mock.banner()
        return promotionDirector.constructMainPromotion(
            PromotionDirectorRequest(
                id = banner.id.toString(),
                title = banner.webViewTitle,
                imageRequest = banner.getPromotionImageRequest(),
                routeRequest = banner.getPromotionRouteRequest()
            ), User.UserId("dummy")
            )
    }

    override fun get(promotionRequest: PromotionRequest): List<Promotion> {
        // 라우트 전략도 비슷하게 설정할 수 있습니다
        //slide -> popup으로 만드는건데 문제는....
        val banner = mock.banner()
        return listOf(
            promotionDirector.constructMainPromotion(
                PromotionDirectorRequest(
                    id = banner.id.toString(),
                    title = banner.webViewTitle,
                    imageRequest = bannerMapper.toPromotionImageRequest(banner),
                    routeRequest = bannerMapper.toPromotionRouteRequest(banner),
                ), User.UserId("dummy")
            )
        )
    }
}


//여기서 데이터를 mock해도 되고 받아도 되고

