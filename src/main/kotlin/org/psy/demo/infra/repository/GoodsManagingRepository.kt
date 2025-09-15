package org.psy.demo.infra.repository

import org.psy.demo.core.goods.domain.GoodsManaging
import org.psy.demo.infra.jpaEntity.GoodsManagingJpaEntity
import org.psy.demo.infra.jpaRepository.GoodsManagingJpaRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import java.util.*

@Component
class GoodsManagingRepository (
        private val goodsManagingJpaRepository: GoodsManagingJpaRepository
) {
    fun loadGoodsManagingList(): List<GoodsManaging> {
        val gmList: List<GoodsManagingJpaEntity> = goodsManagingJpaRepository.findByType(Date(),Date(), PageRequest.of(0, 3), "MAIN_BOTTOM")
//        return gmList.mapTo(mutableListOf(), GoodsManagingJpaEntity::mapToDomain)
        return gmList.map(GoodsManagingJpaEntity::mapToDomain)
    }
}
