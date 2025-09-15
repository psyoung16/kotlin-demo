package org.psy.demo.infra.jpaEntity

import org.psy.demo.common.intToType
import jakarta.persistence.*
import org.psy.demo.core.post.domain.BestPost
import org.psy.demo.core.post.domain.PostsSimple
import java.util.*


@Entity
@Table(name = "posts")
class PostJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null,

    @Lob
    @Column(nullable = false)
    private var description: String,

    @Column(nullable = false, columnDefinition = "int default 0")
    private var totalComments: Int,
    @Column(nullable = false, columnDefinition = "int default 0")
    private var totalScraps: Int,

    @Column(nullable = false, columnDefinition = "int default 0")
    private var totalViews: Int,

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private var createdAt: Date,

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private val updatedAt: Date,

    @Column(nullable = false)
    private var createdBy: Long,

    @Enumerated(EnumType.STRING)
    @Column(
        nullable = false,
        columnDefinition = "ENUM('ACTIVE', 'DEACTIVATED', 'AC_BLOCKED', 'DEAC_BLOCKED', 'AC_ADMIN_WARNING', 'DEAC_ADMIN_WARNING', 'REMOVED', 'ADMIN_WARNING', 'ADMIN_WARNING_BLOCKED') default 'ACTIVE'"
    )
    private var status: PostStatus? = null,

    @Column(nullable = false, columnDefinition = "int default 0")
    private var totalPopularTags: Int,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('NORMAL', 'RECOMMEND', 'WEB_VIEW', 'URL') default 'NORMAL'")
    private var postType: PostType? = null,

    @Column(nullable = false, columnDefinition = "int default 0")
    private var numOrder: Int,

    private val thumbnail: Long? = null,

    @Column(nullable = false, columnDefinition = "int default 0")
    private var totalLikes: Int,

    private val postInfoId: Int? = null,

    @Column(columnDefinition = "TINYINT(1) default 0")
    private var isKkommingTalk: Boolean? = null,

    @Temporal(TemporalType.TIMESTAMP)
    private val endAt: Date? = null,

    private val title: String? = null,

    @Column(columnDefinition = "TINYINT(1) default 0")
    private var isDuplication: Boolean? = null,

    private val isNotiPost: Boolean? = null,

    private val linkUrl: String? = null,

    @Column(columnDefinition = "int default 0")
    private var totalRequest: Int,

    private val version: String? = null,
    //image
    private val thumbnailUrl: String? = null,
) {

    //withThumbnailUrl
    constructor(
        id: Long, description: String,
        createdAt: Date, updatedAt: Date, createdBy: Long, status: PostStatus?, endAt: Date?,
        postType: PostType?, numOrder: Int, postInfoId: Int?, isKkommingTalk: Boolean?,
        title: String?, isDuplication: Boolean?, isNotiPost: Boolean?, linkUrl: String?, version: String?,
        thumbnailUrl: String?
    ) : this(
        id,
        description,
        0,
        0,
        0,
        createdAt,
        updatedAt,
        createdBy,
        status,
        0,
        postType,
        numOrder,
        0,
        0,
        postInfoId,
        isKkommingTalk,
        endAt,
        title,
        isDuplication,
        isNotiPost,
        linkUrl,
        0,
        version,
        thumbnailUrl
    )

    companion object {
        fun mapToDomain(entity: PostJpaEntity) = BestPost(
            entity.id ?: 0,
            entity.description.orEmpty(),
            entity.title.orEmpty(),
            entity.postInfoId ?: 0,
            entity.thumbnailUrl.orEmpty(),
            null,
            arrayListOf()
        )

        fun mapToPostRes(entity: PostJpaEntity) = PostsSimple(
            entity.id ?: 0,
            entity.title.orEmpty(),
            entity.thumbnailUrl.orEmpty(),
            intToType(entity.postInfoId ?: 0),
        )
    }

    fun getId(): Long = id ?: 0
    fun getDescription(): String = description.orEmpty()
    fun getTitle(): String = title.orEmpty()
    fun getPostInfoId(): Int = postInfoId ?: 0
    fun getThumbnailUrl(): String = thumbnailUrl.orEmpty()
    fun getPostType(): PostType = postType ?: PostType.NORMAL
    fun getStatus(): PostStatus = status ?: PostStatus.ACTIVE
    fun getCreatedAt(): Date = createdAt ?: Date()
    fun getUpdatedAt(): Date = updatedAt ?: Date()
    fun getCreatedBy(): Long = createdBy
    fun getNumOrder(): Int = numOrder
    fun getIsKkommingTalk(): Boolean = isKkommingTalk ?: false
    fun getEndAt(): Date = endAt ?: Date()
    fun getIsDuplication(): Boolean = isDuplication ?: false
    fun getIsNotiPost(): Boolean = isNotiPost ?: false
    fun getLinkUrl(): String = linkUrl.orEmpty()
    fun getVersion(): String = version.orEmpty()



}

enum class PostType {
    NORMAL, RECOMMEND, WEB_VIEW, URL
}

enum class PostStatus {
    ACTIVE, DEACTIVATED, AC_BLOCKED, DEAC_BLOCKED, AC_ADMIN_WARNING, DEAC_ADMIN_WARNING, REMOVED, ADMIN_WARNING, ADMIN_WARNING_BLOCKED
}
