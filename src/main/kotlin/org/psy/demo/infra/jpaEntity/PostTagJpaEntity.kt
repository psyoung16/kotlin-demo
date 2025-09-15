package org.psy.demo.infra.jpaEntity

import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "post_tags")
class PostTagJpaEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private var id: Long? = null

    @Column(name = "postId", nullable = false)
    private var postId: Long = 0L

    @Column(name = "tagId", nullable = false)
    private var tagId: Long = 0L

    @Column(name = "createdAt", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private var createdAt: Date? = null

    @Column(name = "updatedAt")
    @Temporal(TemporalType.TIMESTAMP)
    private var updatedAt: Date? = null

    @Column(name = "isRecom", nullable = false)
    private var isRecom = false

    @Column(name = "isWriter", nullable = false)
    private var isWriter = false


    fun getId(): Long? {
        return id
    }
    fun getPostId(): Long {
        return postId
    }

}
