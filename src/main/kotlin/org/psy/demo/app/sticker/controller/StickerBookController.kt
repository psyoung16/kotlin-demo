package org.psy.demo.app.sticker.controller

import org.psy.demo.common.*
import org.psy.demo.user.domain.AuthUser
import org.psy.demo.core.user.domain.User
import jakarta.validation.Valid
import org.hibernate.validator.constraints.Length
import org.psy.demo.app.sticker.response.CalendarDetailResponse
import org.psy.demo.app.sticker.response.StickerResponse
import org.psy.demo.app.sticker.usecase.GetManageTagByStickerResponse
import org.psy.demo.app.sticker.usecase.GetStickerUseCase
import org.psy.demo.app.sticker.usecase.StickingStickerUseCase
import org.psy.demo.core.sticker.domain.Sticker
import org.psy.demo.core.sticker.domain.StickerCalendar
import org.psy.demo.core.vo.TagType
import org.springframework.data.domain.Page
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RestController
@RequestMapping("/api/CLIENT/v3.0/sticker")
class StickerBookController(
    val getStickerUseCase: GetStickerUseCase,
    val stickingStickerUseCase: StickingStickerUseCase,
) {

    @GetMapping("/list2")
    @PreAuthorize("isAuthenticated()")
    fun getStickerBook2(
        pageParam: PageParam,
        tagName: String
    ): ResponseDataEntity<StickerResponse> {
        val stickers : Page<Sticker> = getStickerUseCase.getStickers(tagName, pageParam)
        return ResponseDataEntity(
            StickerResponse(
                stickers.content,
                MetaData.of(pageParam.page, pageParam.size, stickers.totalElements)
            )
        )
    }

    @GetMapping("/manageTags")
    @PreAuthorize("isAuthenticated()")
    fun getStickerBookTag(
        pageParam: PageParam
    ): ResponseDataEntity<GetManageTagByStickerResponse> {
        return ResponseDataEntity(
            getStickerUseCase.getManageTagBySticker(TagType.STICKER)
        )
    }

    @PostMapping("/sticking")
    @PreAuthorize("isAuthenticated()")
    fun stickingSticker(
        @AuthenticationPrincipal authUser: AuthUser,
        @RequestBody param: StickingStickerUseCase.StickingStickerCommand
    ): ResponseDataEntity<Map<String, String>> {
        stickingStickerUseCase.stickingSticker(param, User.UserId(authUser.username)) //객체 return할지는 생각해봐야할듯>
        return ResponseDataEntity(
            mapOf("message" to "Update successfully")
        )
    }

    @PutMapping("/sticking/{id}")
    @PreAuthorize("isAuthenticated()")
    fun stickingStickerUpdate(
        @AuthenticationPrincipal authUser: AuthUser,
        @PathVariable("id") id: String?,
        @Valid @RequestBody param: StickingStickerUseCase.StickingStickerCommand
    ): ResponseDataEntity<Map<String, String>> {
        stickingStickerUseCase.stickingStickerUpdate(
            StickingStickerUseCase.StickingStickerUpdateCommand(
                id.toString(),
                param.stickerId,
                param.title,
                param.description,
                param.underLineColor
        ), User.UserId(authUser.username)) //객체 return할지는 생각해봐야할듯>
        return ResponseDataEntity(
            mapOf("message" to "Update successfully")
        )
    }


//    https://velog.io/@hana0627/Java-8-datetime-type-java.time.LocalDateTime-not-supported-by-default-add-Module-com.fasterxml.jackson.datatypejackson-datatype-jsr310

    /**
     * list에서 바로 조회로 사용해서 04.11기준 사용x 일단 합의?
     */
    @GetMapping("/sticking/{date}")
    @PreAuthorize("isAuthenticated()")
    fun getStickingSticker(
        @AuthenticationPrincipal authUser: AuthUser,
        @Length(max = 10, min = 10, message = "yyyy-MM-dd 형식") @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("date") date: String, //valid 필요
    ): ResponseDataEntity<StickerCalendar> {

        return ResponseDataEntity(
            getStickerUseCase.getCaledarUserStickersByDate(
                date, User.UserId(authUser.username)
            )
        )
    }

    @GetMapping("/calendar")
    @PreAuthorize("isAuthenticated()")
    fun getMyCalendar(
        @AuthenticationPrincipal authUser: AuthUser,
        @DateTimeFormat(pattern = "yyyy-MM") @RequestParam("date") monthYear: String,
    ): ResponseDataEntity<CalendarDetailResponse> {
        return ResponseDataEntity(
            CalendarDetailResponse(
                getStickerUseCase.getCalendarUserStickers(
                    month = MonthYear(monthYear).month, //이걸 param에서 검증? 할 수 잇ㅇ므 좋을 것 같은데... 으음
                    year = MonthYear(monthYear).year,
                    userId = User.UserId(authUser.username)
                ),
                getStickerUseCase.getCalendarGoods(MonthYear(monthYear).year,MonthYear(monthYear).month, User.UserId(authUser.username)),
                System.currentTimeMillis() < 1722538800000L //한국시간으로 2024-08-01 00:00:00 > 일 경우 노출x
            )
        )
    }

}

