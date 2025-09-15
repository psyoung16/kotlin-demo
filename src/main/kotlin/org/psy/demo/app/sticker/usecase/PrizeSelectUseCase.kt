package org.psy.demo.app.sticker.usecase

import org.psy.demo.core.user.domain.User
import jakarta.validation.constraints.NotNull
import org.psy.demo.core.sticker.domain.UserPrize
import org.springframework.format.annotation.DateTimeFormat

interface PrizeSelectUseCase {

    fun prizeApply(prizeApply: PrizeApplyRequest, userId: User.UserId)
    fun prizeSelectUpdate(prizeApply: PrizeApplyUpdateRequest, userId: User.UserId)
    data class PrizeApplyRequest(
        @field:NotNull
        @field:DateTimeFormat(pattern = "yyyy-MM")
        val date: String,
        @field:NotNull
        val prizeId: String
    ) {}
    data class PrizeApplyUpdateRequest(
        @field:NotNull
        val id : Long,
        @field:NotNull
        @field:DateTimeFormat(pattern = "yyyy-MM")
        val date: String,
        @field:NotNull
        val prizeId: Long
    )


    fun prizeApplyCheck(monthYear: String, userId: User.UserId?): UserPrize?

    companion object {
    }

}
