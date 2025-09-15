package org.psy.demo.app.sdui.controller

import org.psy.demo.common.PageParam
import org.psy.demo.common.ResponseDataEntity
import org.psy.demo.common.exception.CustomException
import org.psy.demo.common.exception.ErrorCode
import org.psy.demo.common.validation.ValidEnum
import org.psy.demo.infra.jpaEntity.StyleTagType
import org.psy.demo.core.sdui.mapper.compositor.ProductItemCompositor
import org.psy.demo.app.sdui.usecase.GetSearchUIUseCase
import org.psy.demo.app.sdui.translator.container.Section
import org.psy.demo.user.domain.AuthUser
import org.psy.demo.core.user.domain.User
import jakarta.validation.Valid
import jakarta.validation.constraints.Pattern
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Controller
@RestController
@RequestMapping("/api/CLIENT/v3.0/search")
class SearchUIController(
    val getSearchUIUseCase: GetSearchUIUseCase
) {

    @GetMapping("/tag")
    @PreAuthorize("isAuthenticated()")
    fun getTagSearchResult(
        @AuthenticationPrincipal authUser: AuthUser,
        pageParam: PageParam,
        @Valid request: TagSearchRequest
    ): ResponseDataEntity<Section<ProductItemCompositor>> =
        ResponseDataEntity(
            getSearchUIUseCase.getTagSearchResult(
                User.UserId(
                    authUser.username
                ), request.tagName, pageParam
            ), "/search/tag"
        )

    @GetMapping("/styleTag")
    @PreAuthorize("isAuthenticated()")
    fun getStyleTagSearchResult(
        @AuthenticationPrincipal authUser: AuthUser,
        pageParam: PageParam,
        @Valid request: StyleTagSearchRequest
    ): ResponseDataEntity<Section<ProductItemCompositor>> =
        ResponseDataEntity(
            getSearchUIUseCase.getStyleTagSearchResult(
                User.UserId(authUser.username),
                request.getStyleTagType(),
                pageParam
            ),
            "/search/styleTag"
        )
}


data class TagSearchRequest(
    @field:Pattern(regexp = "^#.*", message = "Tag must start with #")
    val tagName: String,
)

data class StyleTagSearchRequest(
    @field:ValidEnum(
        enumClass = StyleTagType::class,
        message = "Invalid tagName value"
    )
    val tagName: String?
) {
    fun getStyleTagType(): StyleTagType =
        StyleTagType.valueOf(tagName ?: throw CustomException(ErrorCode.VALIDATION_ERROR))
}
