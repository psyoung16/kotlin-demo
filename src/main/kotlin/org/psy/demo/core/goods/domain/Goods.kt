package org.psy.demo.core.goods.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class Goods(
        @JsonProperty("id")
        val id: Long,
        @JsonProperty("writerId")
        val writerId: Long,
        @JsonProperty("goodsName")
        val goodsName: String,
        @JsonProperty("brandName")
        val brandName: String,
        @JsonProperty("url")
        val url: String,
        @JsonProperty("salePrice")
        val salePrice: Int,
        @JsonProperty("salePercent")
        val salePercent: String,
        @JsonProperty("cmdtCode")
        val cmdtCode: String,
        @JsonProperty("defaultPrice")
        val defaultPrice: Int,
        @JsonProperty("createdAt")
        val createdAt: String,
        @JsonProperty("updatedAt")
        val updatedAt: String,
        @JsonProperty("thumbnailImage")
        val thumbnailImage: String

)
