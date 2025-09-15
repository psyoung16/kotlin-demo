package org.psy.demo.infra.jpaEntity

import org.psy.demo.core.domain.entity.vo.FileStatus
import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "goods_files")
class GoodsFileJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private var id: Long? = null

    @Column(name = "createdBy", nullable = false)
    private var createdBy: Long? = null

    @Column(name = "type", nullable = false, length = 50)
    private var type: String? = null

    @Column(name = "bucket", nullable = false, length = 50)
    private var bucket: String? = null

    @Column(name = "url", nullable = false, length = 255)
    private var url: String? = null

    @Column(name = "key", nullable = false, length = 255)
    private var key: String? = null

    @Column(name = "filename", length = 255)
    private var filename: String? = null

    @Column(name = "size", nullable = false)
    private var size = 0

    @Column(name = "createdAt", nullable = false)
    private var createdAt: Date? = null

    @Column(name = "updatedAt")
    private var updatedAt: Date? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private var status: FileStatus? = null

    @Column(name = "goodsId")
    private var goodsId: Long? = null

    internal enum class Status {
        ACTIVE, DEACTIVATED, BLOCKED, REMOVED, READY
    }

}
