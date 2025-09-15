package org.psy.demo.infra.jpaEntity

import jakarta.persistence.*
import org.psy.demo.core.content.domain.Challenge
import org.psy.demo.core.vo.ContentLinkType
import org.psy.demo.core.vo.EventStatus
import org.psy.demo.core.vo.EventType
import java.util.*


@Entity
@Table(name = "events")
class EventJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private var id: Long? = null,

    @Column(name = "title", columnDefinition = "MEDIUMTEXT")
    private var title: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, columnDefinition = "enum('EVENT', 'CHALLENGE')")
    private var type: EventType? = null,

    @Column(name = "createdBy", nullable = false)
    private var createdBy: Long? = null,

    @Column(name = "isShow")
    private var isShow: Boolean = true,

    @Column(name = "createdAt", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private var createdAt: Date? = null,

    @Column(name = "updatedAt")
    @Temporal(TemporalType.TIMESTAMP)
    private var updatedAt: Date? = null,

    @Column(name = "startAt")
    @Temporal(TemporalType.TIMESTAMP)
    private var startAt: Date? = null,

    @Column(name = "endAt")
    @Temporal(TemporalType.TIMESTAMP)
    private var endAt: Date? = null,

    @Enumerated(EnumType.STRING)
    @Column(
        name = "contentLinkType",
        columnDefinition = "enum('URL', 'VOTE', 'APPLY', 'WEB_VIEW', 'COMMENT', 'BINGO', 'DAILY_CHECK', 'MULTI', 'EVENT')"
    )
    private var contentLinkType: ContentLinkType? = null,

    @Column(name = "imageUrlId")
    private var imageUrlId: Long? = null,

    @Column(name = "linkUrl", length = 500)
    private var linkUrl: String? = null,

    @Column(name = "numOrder", nullable = false)
    private var numOrder: Int = 0,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private var status: EventStatus = EventStatus.ACTIVE,

    @Column(name = "imageUrlId2")
    private var imageUrlId2: Long? = null,

    @Column(name = "primaryTag", length = 100)
    private var primaryTag: String? = null,

    @Column(name = "isDuplication")
    private var isDuplication: Boolean? = null,

    @Column(name = "contents", columnDefinition = "TEXT")
    private var contents: String? = null,

    @Column(name = "imageUrlId3")
    private var imageUrlId3: Int? = null,

    @Column(name = "checkStartAt")
    @Temporal(TemporalType.TIMESTAMP)
    private var checkStartAt: Date? = null,

    @Column(name = "checkEndAt")
    @Temporal(TemporalType.TIMESTAMP)
    private var checkEndAt: Date? = null,

    @Column(name = "subEventsId")
    private var subEventsId: Long? = null,

//
    private val mainImgUrl: String? = null, //챌린지,이벤트 배너 리스트
    private val subImgUrl: String? = null, //챌린지,이벤트 배너 상셍
    private val innerImgUrl: String? = null, //챌린지 베너 내부

) {

    fun getContentLinkType(): ContentLinkType? {
        return contentLinkType
    }

    fun getLinkUrl(): String? {
        return linkUrl
    }

    constructor(
        id: Long,
        title: String,
        type: EventType?,
        createdBy: Long,
        createdAt: Date?,
        updatedAt: Date?,
        contentLinkType: ContentLinkType?,
        linkUrl: String,
        numOrder: Int,
        status: EventStatus,
        primaryTag: String?,
        contents: String?,
        checkStartAt: Date?,
        checkEndAt: Date?,
        subEventsId: Long?,
        startAt: Date?,
        endAt: Date?,
        mainImgUrl: String?,
        subImgUrl: String?,
        innerImgUrl: String?
    ) : this(
        id,
        title,
        type,
        createdBy,
        true,
        createdAt,
        updatedAt,
        startAt,
        endAt,
        contentLinkType,
        null,
        linkUrl,
        numOrder,
        status,
        null,
        primaryTag,
        false,
        contents,
        null,
        checkStartAt,
        checkEndAt,
        subEventsId,
        mainImgUrl,
        subImgUrl,
        innerImgUrl
    )


    companion object {
        fun mapToDomain(jpaEntity: EventJpaEntity): Challenge = with(jpaEntity) {
            Challenge(
                id ?: 0,
                title,
                startAt ?: Date(),
                endAt ?: Date(),
                linkUrl,
                numOrder,
                primaryTag,
                subEventsId,
                mainImgUrl,
                subImgUrl,
                innerImgUrl
            )
        }
    }


}
