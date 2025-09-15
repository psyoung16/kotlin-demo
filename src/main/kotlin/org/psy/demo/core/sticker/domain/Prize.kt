package org.psy.demo.core.sticker.domain

data class Prize(
    val id: String,
    val name: String,
    val imgUrl: String,
    val monthYear: String,
    val isSelect: Boolean // 경품 선택 여부
)
