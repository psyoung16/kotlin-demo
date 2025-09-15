package org.psy.demo.infra.sticker.jpaEntity

import org.psy.demo.sticker.domain.entity.UserSticker
import jakarta.persistence.*
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "user_stickers")
class UserStickerJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "title")
    var title: String,

    @Column(name = "description")
    var description: String, // 내용

    @Column(name = "stickerId")
    var stickerId: Long, // 스티커 id

    // @Column(name = "status")
    // var status: String,

    @Column(name = "createdAt")
    var createdAt: Date? = null,

    @Column(name = "updatedAt")
    var updatedAt: Date?,

    @Column(name = "createdBy")
    var createdBy: Long, // userId

    @Column(name = "date")
    var date: LocalDate,

    @Column(name = "underLineColor")
    var underLineColor: String? = "#F8E1E7", //underLineColor

    @Transient
    private val imgUrl: String? = null, //이미지 url DBx
    @Transient
    private val tagName: String? = null //tag url DBx
) {

    constructor(
        id: Long, stickerId: Long, title: String, description: String, createdBy: Long,
        createdAt: Date?, updatedAt: Date?, date: LocalDate, imgUrl: String, tagName: String, underLineColor: String
    ) : this(
        id,
        title,
        description,
        stickerId,
        createdAt,
        updatedAt,
        createdBy,
        date = date,
        imgUrl = imgUrl,
        tagName = tagName,
        underLineColor = underLineColor
    )
    /*constructor(title: String, description: String, stickerId: Long, createdBy: Long, date: LocalDate, underLineColor: String) :
        this(null, title, description, stickerId, null, null, createdBy, date, underLineColor)*/

    fun mapToDomain(): UserSticker {
        return Companion.mapToDomain(this)
    }

    companion object {
        fun mapToDomain(userStickerJpaEntity: UserStickerJpaEntity) =
            with(userStickerJpaEntity) {
                UserSticker(
                    id = id.toString(),
                    title = title,
                    description = description,
                    stickerId = stickerId.toString(),
                    userId = createdBy,
                    date = date.toString(),
                    imgUrl = imgUrl.orEmpty(),
                    tagName = tagName.orEmpty(),
                    underLineColor = underLineColor.orEmpty()
                )
            }


    }
}
