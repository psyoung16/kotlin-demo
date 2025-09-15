package org.psy.demo.core.sticker.service

import org.psy.demo.app.sticker.usecase.PrizeSelectUseCase
import org.psy.demo.common.exception.CustomException
import org.psy.demo.common.exception.ErrorCode
import org.psy.demo.core.sticker.domain.UserPrize
import org.psy.demo.core.user.domain.User
import org.psy.demo.infra.sticker.repository.PrizesRepository
import org.psy.demo.infra.sticker.repository.StickerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.YearMonth


@Service
private class SelectPrizeService (
    private val prizesRepository : PrizesRepository,
    private val stickerRepository: StickerRepository
) : PrizeSelectUseCase {

    //경품 최종 선택하기
    @Transactional
    override fun prizeApply(prizeApply: PrizeSelectUseCase.PrizeApplyRequest, userId: User.UserId) {

        //응모하려는 시간이 벗어나지 않았는지 체크?
        //현재 보내는 데잍가 2024-03인데, 현재시간이 4월 1일일 경우
        /*Date(prizeApply.date).let {
            if (it.before(Date())) {
                throw CustomException(ErrorCode.PAST_DATE)
            }
        }*/

        //이미 최종 선택했으면 더 이상 수정 불가능
        prizesRepository.loadSelectFinalUserPrize(prizeApply.date, userId)?.id?.let { throw CustomException(ErrorCode.ALREADY_APPLY_PRIZE) }

        //모든 날짜를 출석체크 했는지 확인 필요
        //개수로 체크하기
        val stickerCount: Int = stickerRepository.loadStickerCountByYearMonth(prizeApply.date, userId)

        //현재 달의 일수
        if (stickerCount < YearMonth.parse(prizeApply.date).lengthOfMonth()) {
            throw CustomException(ErrorCode.DAYS_MISSING_STICKERS)
        }

        //save
        prizesRepository.finalSelectUserPirze(prizeApply.prizeId.toLong())
    }

    @Transactional
    override fun prizeSelectUpdate(prizeApply: PrizeSelectUseCase.PrizeApplyUpdateRequest, userId: User.UserId) {
        //이미 최종 선택했으면 더 이상 수정 불가능
        val userPirze = prizesRepository.loadSelectFinalUserPrize(prizeApply.date, userId)?.id?.let { throw CustomException(ErrorCode.ALREADY_APPLY_PRIZE) } //1

        val userPrize = UserPrize(
            id = (prizesRepository.loadUserPrize(prizeApply.date, userId)?.id ?: 0L).toString(), //2
            userId.id.toLong(),
            prizeApply.prizeId.toString(),
            prizeApply.date
        )
        prizesRepository.selectUserPrize(userPrize)
    }

    @Transactional(readOnly = true)
    override fun prizeApplyCheck(monthYear: String, userId: User.UserId?): UserPrize? {
        return prizesRepository.loadUserPrizeAndIsFinalCheck(monthYear, userId)
    }



}
