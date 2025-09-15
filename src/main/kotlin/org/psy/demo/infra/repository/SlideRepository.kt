package org.psy.demo.infra.repository

import org.psy.demo.common.PageParam
import org.psy.demo.infra.jpaEntity.SlideJpaEntity
import org.psy.demo.app.sdui.translator.Tabs
import org.psy.demo.core.domain.Banner
import org.psy.demo.core.content.domain.Popup
import org.psy.demo.core.vo.SlidePosition
import org.psy.demo.core.user.domain.User
import org.psy.demo.infra.jpaRepository.EventJpaRepository
import org.psy.demo.infra.jpaRepository.SlideJpaRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class SlideRepository(
    private val slideJpaRepository: SlideJpaRepository,
    private val eventJpaRepository: EventJpaRepository
) {
    fun loadMainPopup(userId: User.UserId?, pageParam: PageParam): List<Popup> {
        return slideJpaRepository.getMainPopupListByPostitionIng(
            Date(), Date(),
            SlidePosition.MAIN, pageParam.toPageRequest()).map {
            it.mapToPopup().apply {
                if (type == SlideJpaEntity.SlideType.EVENT && linkUrl.contains("eventsId")) {
                    type = SlideJpaEntity.SlideType.WEB_VIEW
                    linkUrl += "&id=${userId?.id}"
                }
                //특수한 경우... event인데, 그 이벤트가 내부 url이거나 외부 url일 경우 바꿔줘야함... 이거 나중에 로직수정 필요
//                if (type == SlideJpaEntity.SlideType.EVENT) {
//                    if (eventsId != null) {
//                        eventJpaRepository.findId(eventsId).let { event ->
//                            if (event.getContentLinkType() == ContentLinkType.WEB_VIEW){
//                                type = SlideJpaEntity.SlideType.WEB_VIEW
//                                linkUrl = event.getLinkUrl() ?: ""
//                            }
//                            if (event.getContentLinkType() == ContentLinkType.URL){
//                                type = SlideJpaEntity.SlideType.URL
//                                linkUrl = event.getLinkUrl() ?: ""
//                            }
//                        }
//                    }
//                }
            }
        }
    }

    fun loadMainBanner(userId: User.UserId?, pageParam: PageParam ): List<Banner> {
        return slideJpaRepository.getMainBannerListByPostitionIng(
            Date(), Date(),
            SlidePosition.MAIN, pageParam.toPageRequest()).map {
            it.mapToBanner().apply {
                if (type == SlideJpaEntity.SlideType.EVENT && linkUrl.contains("eventsId")) {
                    type = SlideJpaEntity.SlideType.WEB_VIEW
                    linkUrl += "&id=${userId?.id}"
                }
            }
        }
    }

    fun loadCommunityBanner(userId: User.UserId?, pageParam: PageParam, tagId: Tabs.TAB_ID): List<Banner> {
        /*return slideJpaRepository.findCommunityBanner(SlidePosition.MAIN, pageParam.toPageRequest()).map {
            it.mapToBanner().apply {
                if (type == SlideJpaEntity.SlideType.EVENT && linkUrl.contains("eventsId")) {
                    type = SlideJpaEntity.SlideType.WEB_VIEW
                    linkUrl += "&id=${userId?.id}"
                }
                //특수한 경우... event인데, 그 이벤트가 내부 url이거나 외부 url일 경우 바꿔줘야함... 이거 나중에 로직수정 필요
//                if (type == SlideJpaEntity.SlideType.EVENT) {
//                    if (eventsId != null) {
//                        eventJpaRepository.findId(eventsId).let { event ->
//                            if (event.getContentLinkType() == ContentLinkType.WEB_VIEW){
//                                type = SlideJpaEntity.SlideType.WEB_VIEW
//                                linkUrl = event.getLinkUrl() ?: ""
//                            }
//                            if (event.getContentLinkType() == ContentLinkType.URL){
//                                type = SlideJpaEntity.SlideType.URL
//                                linkUrl = event.getLinkUrl() ?: ""
//                            }
//                        }
//                    }
//                }

            }
        }*/
        return listOf()
    }

}
