package org.psy.demo.core.sticker.service

import org.psy.demo.app.sticker.usecase.GetPrizesUseCase
import org.psy.demo.core.sticker.domain.Prize
import org.psy.demo.core.user.domain.User
import org.psy.demo.infra.sticker.repository.PrizesRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
private class PrizeService (
    val loadPrizePort : PrizesRepository,
) : GetPrizesUseCase {
    override fun getPrizes(monthYear: String, userId: User.UserId?): List<Prize> {

        val prizes = loadPrizePort.loadPrizes(monthYear, userId)
        val userPrize = loadPrizePort.loadUserPrize(monthYear, userId)

        return prizes.map { p ->
            Prize(
                id = p.id,
                name = p.name,
                imgUrl = p.imgUrl,
                monthYear = p.monthYear,
                isSelect = userPrize?.prizeId?.toString() == p.id
            )
        }

    }

}
