package org.psy.demo.app.sticker.usecase

import org.psy.demo.core.user.domain.User
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length
import org.psy.demo.common.validation.CustomKoreanLength
import org.springframework.format.annotation.DateTimeFormat

interface StickingStickerUseCase {

    fun stickingSticker(param: StickingStickerCommand, userId: User.UserId)
    data class StickingStickerCommand(

        @field:NotNull(message = "스티커 아이디를 입력해주세요")
        val stickerId: String,
        @field:NotNull
        @CustomKoreanLength(max = 8, message = "제목은 8자리 이하로 입력해주세요")
        var title: String,
        @CustomKoreanLength(max = 200, message = "내용은 200자 이하로 입력해주세요")
        var description: String?,
        /*@field:NotNull
        val tagName: String,*/
        //yyyy-mm-dd (10자리) 패턴 검사가 필요할까?
        @field:NotNull
        @field:DateTimeFormat(pattern = "yyyy-MM-dd")
        val date : String,

        val underLineColor: String = "#F8E1E7",
    ){
        //추후
        /*init {
            title = Normalizer.normalize(title, Normalizer.Form.NFC)
            description?.let {
                description = Normalizer.normalize(it, Normalizer.Form.NFC)
            }
        }*/
    }

    fun stickingStickerUpdate(param: StickingStickerUpdateCommand, userId: User.UserId)
    data class StickingStickerUpdateCommand(

        @field:NotNull
        val id: String,

        @field:NotNull
        val stickerId: String,

        @field:Length(max = 8, message = "제목은 8자리 이하로 입력해주세요")
        var title: String,
        @field:Length(max = 200, message = "내용은 200자 이하로 입력해주세요")
        var description: String?,
        /*@field:NotNull
        val tagName: String,*/

        //yyyy-mm-dd (10자리) 패턴 검사가 필요할까?
        /*@field:NotNull
        @field:Length(min = 10, max = 10, message = "yyyy-mm-dd")
        val date : String*/

        var underLineColor: String = "#F8E1E7",
    ){
        //추후
        /*init {
            title = Normalizer.normalize(title, Normalizer.Form.NFC)
            description?.let {
                description = Normalizer.normalize(it, Normalizer.Form.NFC)
            }
        }*/
    }

    companion object {
    }

}
