package org.psy.demo.infra.repository

import org.psy.demo.common.PageParam
import org.psy.demo.core.goods.domain.Goods
import org.psy.demo.infra.jpaEntity.WriterGoodsJpaEntity
import org.psy.demo.core.user.domain.User
import org.psy.demo.infra.jpaRepository.GoodsJpaRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

@Component
class GoodsRepository(
        private val goodsJpaRepository: GoodsJpaRepository
) {
     fun loadCodeListByWriterId(writerId: Long): List<Goods> {
        val goodsList: List<WriterGoodsJpaEntity> = goodsJpaRepository.findGoodsListByWriterId(writerId, PageRequest.of(0, 2))
        return goodsList.map(WriterGoodsJpaEntity::mapToDomain)
    }

     fun loadCodeListByGmId(gmId: Long): List<Goods> {
        val goodsList: List<WriterGoodsJpaEntity> = goodsJpaRepository.findGoodsListByGmId(gmId.toInt(), PageParam.of(1,9).toPageRequest()) //TODO PageRequest to PageParam change
        return goodsList.map(WriterGoodsJpaEntity::mapToDomain)
    }

     fun loadRandomGoodsByStickerCategory(): List<Goods> {

        //어떻게 랜덤으로 돌릴 것이냐...?
        // 1. 가능한 goodsId가져온 다음 해당 리스트에서 랜덤으로 뽑는 거 하기
        val ids: List<Long> = goodsJpaRepository.findAvaliableGoodsIdAndCategory(PageRequest.of(0, 100)) //랜덤이 부담이 될 수 있으므로 최신 등록된 100개 ?
        val goods: List<WriterGoodsJpaEntity> = goodsJpaRepository.findGoodsListByGoodsIds(ids.shuffled().take(12))

        return goods.map(WriterGoodsJpaEntity::mapToDomain)
    }

     fun loadRandomGoodsByStickerCategoryByWriterName(writerName: String?): List<Goods> {
        val ids: List<Long> = goodsJpaRepository.findAvaliableGoodsIdAndCategoryAndWriterName(writerName, PageRequest.of(0, 100))
        val goods: MutableList<WriterGoodsJpaEntity> = goodsJpaRepository.findGoodsListByGoodsIds(ids.shuffled().take(12)).toMutableList()

        if (goods.size < 12){
            val ids2: List<Long> = goodsJpaRepository.findAvaliableGoodsIdAndCategory(PageRequest.of(0, 100)) //랜덤이 부담이 될 수 있으므로 최신 등록된 100개 ?
            val goods2: List<WriterGoodsJpaEntity> = goodsJpaRepository.findGoodsListByGoodsIds(ids2.shuffled().take(12-goods.size))

            goods += goods2
        }

        return goods.map(WriterGoodsJpaEntity::mapToDomain)

    }

     fun loadMyWishGoods(
        userId: User.UserId,
        pageParam: PageParam
    ): List<Goods> {
        val goodsList: List<WriterGoodsJpaEntity> = goodsJpaRepository.findGoodsListByWish(userId.id.toLong(), pageParam.toPageRequest())
        return goodsList.map(WriterGoodsJpaEntity::mapToDomain)
    }

    //native라서 일단 분리
     fun loadRecentGoodsTop2GoodsByWriterIds(writerId: List<Long>): List<Long> {
        return goodsJpaRepository.findTop2REcentGoodsIdByWriterId(writerId)
    }

     fun loadGoodsByGoodsIds(goodsIds: List<Long>): List<Goods> {
        val goodsList: List<WriterGoodsJpaEntity> = goodsJpaRepository.findGoodsListByGoodsIds(goodsIds)
        return goodsList.map(WriterGoodsJpaEntity::mapToDomain)
    }


}
