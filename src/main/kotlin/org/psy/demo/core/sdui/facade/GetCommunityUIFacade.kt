package org.psy.demo.core.sdui.facade

import org.psy.demo.common.exception.NOT_AVALIVABLE_DATA
import org.psy.demo.app.main.usecase.GetManageTagUseCase
import org.psy.demo.app.main.usecase.GetPostUseCase
import org.psy.demo.core.sdui.mapper.compositor.ProductItemCompositor
import org.psy.demo.core.sdui.mapper.factory.product.ProductCompositorFactory
import org.psy.demo.core.sdui.mapper.factory.section.SectionBuilder
import org.psy.demo.app.sdui.translator.container.FilterContainer
import org.psy.demo.app.sdui.translator.container.FilterItem
import org.psy.demo.app.sdui.translator.container.FilterItems
import org.psy.demo.app.sdui.translator.container.FilterTag
import org.psy.demo.app.sdui.translator.container.FilterTagType
import org.psy.demo.app.sdui.usecase.GetCommunityUIContentsQuery
import org.psy.demo.app.sdui.usecase.GetCommunityUIMainUseCase
import org.psy.demo.app.sdui.translator.Tabs
import org.psy.demo.app.sdui.translator.container.HeaderUI
import org.psy.demo.app.sdui.translator.container.Section
import org.psy.demo.app.sdui.translator.container.SectionType
import org.psy.demo.app.sdui.translator.items.AlignItems
import org.psy.demo.core.domain.Banner
import org.psy.demo.core.vo.TagType
import org.psy.demo.core.post.domain.Post
import org.psy.demo.core.user.domain.User
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GetCommunityUIFacade(
    private val getPostUseCase: GetPostUseCase,
    private val getManageTagUseCase: GetManageTagUseCase
) : GetCommunityUIMainUseCase {

    override fun getCommunityUITabs(): List<ProductItemCompositor> {

        val tabs: List<Tabs> = listOf(
            Tabs(Tabs.TAB_ID.FEED, "피드"),
            Tabs(Tabs.TAB_ID.ETC, "잡담")
        )

        return tabs.map { tab -> ProductCompositorFactory.tabs(tab) }
    }

    override fun getCommunityUIBanners(tabId: Tabs.TAB_ID): List<ProductItemCompositor> {
        val banners: List<Banner> = listOf()
        return banners.map { banner -> ProductCompositorFactory.banners(banner) }
    }


    override fun getCommunityUICuration(userId: User.UserId?, tabId: Tabs.TAB_ID): Section<ProductItemCompositor> {
        val posts: List<Post> = getPostUseCase.getCommunityCuration(userId ?: User.UserId(""), tabId)
        return SectionBuilder.createSection(
            posts,
            when (tabId) {
                Tabs.TAB_ID.FEED -> SectionType.H_CURATION_IMAGE_ROW
                Tabs.TAB_ID.ETC -> SectionType.H_CURATION_IMAGE_TEXT_ROW
                else -> SectionType.H_CURATION_IMAGE_ROW
            },
            when (tabId) {
                Tabs.TAB_ID.FEED -> "유저들이 좋아하는 콘텐츠"
                Tabs.TAB_ID.ETC -> "지금 뜨고 있는 글"
                else -> "유저들이 좋아하는 콘텐츠"
            }
        )
    }

    @Cacheable(value = ["getCommunityUIFilters"], key = "#tabId")
    override fun getCommunityUIFilters(tabId: String): FilterContainer {
        val commonFilterContainer = FilterContainer(
            id = NOT_AVALIVABLE_DATA,
            layoutIcon = when (tabId) {
                "FEED" -> listOf(SectionType.V_CONTENT_IMAGE_GRID, SectionType.V_CONTENT_IMAGE_TEXT_LIST)
                else -> listOf(SectionType.V_CONTENT_MIX_LIST)
            },
            aligns = when (tabId) {
                "FEED" -> AlignItems.feedItems()
                else -> AlignItems.etcItems()
            },
            tags = when (tabId) {
                "FEED" -> {
                    listOf(
                        FilterTag(
                            type = FilterTagType.STYLE_TAG,
                            searchKey = "styleTags",
                            headers = HeaderUI(title = "스타일"),
                            items = FilterItems.styleItems()
                        ),
                        FilterTag(
                            type = FilterTagType.TAG,
                            searchKey = "postTags",
                            headers = HeaderUI(title = "태그"),
                            items = getManageTagUseCase.getRecommandManageTag(TagType.RECOMMEND).map {
                                FilterItem(
                                    id = it.tagName,
                                    title = it.tagName,
                                    imageUrl = null
                                ) //추후 Id로 하기 이건 ManageTag id라서 일단 string으로...
                            }
                        ))
                }

                else -> listOf()
            }
        )
        return commonFilterContainer
    }

    override fun getCommunityUIContents(request: GetCommunityUIContentsQuery): Section<ProductItemCompositor> {
        val post: List<Post> = getPostUseCase.getCoummunityContents(request)
        return SectionBuilder.createSection(
            entities = post,
            sectionType = when (request.tabId) {
                Tabs.TAB_ID.FEED -> SectionType.V_CONTENT_IMAGE_GRID
                Tabs.TAB_ID.ETC -> SectionType.V_CONTENT_IMAGE_TEXT_LIST
                else -> SectionType.V_CONTENT_IMAGE_GRID
            },
        )
    }


}

