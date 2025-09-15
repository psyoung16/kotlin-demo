package org.psy.demo.infra.jpaEntity

import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "user_badge")
class BadgeJpaEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private val badgeType: BadgeType,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private var status: BadgeStatus,

    @Column(nullable = false)
    private val ownerId: Long,

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private var createdAt: Date,

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private val updatedAt: Date,

    private val orderNum: Long,
){

    fun getId(): Long? = id
    fun getBadgeType(): BadgeType = badgeType
    fun getBadgeStatus(): BadgeStatus = status
    fun getOwnerId(): Long = ownerId


}
enum class BadgeType {
    MAFIA_1,
    UPLOAD_1,
    UPLOAD_10, UPLOAD_50, UPLOAD_100, FOLLOWER_10
    , FOLLOWER_50, FOLLOWER_100, COMMENT_1, COMMENT_10,
    COMMENT_50, COMMENT_100, FOLLOWING_10, FOLLOWING_50, FOLLOWING_100
}
enum class BadgeStatus {
    ACTIVE, DEACTIVATED, BLOCKED, REMOVED, READY
}
