package org.psy.demo.core.sticker.service

import org.psy.demo.app.sticker.usecase.StickingStickerUseCase
import org.psy.demo.common.exception.CustomException
import org.psy.demo.common.exception.ErrorCode
import org.psy.demo.core.sticker.domain.Sticker
import org.psy.demo.core.sticker.domain.UserSticker
import org.psy.demo.core.user.domain.User
import org.psy.demo.infra.sticker.repository.StickerRepository
import org.psy.demo.infra.sticker.repository.UserStickerRepository
import org.springframework.stereotype.Service

@Service
private class StickingStickerService(
    private val loadStickerPort: StickerRepository,
    private val userStickerRepository: UserStickerRepository,
) : StickingStickerUseCase {

    override fun stickingSticker(param: StickingStickerUseCase.StickingStickerCommand, userId: User.UserId) {

        //기존에 있던 스티커 조회해서 넣기
        val sticker: Sticker = loadStickerPort.loadStickerByStickerId(param.stickerId.toLong())
            ?: throw CustomException(ErrorCode.NO_STICKER_AVAILABLE)

        //스티커붙이기
        //현재 날짜의 스티커가 있는지 체크, 1일 1스티커
        if (userStickerRepository.loadUserStickerByDate(param.date, userId) != null) {
            throw CustomException(ErrorCode.A_STICKER_FOR_THE_SPECIFIED_DATE_ALREADY_EXISTS)
        }

        val userSticker: UserSticker = UserSticker(
            null,
            param.title,
            param.description,
            param.stickerId,
            userId.id.toLong(),
            param.date,
            sticker.tagName.orEmpty(),
            sticker.imgUrl.orEmpty(),
            param.underLineColor
        )

        userStickerRepository.createUserSticker(userSticker)

    }

    override fun stickingStickerUpdate(
        param: StickingStickerUseCase.StickingStickerUpdateCommand,
        userId: User.UserId
    ) {
        //기존 UserSticker의 데이터조회해서 imgUrl, tagName 가져와서 저장
        val preUserSticker: UserSticker = userStickerRepository.loadUserStickerById(param.id, userId)
            ?: throw CustomException(ErrorCode.NO_STICKER_TO_EDIT_FOR_DATE)

        //stickerId가 변경될 경우 사용가능한지 확인 필요
        val sticker: Sticker = loadStickerPort.loadStickerByStickerId(param.stickerId.toLong())
            ?: throw CustomException(ErrorCode.NO_STICKER_AVAILABLE)

        //스티커붙이기
        val userSticker: UserSticker = UserSticker(
            param.id,
            param.title,
            param.description,
            param.stickerId,
            userId.id.toLong(),
            preUserSticker.date,
            sticker.imgUrl.orEmpty(),
            sticker.tagName.orEmpty(),
            param.underLineColor
        )

        userStickerRepository.updateUserSticker(userSticker)
    }


}
