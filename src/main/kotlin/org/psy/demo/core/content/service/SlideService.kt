package org.psy.demo.core.content.service

import org.psy.demo.common.MetaData
import org.psy.demo.common.PageParam
import org.psy.demo.app.main.usecase.GetSlideUseCase
import org.psy.demo.app.sdui.translator.Tabs
import org.psy.demo.core.content.domain.Banner
import org.psy.demo.core.content.domain.BannerWithPagination
import org.psy.demo.core.content.domain.PopupWithPagination
import org.psy.demo.core.user.domain.User
import org.psy.demo.infra.repository.SlideRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
private class GetSlideService (
    private val loadSlidePort: SlideRepository,
): GetSlideUseCase {
    override fun getMainSlideBanners(userId: User.UserId?): BannerWithPagination? {
        val pageParam = PageParam.of(1, 12, Sort.by(Sort.Direction.DESC, "numOrder"))
        return loadSlidePort.loadMainBanner(userId, pageParam).let { banners ->
            BannerWithPagination(
                banners,
                MetaData.of(pageParam.page, pageParam.size, banners.size.toLong()) //pageable 어떻게 할건지?
            )
        }
    }

    override fun getMainSlidePopups(userId: User.UserId?): PopupWithPagination? {
        val pageParam = PageParam.of(1, 12, Sort.by(Sort.Direction.DESC, "numOrder"))
        return loadSlidePort.loadMainPopup(userId, pageParam).let { popups ->
            PopupWithPagination(
                popups,
                MetaData.of(pageParam.page, pageParam.size, popups.size.toLong())
            )
        }
    }

    override fun getCoummunityUITabs(
        tabId: Tabs.TAB_ID
    ): List<Banner> {
        val banner: List<Banner> = loadSlidePort.loadCommunityBanner(null, pageParam = PageParam.of(0, 1), tabId)
        return banner
    }
}
