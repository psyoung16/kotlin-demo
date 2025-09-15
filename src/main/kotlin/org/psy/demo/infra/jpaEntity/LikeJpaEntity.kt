package org.psy.demo.infra.jpaEntity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Temporal
import jakarta.persistence.TemporalType
import java.util.Date

@Entity
@Table(name = "likes")
class LikeJpaEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private val id: Long? = null,

    @Column(nullable = false)
    private var postId: Long,

    @Column(nullable = false)
    private var postOwnerId: Long,

    @Column(nullable = false)
    private var createdBy: Long,

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private var createdAt: Date,

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private val updatedAt: Date,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private var status: LikeStatus? = null,

    ) {
}

enum class LikeStatus{
    ACTIVE, REMOVED
}
