package org.psy.demo.app.main.response

import com.fasterxml.jackson.annotation.JsonProperty
import org.psy.demo.common.formatLegacyDate
import org.psy.demo.core.goods.domain.Goods
import org.psy.demo.core.goods.domain.GoodsManaging


data class MainGoodsManagingResponse(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("startAt")
    val startAt: String,
    @JsonProperty("endAt")
    val endAt: String,
    @JsonProperty("orderNum")
    val orderNum: Int,
    @JsonProperty("writerGoods")
    val writerGoods: List<Goods>
) {
    companion object {
        fun of(goodsManaging: GoodsManaging, goods: List<Goods> = emptyList()): MainGoodsManagingResponse {
            return with(goodsManaging) {
                MainGoodsManagingResponse(
                    id = id,
                    title = title,
                    startAt = startAt.formatLegacyDate(),
                    endAt = endAt.formatLegacyDate(),
                    orderNum = orderNum,
                    writerGoods = goods
                )
            }
        }
    }

}
