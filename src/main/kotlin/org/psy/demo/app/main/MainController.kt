package org.psy.demo.app.main

import org.psy.demo.app.main.usecase.GetMainUseCase
import org.psy.demo.app.main.usecase.GetNotiDataUseCase
import org.psy.demo.app.main.usecase.GetPromotionUseCase
import org.psy.demo.common.ResponseDataEntity
import org.psy.demo.core.promotion.PromotionPosition
import org.psy.demo.core.promotion.PromotionRequest
import org.psy.demo.core.vo.TagType
import org.psy.demo.user.domain.AuthUser
import org.psy.demo.core.user.domain.User
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Controller
@RestController
@RequestMapping("/api/CLIENT/v3.0/main")
class MainController(
    val getMainUseCase: GetMainUseCase,
    val getNotiDataUseCase: GetNotiDataUseCase,
    val getPromotionUseCase: GetPromotionUseCase
) {

    /**
     * 홈 조회
     * @param userAgent
     * @return
     */
    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    fun getMainAll(
        @AuthenticationPrincipal authUser: AuthUser,
    ): ResponseDataEntity<GetMainUseCase.GetMainResponse> {
        val main: GetMainUseCase.GetMainResponse =  GetMainUseCase.GetMainResponse(
            getMainUseCase.getMainPopupList(User.UserId(authUser.username)),  //popup -- ok
            getMainUseCase.getMainBannerList(User.UserId(authUser.username)),  //banner -- o
            getPromotionUseCase.get(
                PromotionRequest(
                    User.UserId(authUser.username),
                    PromotionPosition.MAIN_BANNER
                )
            ),  //banner2 -- o
            getMainUseCase.getMainCategoryList(),  //categoryList -- o
            getMainUseCase.getMainPhrase(),  //phrase -- o
            getMainUseCase.getBestPostList(User.UserId(authUser.username)),  //best
            getMainUseCase.getMainProWriterWithGoodsList(),  //proWriter
            getMainUseCase.getMainGoodsManagingWriterGoods(),  //goodsManagingWriterGoods
            getMainUseCase.getManageTagList(TagType.RECOMMEND),  //recommendTag
            getMainUseCase.getMainChallengeList(),  //challenge
            getNotiDataUseCase.getMainNotiData(User.UserId(authUser.username)) //notiCountData -- o
        )
        return ResponseDataEntity(
            main, "/main/home"
        )
    }

}