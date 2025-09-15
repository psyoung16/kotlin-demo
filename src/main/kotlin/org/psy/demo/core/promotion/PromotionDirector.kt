package org.psy.demo.core.promotion

import org.psy.demo.core.user.domain.User
import org.springframework.stereotype.Component

@Component
class PromotionDirector(
    private val mainPromotionBuilder: MainPromotionBuilder
) {
    fun constructMainPromotion(promotionDirectorRequest: PromotionDirectorRequest, userId: User.UserId): MainPopup {
        return mainPromotionBuilder.build(userId, promotionDirectorRequest)
    }
}
//지금은 단순하지만 ... 예를 들어 저 builder가 여러개일 떄 director는 좋음
/*interface PromotionBuilder {
    fun build(request: PromotionDirectorRequest, userId: User.UserId): Promotion
}

class MainPromotionBuilder : PromotionBuilder { ... }
class SidebarPromotionBuilder : PromotionBuilder { ... }
class PopupPromotionBuilder : PromotionBuilder { ... }

class PromotionDirector(
    private val mainBuilder: MainPromotionBuilder,
    private val sidebarBuilder: SidebarPromotionBuilder,
    private val popupBuilder: PopupPromotionBuilder
) {
    fun createPromotions(request: PromotionDirectorRequest, userId: User.UserId): List<Promotion> {
        return listOf(
            mainBuilder.build(request, userId),
            sidebarBuilder.build(request, userId),
            popupBuilder.build(request, userId)
        )
    }
}*/




data class PromotionDirectorRequest(
//    val userInfo: User.UserId?,
    val id: String,
    val title: String,
    val imageRequest: PromotionImageRequest,
    val routeRequest: PromotionRouteRequest
)
