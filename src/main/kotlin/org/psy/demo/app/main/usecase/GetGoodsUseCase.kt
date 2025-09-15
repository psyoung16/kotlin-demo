package org.psy.demo.app.main.usecase

import org.psy.demo.common.PageParam
import org.psy.demo.core.goods.domain.Goods
import org.psy.demo.core.user.domain.User

interface GetGoodsUseCase {
    fun getMyWishGoods(userId: User.UserId, pageParam: PageParam): List<Goods>
    fun getCodeListByWriterId(writerId: Long): List<Goods>
    fun getCodeListByGmId(gmId: Long): List<Goods>



    fun getRecentTop2GoodsByWriterIds(writerIds: List<Long>): List<Goods> //사용x
}

