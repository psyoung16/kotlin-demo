package org.psy.demo.infra.jpaEntity

import jakarta.persistence.*
import org.psy.demo.core.vo.TagStatus
import java.util.*

@Entity
@Table(name = "tags")
class TagJpaEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    private var id: Long? = null,

    @jakarta.persistence.Column(name = "createdBy", nullable = false)
    private val createdBy: Long? = null,

    @Column(name = "roleCreator", nullable = false, length = 10)
    private var roleCreator: String? = null,

    @Column(name = "tagName", nullable = false, length = 255)
    private var tagName: String? = null,

    @Column(name = "totalSelects", nullable = false)
    private var totalSelects : Int = 0,

    @Column(name = "createdAt")
    @Temporal(TemporalType.TIMESTAMP)
    private var createdAt: Date? = null,

    @Column(name = "updatedAt")
    @Temporal(TemporalType.TIMESTAMP)
    private var updatedAt: Date? = null,

    @Enumerated(EnumType.STRING)
    @Column(
        name = "status",
        nullable = false,
        columnDefinition = "enum('ACTIVE', 'DEACTIVATED', 'REMOVED') default 'ACTIVE'"
    )
    private var status : TagStatus = TagStatus.ACTIVE,

    @Column(name = "totalSearchs", nullable = false)
    private var totalSearchs : Int =0,

    @Column(name = "numOrder", nullable = false)
    private var numOrder : Int =0
) {
}
