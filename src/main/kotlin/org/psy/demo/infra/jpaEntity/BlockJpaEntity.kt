package org.psy.demo.infra.jpaEntity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "blocks")
class BlockJpaEntity (

    @Column(nullable = false)
    private val blockerId: Long,
    @Column(nullable = false)
    private val blockedPersonId: Long,

    @Column(nullable = false)
    private val createdAt: Date,
    @Column(nullable = false)
    private val updatedAt: Date,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private val status: BlockStatus,
){
    //고민중
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    fun getId(): Long? {
        return id
    }
    fun getBlockerdId(): Long {
        return blockerId
    }
    fun getBlockedPersonId(): Long {
        return blockedPersonId
    }
    fun getCreatedAt(): Date {
        return createdAt
    }
    fun getUpdatedAt(): Date {
        return updatedAt
    }
    fun getStatus(): BlockStatus {
        return status
    }

}
enum class BlockStatus {
    ACTIVE, DEACTIVATED, AC_BLOCKED, DEAC_BLOCKED, REMOVED
}
