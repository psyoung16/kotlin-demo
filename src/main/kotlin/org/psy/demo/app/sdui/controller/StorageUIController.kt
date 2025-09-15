package org.psy.demo.app.sdui.controller

import org.psy.demo.common.PageParam
import org.psy.demo.common.ResponseDataEntity
import org.psy.demo.common.validation.ValidEnum
import org.psy.demo.core.sdui.mapper.compositor.ProductItemCompositor
import org.psy.demo.app.sdui.usecase.GetStorageUIUseCase
import org.psy.demo.app.sdui.translator.Tabs
import org.psy.demo.app.sdui.translator.container.Section
import org.psy.demo.user.domain.AuthUser
import org.psy.demo.core.user.domain.User
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Controller
@RestController
@RequestMapping("/api/CLIENT/v3.0/storage")
class StorageUIController(
    val getStorageUIUseCase: GetStorageUIUseCase,
) {

    @GetMapping("/contents")
    @PreAuthorize("isAuthenticated()")
    fun getStorageContentsUI(
        @AuthenticationPrincipal authUser: AuthUser,
        @Valid @RequestParam(required = false) @ValidEnum(
            enumClass = Tabs.TAB_ID::class,
            message = "Invalid tabId value"
        ) tabId: String?,
        pageParam: PageParam,
    ): ResponseDataEntity<Section<ProductItemCompositor>> =
        ResponseDataEntity(
            getStorageUIUseCase.getStorageItems(
                userId = User.UserId(authUser.username),
                tabId = Tabs.TAB_ID.valueOf((tabId ?: Tabs.TAB_ID.LIKED).toString()),
                pageParam,
            )
        )

    //postTabs구조
    //main xx
    @GetMapping("/tabs")
    @PreAuthorize("isAuthenticated()")
    fun getStorageTabUI(
        @AuthenticationPrincipal authUser: AuthUser,
    ): ResponseDataEntity<Section<ProductItemCompositor>> =
        ResponseDataEntity(
            getStorageUIUseCase.getStoargeTabs(
                userId = User.UserId(authUser.username),
            )
        )


}
