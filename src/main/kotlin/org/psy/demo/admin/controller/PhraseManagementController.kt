package org.psy.demo.admin.controller

import io.jsonwebtoken.Claims
import org.psy.demo.common.PageParam
import org.psy.demo.common.ResponseDataEntity
import org.psy.demo.config.JwtService
import org.psy.demo.admin.usecase.GetManagementPhraseUseCase
import org.psy.demo.admin.usecase.SettingManagementPhraseUseCase
import org.psy.demo.admin.response.PhraseManagementResponse
import org.psy.demo.core.phrase.domain.PhraseManagement
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/ADMIN/v3.0/phraseSetting")
class PhraseManagementController (
    val getManagementPhraseUseCase: GetManagementPhraseUseCase,
    val settingManagementPhraseUseCase: SettingManagementPhraseUseCase,
    val jwtService: JwtService
){

    @GetMapping("/list")
    @CrossOrigin(origins = ["*"])
    fun getPhraseList(
        @RequestHeader("Authorization") userAgent: String,
        pageParam: PageParam
    ): ResponseDataEntity<PhraseManagementResponse> {
        val claims: Claims = jwtService.decodeJwt(userAgent)
        val userId = claims["id"].toString()
        return ResponseDataEntity(

            getManagementPhraseUseCase.getManagePhrases(pageParam)
        )
    }

    @PostMapping("/settingPhrase")
    @CrossOrigin(origins = ["*"])
    fun settingPhrase(
        @RequestHeader("Authorization") userAgent: String,
        @RequestBody praseManagement: PhraseManagement
    ): ResponseDataEntity<Map<String, String>> {
        val claims: Claims = jwtService.decodeJwt(userAgent)
        val userId = claims["id"].toString()

        settingManagementPhraseUseCase.createPhraseMangement(praseManagement)

        return ResponseDataEntity(
            mapOf("message" to "Update successfully")
        )
    }


}