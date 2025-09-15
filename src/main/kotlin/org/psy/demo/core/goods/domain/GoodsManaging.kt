package org.psy.demo.core.goods.domain

import java.util.*

data class GoodsManaging(
        val id: Long,
        val title: String,
        val startAt: Date,
        val endAt: Date,
        val orderNum: Int)
