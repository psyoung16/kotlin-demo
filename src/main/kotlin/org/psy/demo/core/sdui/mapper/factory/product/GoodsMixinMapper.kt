package org.psy.demo.core.sdui.mapper.factory.product

import org.psy.demo.common.addPercent
import org.psy.demo.common.formatPrice
import org.psy.demo.app.sdui.translator.BaseMixin
import org.psy.demo.app.sdui.translator.SupportingMixin
import org.psy.demo.core.goods.domain.Goods
import org.psy.demo.core.sdui.mapper.factory.DomainBaseMixinMapper
import org.psy.demo.core.sdui.mapper.factory.DomainSupportingMixinMapper

class GoodsMixinMapper {

    data class PriceItemSupporting(
        val percent: String,
        val normal: String,
        val strike: String,
        val isWish: Boolean,
        val url: String //link 이동
    )

    data class GoodsItemSupporting(
        val description: String,
        val category: String,
        val time: String,
        val profileName: String,
        val profileImageUrl: String?,
        val isLike: Boolean?,
        val isScrap: Boolean?,
        val price: PriceItemSupporting
    )

    companion object {
        val baseMixin: DomainBaseMixinMapper<Goods> = { product ->
            with(product){
                BaseMixin(
                    id = id.toString(),
                    title = goodsName,
                    imageUrl = thumbnailImage,
                )
            }
        }
        val supportingMixin: DomainSupportingMixinMapper<Goods, GoodsItemSupporting> = { product ->
            with(product){
                SupportingMixin(
                    GoodsItemSupporting(
                        description = brandName,
                        category = cmdtCode,
                        time = "",
                        profileName = "",
                        profileImageUrl = null,
                        isLike = null,
                        isScrap = null,
                        price = PriceItemSupporting(
                            percent = salePercent.takeUnless { it == "0" }?.addPercent() ?: "",
                            normal = if (defaultPrice == salePrice) defaultPrice.formatPrice() else salePrice.formatPrice(),
                            strike = if (defaultPrice == salePrice) "" else defaultPrice.formatPrice(),
                            isWish = true,
                            url = url
                        )
                    )
                )
            }
        }
    }

}
