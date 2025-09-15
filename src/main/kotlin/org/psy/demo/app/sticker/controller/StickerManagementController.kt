package org.psy.demo.app.sticker.controller

import org.psy.demo.app.sticker.response.StickerManagementResponse
import org.psy.demo.app.sticker.usecase.GetStickerManagementUseCase
import org.psy.demo.app.sticker.usecase.UpdateStickerManagementUseCase
import org.psy.demo.common.PageParam
import org.psy.demo.common.ResponseDataEntity
import org.psy.demo.common.exception.CustomException
import org.psy.demo.common.exception.ErrorCode
import org.psy.demo.config.JwtService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*


@Controller
@RestController
@RequestMapping("/api/ADMIN/v3.0/sticker")
class StickerManagementController (
    val jwtService: JwtService,
    val getStickerManagementUseCase: GetStickerManagementUseCase,
    val updateStickerManagementUseCase: UpdateStickerManagementUseCase,
)  {

    //해당하는 태그의 스티커 리스트
    @GetMapping("/list/{tagName}")
    @CrossOrigin(origins = ["*"])
    fun getSticker(
        @PathVariable("tagName") tagName: String?,  //현재 있는 태그 인지까지는 검증 x, null 여부만체크
        @RequestHeader("Authorization") userAgent: String?,
        @ModelAttribute param: PageParam
    ): ResponseDataEntity<StickerManagementResponse> {
        val claims = jwtService.decodeJwt(userAgent!!)
        val userId = claims["id"].toString()

        if (Objects.isNull(userId)) {
            throw CustomException(ErrorCode.VALIDATION_ERROR)
        }
        //권한 체크 필요 -- 추후 --- 에?
        return ResponseDataEntity(
            
            getStickerManagementUseCase.getStickers(tagName, param)
        )
    }

    @PostMapping("/create")
    @CrossOrigin(origins = ["*"])
    fun createSticker(
        @RequestHeader("Authorization") userAgent: String?,
        @RequestBody command: UpdateStickerManagementUseCase.CreateStickerManagementCommand
    ): ResponseDataEntity<Map<String, String>> {
//        //val claims = jwtService.decodeJwt(userAgent!!)
//        //val userId = claims["id"].toString()

//        if (Objects.isNull(userId)) {
//            throw CustomException("0002")
//        }
        updateStickerManagementUseCase.createStickerManagement(command)

        //권한 체크 필요 -- 추후 --- 에?
        return ResponseDataEntity(
            
            mapOf("message" to "Update successfully")
        )
    }

    @PutMapping("/update")
    @CrossOrigin(origins = ["*"])
    fun updateSticker(
        @RequestHeader("Authorization") userAgent: String?,
        @RequestBody command: UpdateStickerManagementUseCase.UpdateStickerManagementCommand
    ): ResponseDataEntity<Map<String, String>> {
//        //val claims = jwtService.decodeJwt(userAgent!!)
//        //val userId = claims["id"].toString()

//        if (Objects.isNull(userId)) {
//            throw CustomException("0002")
//        }
        updateStickerManagementUseCase.updateStickerManagement(command)

        //권한 체크 필요 -- 추후 --- 에?
        return ResponseDataEntity(
            
            mapOf("message" to "Update successfully")
        )
    }

    @PutMapping("/toggleIsShow")
    @CrossOrigin(origins = ["*"])
    fun updateSticker(
        @RequestHeader("Authorization") userAgent: String?,
        @RequestBody toggleStickerManagementRequest: UpdateStickerManagementUseCase.ToggleStickerManagementRequest
    ): ResponseDataEntity<Map<String, String>> {
//        //val claims = jwtService.decodeJwt(userAgent!!)
//        //val userId = claims["id"].toString()

        /*if (Objects.isNull(userId)) {
            throw CustomException("0002")
        }*/
        updateStickerManagementUseCase.toggleIsShowStickerManagement(
            toggleStickerManagementRequest.id,
            toggleStickerManagementRequest.isShow
        )

        //권한 체크 필요 -- 추후 --- 에?
        return ResponseDataEntity(
            
            mapOf("message" to "Update successfully")
        )
    }



}
