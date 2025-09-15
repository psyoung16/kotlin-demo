package org.psy.demo.infra.jpaEntity

import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "content_managing")
class ContentManagingJpaEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    @Column(name = "createdAt")
    private var createdAt: Date? = null

    @Column(name = "startAt")
    private var startAt: Date? = null

    @Column(name = "endAt")
    private var endAt: Date? = null

    @Column(name = "updatedAt")
    private var updatedAt: Date? = null

    @Column(name = "postId")
    private var postId: Int? = null

    @Column(name = "orderNum")
    private var orderNum: Int? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private var type: ContentManagingType? = null

    @Column(name = "totalLikes")
    private var totalLikes: Int? = null

    @Column(name = "createdBy")
    private var createdBy: Int? = null

    @Column(name = "writerId")
    private var writerId: Long? = null

    @Column(name = "cmmmId")
    private var cmmmId: Long? = null

    @Column(name = "eventsId")
    private var eventsId: Int? = null

    @Column(name = "thumbnail")
    private var thumbnail: Int? = null

    @Column(name = "tags")
    private var tags: String? = null

    @Column(name = "title")
    private var title: String? = null

    @Column(name = "contents")
    private var contents: String? = null

    @Column(name = "linkUrl")
    private var linkUrl: String? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private var status: ContentManagingStatus? = null

    internal enum class ContentManagingStatus {
        FEED, STAR, POST_GOODS, EVENT, CHALLENGE, URL, WEB_VIEW, WRITER, TAG, STYLE_TAG, WRITER_TAG, HOME_WRITER
    }
    internal enum class ContentManagingType {
        ACTIVE, ALWAYS
    }
}
