package org.psy.demo.infra.sticker.jpaEntity

import jakarta.persistence.*
import org.psy.demo.core.vo.CommonStatus
import org.psy.demo.core.sticker.domain.Sticker
import org.psy.demo.core.sticker.domain.StickerManagement
import java.util.*


@Entity
@Table(name = "stickers")
class StickerJpaEntity(

    //private로 두면 companion object에서


    //val
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private val id: Long? = 0L,

    private val name: String? = null, //스티커 이름
    private val tagName: String? = null, //태그 네임  현재는 1:1
    private val imgUrl: String? = null, //이미지 url

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private val status: CommonStatus = CommonStatus.ACTIVE,
    private val createdAt: Date = Date(),
    private val updatedAt: Date = Date(),
    private val writerName: String? = null,
    private val isShow: Boolean = false, //노출여부
    private val orderNum: Int = 0 //순서

) {

    fun mapToDomain(): Sticker {
        return Companion.mapToDomain(this)
    }

    fun mapToDomainManagement(): StickerManagement {
        return Companion.mapToDomainManagement(this)
    }

    companion object {
        //이게 이상적...? private니까..
        fun mapToDomain(sticker: StickerJpaEntity) = Sticker(
            sticker.id.toString(),
            sticker.name,
            sticker.tagName,
            sticker.imgUrl.orEmpty(),
            sticker.writerName.orEmpty(),
        )
        fun mapToDomainManagement(sticker: StickerJpaEntity) = StickerManagement(
            sticker.id,
            sticker.name.orEmpty(),
            sticker.tagName.orEmpty(),
            sticker.imgUrl.orEmpty(),
            sticker.status,
            sticker.writerName.orEmpty(),
            sticker.isShow,
            sticker.createdAt
        )
    }
}
