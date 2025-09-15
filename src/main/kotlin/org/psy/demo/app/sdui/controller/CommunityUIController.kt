package org.psy.demo.app.sdui.controller

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.psy.demo.common.DEAFULT_COMMUNITY_POSTS_UI_TAB_ID
import org.psy.demo.common.PageParam
import org.psy.demo.common.ResponseDataEntity
import org.psy.demo.common.deserializer.StringToListDeserializer
import org.psy.demo.common.validation.ValidEnum
import org.psy.demo.app.sdui.translator.Tabs
import org.psy.demo.app.sdui.translator.container.CommunityUIFilterType
import org.psy.demo.app.sdui.translator.container.CommunityUIOrdering
import org.psy.demo.app.sdui.translator.container.Section
import org.psy.demo.app.sdui.translator.container.SectionType
import org.psy.demo.user.domain.AuthUser
import org.psy.demo.core.user.domain.User
import jakarta.validation.Valid
import org.psy.demo.app.sdui.usecase.GetCommunityUIContentsQuery
import org.psy.demo.app.sdui.usecase.GetCommunityUIMainResponse
import org.psy.demo.app.sdui.usecase.GetCommunityUIMainUseCase
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*


@Controller
@RestController
@RequestMapping("/api/CLIENT/v3.0/community")
class CommunityUIController(
    val getCommunityUIMainUseCase: GetCommunityUIMainUseCase
) {

    @GetMapping("/main")
    @PreAuthorize("isAuthenticated()")
    fun getCommunityUIMain(
        @AuthenticationPrincipal authUser: AuthUser,
        @Valid @RequestParam(required = false) @ValidEnum(
            enumClass = Tabs.TAB_ID::class,
            message = "Invalid tabId value"
        ) tabId: String?,
    ): ResponseDataEntity<GetCommunityUIMainResponse> {
        //받아야하는거 tab의 id, => id가 없으면 첫번째 인덱스를 선택했다고 가정함.
        val setTabId: Tabs.TAB_ID = Tabs.TAB_ID.valueOf((tabId ?: DEAFULT_COMMUNITY_POSTS_UI_TAB_ID).toString())

        val main = GetCommunityUIMainResponse(
            tabs = getCommunityUIMainUseCase.getCommunityUITabs(),
            banners = getCommunityUIMainUseCase.getCommunityUIBanners(setTabId),
            curations = getCommunityUIMainUseCase.getCommunityUICuration(
                User.UserId(authUser.username.toString()),
                setTabId
            ),
            getCommunityUIMainUseCase.getCommunityUIFilters(setTabId.name),
        )

        return ResponseDataEntity(
            main, "/community/main"
        )
    }


    @GetMapping("/contents")
    @PreAuthorize("isAuthenticated()")
    fun getCommunityUIContentsList(
        @AuthenticationPrincipal authUser: AuthUser,
        @Valid request: CommunityPostsRequest,
        pageParam: PageParam
    ): ResponseDataEntity<Section<*>> = ResponseDataEntity(
        getCommunityUIMainUseCase.getCommunityUIContents(request.toQuery(authUser.username, pageParam)),
        "/community/contents"
    )

}

data class CommunityPostsRequest(
    @field:ValidEnum(enumClass = CommunityUIOrdering::class, message = "Invalid ordering value")
    val searchKey: String? = null,
    @field:ValidEnum(enumClass = CommunityUIFilterType::class, message = "Invalid layoutIcon value")
    val layoutIcon: String? = null,
    val tabId: String? = null,
    @JsonDeserialize(using = StringToListDeserializer::class)
    val postTags: List<String> = emptyList(),
    @JsonDeserialize(using = StringToListDeserializer::class)
    val styleTags: List<String> = emptyList(),
) {
    fun toQuery(username: String, pageParam: PageParam) = GetCommunityUIContentsQuery(
        userId = User.UserId(username),
        tabId = Tabs.TAB_ID.valueOf((tabId ?: DEAFULT_COMMUNITY_POSTS_UI_TAB_ID).toString()),
        pageParam = pageParam,
        ordering = CommunityUIOrdering.valueOf(searchKey ?: CommunityUIOrdering.NEW.name),
        layoutIconType = SectionType.valueOf(layoutIcon ?: SectionType.V_CONTENT_IMAGE_GRID.name),
        postTags = postTags,
        styleTags = styleTags
    )
}
