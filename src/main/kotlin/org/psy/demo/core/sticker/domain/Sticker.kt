package org.psy.demo.core.sticker.domain

import org.psy.demo.core.vo.CommonStatus
import org.psy.demo.infra.sticker.jpaEntity.StickerJpaEntity
import java.util.*

data class Sticker(
    val id: String,
    var name    : String? = null,
    var tagName: String? = null,
    val imgUrl: String?,
    val writerName: String,
    var underLineColor: String = "#F8E1E7",
) {
    //    data class StickerId(val number: Long)

    init {
        // 필요한 경우 추가적인 초기화 로직
        name = name.orEmpty()
        tagName = tagName.orEmpty()

    }

    companion object {
        fun mapToEntity(sticker: Sticker) =
            with(sticker) {
                StickerJpaEntity(
                    id = id.toLong(),
                    name = name,
                    tagName = tagName,
                    imgUrl = imgUrl.orEmpty(),
                    status = CommonStatus.ACTIVE,
                    createdAt = Date(),
                    updatedAt = Date(),
                    writerName = writerName
                )
            }
    }

}


//db에는 null가능. but front에서는 불가능?




