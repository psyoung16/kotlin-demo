package org.psy.demo.infra.jpaEntity

import jakarta.persistence.*
import org.psy.demo.core.vo.FileStatus
import java.util.*


@Entity
@Table(name = "files")
class FileJpaEntity (


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long,

    @Column(name = "createdBy")
    private var createdBy: Long? = null,

    @Column(name = "type", length = 50, nullable = false)
    private var type: String? = null,

    @Column(name = "bucket", length = 50, nullable = false)
    private var bucket: String? = null,

    @Column(name = "url", length = 255, nullable = false)
    private var url: String? = null,

    @Column(name = "key", length = 255, nullable = false)
    private var key: String? = null,

    @Column(name = "filename", length = 255)
    private var filename: String? = null,

    @Column(name = "size", nullable = false)
    private var size: Int? = null,


    /*@Column(name = "createdAt", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updatedAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;*/
    private val createdAt: Date? = null,
    private val updatedAt: Date? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private var status: FileStatus? = null,

    @Column(name = "postId")
    private var postId: Long? = null,

    @Column(name = "writerId")
    private var writerId: Long? = null,

    @Column(name = "is300", nullable = false)
    private var is300: Boolean = false

    ){

    fun getId(): Long {
        return id
    }

    fun getUrl(): String {
        return url ?: ""
    }

}
