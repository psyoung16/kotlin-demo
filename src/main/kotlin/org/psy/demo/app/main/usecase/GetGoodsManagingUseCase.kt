package org.psy.demo.app.main.usecase

import org.psy.demo.core.goods.domain.GoodsManaging


interface GetGoodsManagingUseCase {
    fun getMainGoodsManaiging(): List<GoodsManaging>
}

