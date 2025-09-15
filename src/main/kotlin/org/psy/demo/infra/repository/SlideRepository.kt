package org.psy.demo.infra.repository

import org.psy.demo.app.sdui.translator.Tabs
import org.psy.demo.common.PageParam
import org.psy.demo.core.content.domain.Banner
import org.psy.demo.core.content.domain.Popup
import org.psy.demo.core.user.domain.User
import org.psy.demo.core.vo.SlidePosition
import org.psy.demo.infra.jpaEntity.SlideJpaEntity
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
        val now = Date()
        return slideJpaRepository.getMainPopupListByPostitionIng(
            now, now,
            SlidePosition.MAIN,
            pageParam.toPageRequest()
        ).map { slideEntity ->
            val popup = slideEntity.mapToPopup()
            if (popup.type == SlideJpaEntity.SlideType.EVENT && popup.linkUrl.contains("eventsId")) {
                popup.type = SlideJpaEntity.SlideType.WEB_VIEW
                popup.linkUrl = buildString {
                    append(popup.linkUrl)
                    userId?.let { append("&id=${it.id}") }
                }
            }
            popup
        }
    }

    fun loadMainBanner(userId: User.UserId?, pageParam: PageParam): List<Banner> {
        val now = Date()
        return slideJpaRepository.getMainBannerListByPostitionIng(
            now, now,
            SlidePosition.MAIN,
            pageParam.toPageRequest()
        ).map { slideEntity ->
            val banner = slideEntity.mapToBanner()
            if (banner.type == SlideJpaEntity.SlideType.EVENT && banner.linkUrl.contains("eventsId")) {
                banner.type = SlideJpaEntity.SlideType.WEB_VIEW
                banner.linkUrl = buildString {
                    append(banner.linkUrl)
                    userId?.let { append("&id=${it.id}") }
                }
            }
            banner
        }
    }

    fun loadCommunityBanner(userId: User.UserId?, pageParam: PageParam, tagId: Tabs.TAB_ID): List<Banner> {
        // TODO: Community banner 로직 구현 필요
        return emptyList()
    }
}