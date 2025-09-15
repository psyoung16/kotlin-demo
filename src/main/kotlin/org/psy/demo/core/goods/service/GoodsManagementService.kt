package org.psy.demo.core.goods.service

import org.psy.demo.app.main.usecase.GetGoodsManagingUseCase
import org.psy.demo.core.goods.domain.GoodsManaging
import org.psy.demo.infra.repository.GoodsManagingRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
private class GetGoodsManagingService(
    private val loadGoodsManagingPort: GoodsManagingRepository
) : GetGoodsManagingUseCase
{

    override fun getMainGoodsManaiging(): List<GoodsManaging> {
        return loadGoodsManagingPort.loadGoodsManagingList()
    }
}
