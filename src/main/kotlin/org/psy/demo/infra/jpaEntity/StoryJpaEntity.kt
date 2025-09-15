package org.psy.demo.infra.jpaEntity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "storys")
class StoryJpaEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private var id: Long,

    private var title: String,
    private var description: String,

    private var createdBy: Long,

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private var createdAt: Date,

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private val updatedAt: Date,

    @Column(nullable = false)
    private val thumbnail: Long,

    @Enumerated(EnumType.STRING)
    private var status: StoryStatus? = null,

){

    fun getId(): Long {
        return id
    }

    fun getTitle(): String {
        return title
    }

    fun getDescription(): String {
        return description
    }

    fun getCreatedBy(): Long {
        return createdBy
    }

    fun getCreatedAt(): Date {
        return createdAt
    }

    fun getThumbnail(): Long {
        return thumbnail
    }




}

enum class StoryStatus {
    ACTIVE, REMOVED
}
