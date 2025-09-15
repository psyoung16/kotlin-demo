package org.psy.demo.core.sticker.domain

import org.psy.demo.core.vo.CommonStatus

import java.util.*

data class StickerManagement(
    val id: Long?,
    val name: String,
    val tagName: String,
    val imgUrl: String,
    val status: CommonStatus,
    val writerName: String,
    val isShow: Boolean,
    val createdAt: Date?
){
}
