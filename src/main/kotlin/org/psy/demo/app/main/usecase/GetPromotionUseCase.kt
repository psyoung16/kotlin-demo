package org.psy.demo.app.main.usecase

import org.psy.demo.core.promotion.Promotion
import org.psy.demo.core.promotion.PromotionRequest


interface GetPromotionUseCase {

    fun get(promotionRequest: PromotionRequest): List<Promotion>


}
