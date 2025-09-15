package org.psy.demo.app.sdui.usecase

import org.psy.demo.common.PageParam
import org.psy.demo.core.sdui.mapper.compositor.ProductItemCompositor
import org.psy.demo.app.sdui.translator.Tabs
import org.psy.demo.app.sdui.translator.container.CommunityUIOrdering
import org.psy.demo.app.sdui.translator.container.FilterContainer
import org.psy.demo.app.sdui.translator.container.Section
import org.psy.demo.app.sdui.translator.container.SectionType
import org.psy.demo.core.user.domain.User

interface GetCommunityUIMainUseCase {

    fun getCommunityUITabs(): List<ProductItemCompositor>
    fun getCommunityUIBanners(tabId: Tabs.TAB_ID): List<ProductItemCompositor>
    fun getCommunityUICuration(userId: User.UserId?, tabId: Tabs.TAB_ID): Section<ProductItemCompositor>
    fun getCommunityUIFilters(tabId: String): FilterContainer

    fun getCommunityUIContents(request: GetCommunityUIContentsQuery): Section<ProductItemCompositor>

}


data class GetCommunityUIMainResponse(
    val tabs: List<ProductItemCompositor>,
    val banners: List<ProductItemCompositor>,
    val curations: Section<ProductItemCompositor>,
    val filters: FilterContainer,
    )

data class GetCommunityUIContentsQuery(
    val userId: User.UserId,
    val tabId: Tabs.TAB_ID,
    val pageParam: PageParam,
    val ordering: CommunityUIOrdering,
    val layoutIconType: SectionType, //TODO SectionType 제한 거는거 필요
    val postTags: List<String>,
    val styleTags: List<String>
)
