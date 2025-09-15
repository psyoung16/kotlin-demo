package org.psy.demo.core.goods.service

import org.psy.demo.common.PageParam
import org.psy.demo.app.main.usecase.GetGoodsUseCase
import org.psy.demo.core.goods.domain.Goods
import org.psy.demo.core.user.domain.User
import org.psy.demo.infra.repository.GoodsRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
private class GetGoodsService (
    private val loadGoodsPort: GoodsRepository
) : GetGoodsUseCase {
    override fun getMyWishGoods(
        userId: User.UserId,
        pageParam: PageParam
    ): List<Goods> {
        return loadGoodsPort.loadMyWishGoods(userId, pageParam)
    }

    @Cacheable(value = ["CodeListByWriterId"], key = "#root.method.name+#writerId")
    override fun getCodeListByWriterId(writerId: Long): List<Goods> {
        return loadGoodsPort.loadCodeListByWriterId(writerId)
    }

    @Cacheable(value = ["CodeListByWriterId"], key = "#root.method.name+#gmId")
    override fun getCodeListByGmId(gmId: Long): List<Goods> {
        return loadGoodsPort.loadCodeListByGmId(gmId)
    }

    override fun getRecentTop2GoodsByWriterIds(writerIds: List<Long>): List<Goods> {
        val goodsList = loadGoodsPort.loadRecentGoodsTop2GoodsByWriterIds(writerIds)
        return loadGoodsPort.loadGoodsByGoodsIds(goodsList)
    }


}
