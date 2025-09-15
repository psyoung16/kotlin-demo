package org.psy.demo.app.sdui.usecase

import org.psy.demo.common.PageParam
import org.psy.demo.core.sdui.mapper.compositor.ProductItemCompositor
import org.psy.demo.app.sdui.translator.Tabs
import org.psy.demo.app.sdui.translator.container.Section
import org.psy.demo.core.user.domain.User
import java.time.LocalDate



interface GetMyRoomUIUseCase {


    fun getMyRoomMyProfile(requestUserId: User.UserId, targetUserId: User.UserId): ProductItemCompositor
    fun getMyRoomTodayMySticker(userId: User.UserId, date: LocalDate): ProductItemCompositor

    fun getMyRoomRecentUploadFeeds(
        requestUserId: User.UserId, targetUserId: User.UserId, pageParam: PageParam,
        display: Display, tabId: Tabs.TAB_ID?
    ): Section<ProductItemCompositor>

    fun getMyRoomMyBadge(userId: User.UserId): Section<ProductItemCompositor>

    fun getMyRoomStorys(userId: User.UserId): Section<ProductItemCompositor>

    fun getMyRoomPostTabs(userId: User.UserId): Section<ProductItemCompositor>

    enum class Display {
        MAIN,
        SUB_POST,
        SUB_FEED
    }
}


data class GetMyRoomUIMainOtherResponse(
    val profile: ProductItemCompositor,
    val recentUploadFeed: Section<ProductItemCompositor>,
    val recentUploadPost: Section<ProductItemCompositor>,
    val myBadge: Section<ProductItemCompositor>
)


data class GetMyRoomUIStoryResponse(
    val curation: Section<ProductItemCompositor>
)

data class GetMyRoomUIFeedResponse(
    val feeds: Section<ProductItemCompositor>
)


data class GetRoomUIMainResponse(
    val profile: ProductItemCompositor,
    val todayMySticker: ProductItemCompositor,
    val recentUploadFeed: Section<ProductItemCompositor>,
    val recentUploadPost: Section<ProductItemCompositor>,
    val myBadge: Section<ProductItemCompositor>
)
