package org.psy.demo.app.sticker.controller

//import io.jsonwebtoken.Claims
import org.psy.demo.common.ResponseDataEntity
import org.psy.demo.app.sticker.response.PrizesResponse
import org.psy.demo.user.domain.AuthUser
import org.psy.demo.core.user.domain.User
import jakarta.validation.Valid
import org.psy.demo.app.sticker.usecase.GetPrizesUseCase
import org.psy.demo.app.sticker.usecase.PrizeSelectUseCase
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*


@Controller
@RestController
@RequestMapping("/api/CLIENT/v3.0/prizes")
class PrizeController(
    val getPrizesUseCase: GetPrizesUseCase,
    val prizeSelectUseCase: PrizeSelectUseCase,
) {

    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    fun getPrizes(
        @AuthenticationPrincipal authUser: AuthUser,
        @DateTimeFormat(pattern = "yyyy-MM") @RequestParam("date") monthYear: String,
    ): ResponseDataEntity<PrizesResponse> =
        //재로그인시 toekn체크가 안되는구나... 흠
        ResponseDataEntity(
            PrizesResponse(
                getPrizesUseCase.getPrizes(monthYear, User.UserId(authUser.username)),
                prizeSelectUseCase.prizeApplyCheck(monthYear, User.UserId(authUser.username)) != null

            )
        )


    /** 수정 **/
    @PutMapping("/select")
    @PreAuthorize("isAuthenticated()")
    fun prizeSelectUpdate(
        @AuthenticationPrincipal authUser: AuthUser,
        @Valid @RequestBody param: PrizeSelectUseCase.PrizeApplyUpdateRequest
    ): ResponseDataEntity<Map<String, String>> {
        prizeSelectUseCase.prizeSelectUpdate(param, User.UserId(authUser.username)) //객체 return할지는 생각해봐야할듯>
        return ResponseDataEntity(
            mapOf("message" to "Update successfully")
        )
    }

    /** 최종선택 **/
    @PostMapping("/apply")
    @PreAuthorize("isAuthenticated()")
    fun prizeApply(
        @AuthenticationPrincipal authUser: AuthUser,
        @Valid @RequestBody param: PrizeSelectUseCase.PrizeApplyRequest
    ): ResponseDataEntity<Map<String, String>> {
        prizeSelectUseCase.prizeApply(param, User.UserId(authUser.username)) //객체 return할지는 생각해봐야할듯>
        return ResponseDataEntity(
            mapOf("message" to "Update successfully")
        )
    }


}
