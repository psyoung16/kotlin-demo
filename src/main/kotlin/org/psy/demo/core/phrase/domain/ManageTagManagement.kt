package org.psy.demo.core.phrase.domain

import org.psy.demo.core.vo.TagStatus
import org.psy.demo.core.vo.TagType
import java.util.*

data class ManageTagManagement(
    val id: Long?,
    val tagName: String,
    val createdAt: Date,
    val updatedAt: Date,
    val type: TagType,
    val numOrder: Int,
    val isShow: Boolean,
    val createdBy: Long?,
    val status: TagStatus
) {
    fun update(
        id: Long?,
        tagName: String,
        type: TagType,
        createdBy: Long?,
        status: TagStatus
    ): ManageTagManagement = this.copy(
        id = id,
        tagName = tagName,
        updatedAt = Date(),
        type = type,
        createdBy = createdBy,
        status = status
    )




}
