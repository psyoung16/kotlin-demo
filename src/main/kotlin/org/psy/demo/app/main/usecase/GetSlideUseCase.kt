package org.psy.demo.app.main.usecase

import org.psy.demo.app.sdui.translator.Tabs
import org.psy.demo.core.content.domain.Banner
import org.psy.demo.core.content.domain.BannerWithPagination
import org.psy.demo.core.content.domain.PopupWithPagination
import org.psy.demo.core.user.domain.User


interface GetSlideUseCase {

    fun getMainSlideBanners(userId: User.UserId?): BannerWithPagination?
    fun getMainSlidePopups(userId: User.UserId?): PopupWithPagination?

    fun getCoummunityUITabs(tabId: Tabs.TAB_ID): List<Banner>



}
