package org.psy.demo.infra.jpaEntity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "comments")
class CommentJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long,

    private val postId: Long?,
    private val postOwnerId: Long?,
    private val parentId: Long?,

    @Column(nullable = false)
    private val content: String,
    @Column(nullable = false)
    private val isEdit: Boolean = false,
    @Column(nullable = false)
    private val totalReply: Int = 0,

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
        columnDefinition = "ENUM('ACTIVE', 'DEACTIVATED', 'REMOVED', 'USER_REMOVED', 'ADMIN_WARNING', 'ACTIVE_BLOCKED', 'DEACTIVATED_BLOCKED', 'USER_REMOVED_BLOCKED', 'ADMIN_WARNING_BLOCKED') default 'ACTIVE'"
    )
    private var status: CommentStatus? = null,

    private var tagCommentOwnerId: Long? = null,

    @Column(nullable = false)
    private var isSecret: Boolean = false,

    private var eventsId: Int,

    @Column(nullable = false)
    private var isAiAnswer: Boolean = false,

    private var useToken: Int = 0,

    ) {
}

enum class CommentStatus {
    ACTIVE, DEACTIVATED, REMOVED, USER_REMOVED, ADMIN_WARNING, ACTIVE_BLOCKED, DEACTIVATED_BLOCKED, USER_REMOVED_BLOCKED, ADMIN_WARNING_BLOCKED
}
