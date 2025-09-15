package org.psy.demo.infra.jpaEntity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "post_interaction")
class PostInteractionJpaEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long?,

    @Column(nullable = false)
    private var isLike: Boolean,

    @Column(nullable = false)
    private var isScrap: Boolean,

    @Temporal(TemporalType.TIMESTAMP)
    private var likeCreatedAt: Date,

    @Temporal(TemporalType.TIMESTAMP)
    private var likeUpdatedAt: Date,

    @Temporal(TemporalType.TIMESTAMP)
    private var scrapCreatedAt: Date,

    @Temporal(TemporalType.TIMESTAMP)
    private var scrapUpdatedAt: Date,

    @Column(nullable = false)
    private var createdBy: Long,

    @Column(nullable = false)
    private var postId: Long

    ) {

    fun getId(): Long? {
        return id
    }

    fun getPostId(): Long? {
        return postId
    }

    fun getIsLike(): Boolean {
        return isLike
    }

    fun getIsScrap(): Boolean {
        return isScrap
    }

    fun getLikeCreatedAt(): Date {
        return likeCreatedAt
    }

    fun getLikeUpdatedAt(): Date {
        return likeUpdatedAt
    }

    fun getScrapCreatedAt(): Date {
        return scrapCreatedAt
    }

    fun getScrapUpdatedAt(): Date {
        return scrapUpdatedAt
    }
    fun getCreatedBy(): Long {
        return createdBy
    }
}
