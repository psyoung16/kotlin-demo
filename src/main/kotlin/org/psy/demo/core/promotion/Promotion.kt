package org.psy.demo.core.promotion

import org.psy.demo.core.user.domain.User


data class RouteInfo(
    val url: String,
    val routeType: RouteType

)


interface Promotion{
    val id : String
    val title: String
    val imgUrl: String
    val routeInfo : RouteInfo
}

//배너 + 팝업
data class PromotionRequest(
    val user: User.UserId?,
    val promotionPosition: PromotionPosition
)


enum class PromotionPosition {
    MAIN_POPUP, //메인 팝업
    MAIN_BANNER //메인 배너
}
enum class RouteType {
    ROUTE,  //내부 url 이동
    EXTERNAL_URL, //외부 url 이동
    NONE //반응x
}

//팝업 구현체
data class MainPopup(
    override val id: String, //default
    override val title: String, //default
    override val imgUrl: ImageUrl,
    override val routeInfo: RouteInfo, //클릭했을 때 사용
    val position: PromotionPosition,
) : Promotion
