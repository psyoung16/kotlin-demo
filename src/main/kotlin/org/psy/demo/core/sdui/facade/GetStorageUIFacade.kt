package org.psy.demo.core.sdui.facade

import org.psy.demo.common.PageParam
import org.psy.demo.app.main.usecase.GetGoodsUseCase
import org.psy.demo.app.main.usecase.GetPostUseCase
import org.psy.demo.core.sdui.mapper.compositor.ProductItemCompositor
import org.psy.demo.core.sdui.mapper.factory.section.SectionBuilder
import org.psy.demo.app.sdui.usecase.GetStorageUIUseCase
import org.psy.demo.app.sdui.translator.Tabs
import org.psy.demo.app.sdui.translator.container.Section
import org.psy.demo.app.sdui.translator.container.SectionType
import org.psy.demo.core.goods.domain.Goods
import org.psy.demo.core.post.domain.Post
import org.psy.demo.core.user.domain.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GetStorageUIFacade(
    private val getPostUseCase: GetPostUseCase,
    private val getGoodsUseCase: GetGoodsUseCase

) : GetStorageUIUseCase {
    override fun getLikeStorageUseCase(
        userId: User.UserId,
        pageParam: PageParam
    ): Section<ProductItemCompositor> {
        val post : List<Post> = getPostUseCase.getPostsMyLiked(userId, pageParam)
        return SectionBuilder.createSection(post, SectionType.V_CONTENT_MIX_LIST, "좋아요")
    }

    override fun getScrapStorageUseCase(
        userId: User.UserId,
        pageParam: PageParam
    ): Section<ProductItemCompositor> {
        val post : List<Post> = getPostUseCase.getPostsMyScraped(userId, pageParam)
        return SectionBuilder.createSection(post, SectionType.V_CONTENT_MIX_LIST, "스크랩")
    }

    override fun getWishStorageUseCase(
        userId: User.UserId,
        pageParam: PageParam
    ): Section<ProductItemCompositor> {
        val goods : List<Goods> = getGoodsUseCase.getMyWishGoods(userId, pageParam)
        return SectionBuilder.createSection(goods, SectionType.V_CONTENT_IMAGE_PRICE_GRID, "찜")
    }

    override fun getStorageItems(
        userId: User.UserId,
        tabId: Tabs.TAB_ID,
        pageParam: PageParam
    ): Section<ProductItemCompositor> {

        return if (tabId == Tabs.TAB_ID.LIKED) {
            getLikeStorageUseCase(userId, pageParam)
        } else if (tabId == Tabs.TAB_ID.SCRAP) {
            getScrapStorageUseCase(userId, pageParam)
        } else if (tabId == Tabs.TAB_ID.GOODS_WISH) {
            getWishStorageUseCase(userId, pageParam)
        } else {
            throw IllegalArgumentException("Unsupported tabId")
        }
    }

    override fun getStoargeTabs(
        userId: User.UserId
    ): Section<ProductItemCompositor> {

        val tabs: List<Tabs> = listOf(
            Tabs(Tabs.TAB_ID.GOODS_WISH, "찜한 상품"),
            Tabs(Tabs.TAB_ID.LIKED, "좋아요"),
            Tabs(Tabs.TAB_ID.SCRAP, "스크랩"),
        )

        return SectionBuilder.createSection(
            tabs,
            sectionType = SectionType.H_TAB_UNDERLINE_FULL_ROW,
            title = ""
        )

    }

    fun determineSectionType(tabs: List<Tabs>): SectionType {
        val tabIds = tabs.map { it.id }

        return when {
            tabIds.contains(Tabs.TAB_ID.GOODS_WISH) && tabIds.size == 1 -> SectionType.V_CONTENT_IMAGE_PRICE_GRID
            tabIds.containsAll(listOf(Tabs.TAB_ID.GOODS_WISH, Tabs.TAB_ID.LIKED)) -> SectionType.V_CONTENT_MIX_LIST
            else -> SectionType.V_CONTENT_MIX_LIST
        }
    }

}
