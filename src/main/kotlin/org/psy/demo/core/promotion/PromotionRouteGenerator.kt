package org.psy.demo.core.promotion

import org.psy.demo.core.vo.SlideExposure
import org.psy.demo.infra.jpaEntity.SlideJpaEntity.SlideType
import org.springframework.stereotype.Component

@Component
class PromotionRouteGenerator(

) : PromotionRouteStrategy {
    override fun get(request: PromotionRouteRequest): RouteInfo {

        return if (request.type == SlideType.URL) { //이건 여기서 끝
            RouteInfo(
                url = request.url,
                routeType = RouteType.EXTERNAL_URL
            )
        } else {
            if (request.type == SlideType.WEB_VIEW){
                return RouteInfo(
                    url = request.url,
                    routeType = RouteType.ROUTE
                )
            }else{
                if (request.type == SlideType.EVENT) {
                    //event type마다 다륻잖아 이거는 사실 migration하는게 나을거긴할듯...
                    return RouteInfo(
                        url = "demoapp://event/vote/${request.eventsId}",
                        routeType = RouteType.ROUTE
                    )
                }
            }
            RouteInfo(
                url = "demoapp://"+request.url, //여기는 route 링크 세팅하는 로직이 필요함
                routeType = RouteType.ROUTE
            )
        }
    }
}

//demoapp:// 으로 시작하는 링크가 있고, https://demoapp.kr로 넘어가는 경우가 있음
//demoapp://
// demoapp://event/vote/{id} - 투표 문답형
// demoapp://event/apply/{id} - 응답형
// demoapp://event/comment/{id} - 댓글형
// demoapp://event/bingo/{id} - 빙고형

// demoapp.kr/eventPage?eventsId=214 - 출석체크형
// demoapp.kr/eventPage?eventsId=214 - 혼합형
// ~~~ - 외부형



//NONE, EXTERNAL_URL
data class PromotionRouteRequest(
    val exposure: SlideExposure,
    val type: SlideType,
    val url: String,
    val eventsId: Long?
)

