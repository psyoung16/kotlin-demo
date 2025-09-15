package org.psy.demo.infra.jpaEntity

import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "follows")
class FollowJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long,

    private var followerId: Long,

    private var followingId: Long,

    private var createdAt: Date,
    private var updatedAt: Date,
    private var status: String,

    private var isNotification: Boolean,




) {

    fun getId(): Long? {
        return id
    }

    fun getFollowerId(): Long {
        return followerId
    }

    fun getFollowingId(): Long {
        return followingId
    }

}
