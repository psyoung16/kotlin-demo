package org.psy.demo.core.sdui.facade

import org.psy.demo.common.PageParam
import org.psy.demo.common.postInfoIdsByAll
import org.psy.demo.common.postInfoIdsByFeed
import org.psy.demo.common.postInfoIdsByPosts
import org.psy.demo.app.main.usecase.GetBadgeUseCase
import org.psy.demo.app.main.usecase.GetUserUseCase
import org.psy.demo.app.main.usecase.GetPostUseCase
import org.psy.demo.app.main.usecase.GetStoryUseCase
import org.psy.demo.core.sdui.mapper.compositor.ProductItemCompositor
import org.psy.demo.core.sdui.mapper.factory.product.ProductCompositorFactory
import org.psy.demo.core.sdui.mapper.factory.section.SectionBuilder
import org.psy.demo.app.sdui.usecase.GetMyRoomUIUseCase
import org.psy.demo.app.sdui.translator.Tabs
import org.psy.demo.app.sdui.translator.container.Section
import org.psy.demo.app.sdui.translator.container.SectionType
import org.psy.demo.app.sticker.usecase.GetStickerUseCase
import org.psy.demo.core.post.domain.Story
import org.psy.demo.core.post.domain.Post
import org.psy.demo.core.user.domain.Badge
import org.psy.demo.core.user.domain.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional(readOnly = true)
class GetMyRoomUIFacade(
    private val stickerUseCase: GetStickerUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getPostUseCase: GetPostUseCase,
    private val getBadgeUseCase: GetBadgeUseCase,
    private val getStoryUseCase: GetStoryUseCase,
) : GetMyRoomUIUseCase {

    //ProductItemCompositor static
    override fun getMyRoomTodayMySticker(userId: User.UserId, date: LocalDate): ProductItemCompositor {
        return ProductCompositorFactory.sticker(stickerUseCase.getCaledarUserStickersByDate(date.toString(), userId))
    }

    override fun getMyRoomMyProfile(requestUserId: User.UserId, targetUserId: User.UserId): ProductItemCompositor {
        return ProductCompositorFactory.profile(getUserUseCase.getMyProfile(requestUserId, targetUserId))
    }


    //Section<ProductItemCompositor>
    fun getPostInfoIds(display: GetMyRoomUIUseCase.Display, tabId: Tabs.TAB_ID?): List<Int> {
        return when {
            display == GetMyRoomUIUseCase.Display.MAIN && tabId == Tabs.TAB_ID.FEED -> postInfoIdsByFeed
            display == GetMyRoomUIUseCase.Display.SUB_FEED -> postInfoIdsByFeed
            display == GetMyRoomUIUseCase.Display.SUB_POST  && tabId == Tabs.TAB_ID.RECENT_COMMENT -> postInfoIdsByAll
            else -> postInfoIdsByPosts
        }
    }

    override fun getMyRoomRecentUploadFeeds(
        requestUserId: User.UserId,
        targetUserId: User.UserId,
        pageParam: PageParam,
        display: GetMyRoomUIUseCase.Display,
        tabId: Tabs.TAB_ID?
    ): Section<ProductItemCompositor> {
        val postInfoIds = getPostInfoIds(display, tabId)
        val posts: List<Post> = if (tabId == Tabs.TAB_ID.RECENT_COMMENT) {
            getPostUseCase.getPostsWithPostInteractionWithPostCreatedInfoOrderingComment(
                requestUserId,
                targetUserId,
                pageParam,
                postInfoIds
            )

        } else {
            getPostUseCase.getPostsWithPostInteractionWithPostCreatedInfo(
                requestUserId,
                targetUserId,
                pageParam,
                postInfoIds
            )
        }

        return settingMyRoomTitleAndType(posts, display, tabId)
    }

    fun settingMyRoomTitleAndType(
        posts: List<Post>,
        display: GetMyRoomUIUseCase.Display,
        tabId: Tabs.TAB_ID?
    ): Section<ProductItemCompositor> {

        return when (display to tabId) {
            GetMyRoomUIUseCase.Display.MAIN to Tabs.TAB_ID.FEED ->
                SectionBuilder.createSection(posts, SectionType.H_CURATION_IMAGE_BUTTON_ROW, "최근 업로드한 피드", true)

            GetMyRoomUIUseCase.Display.MAIN to Tabs.TAB_ID.ETC ->
                SectionBuilder.createSection(posts, SectionType.H_CURATION_IMAGE_TEXT_BUTTON_ROW, "최근 작성한 게시글" , true)

            GetMyRoomUIUseCase.Display.SUB_FEED to Tabs.TAB_ID.FEED ->
                SectionBuilder.createSection(posts, SectionType.V_CONTENT_IMAGE_GRID, null)

            GetMyRoomUIUseCase.Display.SUB_POST to Tabs.TAB_ID.WRITTEN_POST ->
                SectionBuilder.createSection(posts, SectionType.V_CONTENT_MIX_LIST, "작성한 글")

            GetMyRoomUIUseCase.Display.SUB_POST to Tabs.TAB_ID.RECENT_COMMENT ->
                SectionBuilder.createSection(posts, SectionType.V_CONTENT_MIX_LIST, "댓글 단 글")

            else -> throw IllegalArgumentException("Unsupported display and tabId")
        }
    }


    override fun getMyRoomMyBadge(userId: User.UserId): Section<ProductItemCompositor> {

        val badges: List<Badge> = getBadgeUseCase.getUserBadges(userId)

        return SectionBuilder.createSection(badges, SectionType.H_CURATION_IMAGE_TEXT_TOOLTIP_ROW, "꾸밍 배지")
    }

    override fun getMyRoomStorys(userId: User.UserId): Section<ProductItemCompositor> {
        val storys: List<Story> = getStoryUseCase.getStorys(userId)

        return SectionBuilder.createSection(storys, SectionType.H_CURATION_PLUS_IMAGE_TEXT_TOOLTIP_ROW, "스토리")
    }

    override fun getMyRoomPostTabs(userId: User.UserId): Section<ProductItemCompositor> {

        val tabs: List<Tabs> = listOf(
            Tabs(Tabs.TAB_ID.WRITTEN_POST, "작성한 글"),
            Tabs(Tabs.TAB_ID.RECENT_COMMENT, "댓글 단 글"),
        )

        return SectionBuilder.createSection(
            tabs,
            SectionType.V_CONTENT_MIX_LIST,
            ""
        )
    }

}
