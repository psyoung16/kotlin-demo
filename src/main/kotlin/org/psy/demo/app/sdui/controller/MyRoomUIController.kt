package org.psy.demo.app.sdui.controller

import org.psy.demo.common.DEAFULT_MYROOM_POSTS_UI_TAB_ID
import org.psy.demo.common.PageParam
import org.psy.demo.common.ResponseDataEntity
import org.psy.demo.common.toKoreaDate
import org.psy.demo.common.validation.ValidEnum
import org.psy.demo.core.sdui.mapper.compositor.ProductItemCompositor
import org.psy.demo.app.sdui.translator.Tabs
import org.psy.demo.app.sdui.translator.container.Section
import org.psy.demo.user.domain.AuthUser
import org.psy.demo.core.user.domain.User
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.psy.demo.app.sdui.usecase.GetMyRoomUIMainOtherResponse
import org.psy.demo.app.sdui.usecase.GetMyRoomUIUseCase
import org.psy.demo.app.sdui.usecase.GetRoomUIMainResponse
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import java.time.LocalDate


@Controller
@RestController
@RequestMapping("/api/CLIENT/v3.0/myroom")
class MyRoomUIController(
    val getMyRoomUIUseCase: GetMyRoomUIUseCase,
) {

    @GetMapping("/main")
    @PreAuthorize("isAuthenticated()")
    fun getMyRoomUIMain(
        @AuthenticationPrincipal authUser: AuthUser,
    ): ResponseDataEntity<GetRoomUIMainResponse> {
        //TODO - multiSection의 개념으로 넣는것이 좋을까? (하지만
        //받아야하는거 tab의 id, => id가 없으면 첫번째 인덱스를 선택했다고 가정함.
        return ResponseDataEntity(
            GetRoomUIMainResponse(
                getMyRoomUIUseCase.getMyRoomMyProfile(
                    requestUserId = User.UserId(authUser.username),
                    targetUserId = User.UserId(authUser.username),
                ),
                getMyRoomUIUseCase.getMyRoomTodayMySticker(
                    userId = User.UserId(authUser.username),
                    date = LocalDate.now().toKoreaDate()
                ),
                getMyRoomUIUseCase.getMyRoomRecentUploadFeeds(
                    requestUserId = User.UserId(authUser.username),
                    targetUserId = User.UserId(authUser.username),
                    pageParam = PageParam(1, 12),
                    display = GetMyRoomUIUseCase.Display.MAIN,
                    tabId = Tabs.TAB_ID.FEED
                ),
                getMyRoomUIUseCase.getMyRoomRecentUploadFeeds(
                    requestUserId = User.UserId(authUser.username),
                    targetUserId = User.UserId(authUser.username),
                    pageParam = PageParam(1, 1),
                    display = GetMyRoomUIUseCase.Display.MAIN,
                    tabId = Tabs.TAB_ID.ETC
                ),
                getMyRoomUIUseCase.getMyRoomMyBadge(
                    User.UserId(authUser.username)
                )
            )
        )
    }

    @GetMapping("/someoneMain")
    @PreAuthorize("isAuthenticated()")
    fun getCommunityUIMain(
        @AuthenticationPrincipal authUser: AuthUser,
        @NotBlank(message = "targetUserId not null")
        @RequestParam(value = "targetUserId") targetUserId: String
    ): ResponseDataEntity<GetMyRoomUIMainOtherResponse> = ResponseDataEntity(
        //받아야하는거 tab의 id, => id가 없으면 첫번째 인덱스를 선택했다고 가정함.
        GetMyRoomUIMainOtherResponse(
            getMyRoomUIUseCase.getMyRoomMyProfile(
                requestUserId = User.UserId(authUser.username),
                targetUserId = User.UserId(targetUserId),
            ),
            getMyRoomUIUseCase.getMyRoomRecentUploadFeeds(
                requestUserId = User.UserId(authUser.username),
                targetUserId = User.UserId(targetUserId),
                pageParam = PageParam(1, 12),
                display = GetMyRoomUIUseCase.Display.MAIN,
                tabId = Tabs.TAB_ID.FEED
            ),
            getMyRoomUIUseCase.getMyRoomRecentUploadFeeds(
                requestUserId = User.UserId(authUser.username),
                targetUserId = User.UserId(targetUserId),
                pageParam = PageParam(1, 1),
                display = GetMyRoomUIUseCase.Display.MAIN,
                tabId = Tabs.TAB_ID.ETC
            ),
            getMyRoomUIUseCase.getMyRoomMyBadge(
                User.UserId(targetUserId)
            )
        )
    )


    @GetMapping("/feeds")
    @PreAuthorize("isAuthenticated()")
    fun getFeeds(
        @AuthenticationPrincipal authUser: AuthUser,
        pageParam: PageParam,
        @NotBlank(message = "targetUserId not null")
        @RequestParam(value = "targetUserId") targetUserId: String
    ): ResponseDataEntity<Section<ProductItemCompositor>> = ResponseDataEntity(
        //내가 차단한 유저면 못보지...?
        getMyRoomUIUseCase.getMyRoomRecentUploadFeeds(
            requestUserId = User.UserId(authUser.username),
            targetUserId = User.UserId(targetUserId),
            pageParam = PageParam(pageParam.page, pageParam.size),
            display = GetMyRoomUIUseCase.Display.SUB_FEED,
            tabId = Tabs.TAB_ID.FEED
        )
    )


    @GetMapping("/postTabs")
    @PreAuthorize("isAuthenticated()")
    fun getPostsTabs(
        @AuthenticationPrincipal authUser: AuthUser,
        @NotBlank(message = "targetUserId not null")
        @RequestParam(value = "targetUserId") targetUserId: String
    ): ResponseDataEntity<Section<ProductItemCompositor>> =
    //받아야하는거 tab의 id, => id가 없으면 첫번째 인덱스를 선택했다고 가정함.
    //TODO: section 구조 -> item 구조로 변경 필요 --> 이 controller는 uicontroller이므로 section으로 return하는게 낫다고 판단
        //내가 차단한 유저면 못보지...
        ResponseDataEntity(
            getMyRoomUIUseCase.getMyRoomPostTabs(
                User.UserId(authUser.username)
            )
        )


    @GetMapping("/posts")
    @PreAuthorize("isAuthenticated()")
    fun getPosts(
        @AuthenticationPrincipal authUser: AuthUser,
        pageParam: PageParam,

        @NotBlank(message = "targetUserId not null")
        @RequestParam(value = "targetUserId") targetUserId: String,
        @Valid @RequestParam(required = false) @ValidEnum(
            enumClass = Tabs.TAB_ID::class,
            message = "Invalid tabId value"
        ) tabId: String?,

        ): ResponseDataEntity<Section<ProductItemCompositor>> {
        //받아야하는거 tab의 id, => id가 없으면 첫번째 인덱스를 선택했다고 가정함.

        val setTabId = tabId ?: DEAFULT_MYROOM_POSTS_UI_TAB_ID

        return ResponseDataEntity(

            getMyRoomUIUseCase.getMyRoomRecentUploadFeeds(
                requestUserId = User.UserId(authUser.username),
                targetUserId = User.UserId(targetUserId),
                pageParam = PageParam(pageParam.page, pageParam.size),
                display = GetMyRoomUIUseCase.Display.SUB_POST,
                tabId = when (setTabId) {
                    Tabs.TAB_ID.WRITTEN_POST.name -> Tabs.TAB_ID.WRITTEN_POST
                    else -> Tabs.TAB_ID.RECENT_COMMENT
                }
            )
        )
    }


    @GetMapping("/storys")
    @PreAuthorize("isAuthenticated()")
    fun getStorys(
        @AuthenticationPrincipal authUser: AuthUser,
        @NotBlank(message = "targetUserId not null")
        @RequestParam(value = "targetUserId") targetUserId: String
    ): ResponseDataEntity<Section<ProductItemCompositor>> =
        //받아야하는거 tab의 id, => id가 없으면 첫번째 인덱스를 선택했다고 가정함.
        ResponseDataEntity(
            getMyRoomUIUseCase.getMyRoomStorys(
                User.UserId(targetUserId)
            )
        )


}




