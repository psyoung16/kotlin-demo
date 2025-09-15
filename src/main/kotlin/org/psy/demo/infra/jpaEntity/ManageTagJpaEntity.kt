package org.psy.demo.infra.jpaEntity

import jakarta.persistence.*
import org.psy.demo.core.common.domain.ManageTag
import org.psy.demo.core.vo.TagStatus
import org.psy.demo.core.vo.TagType
import org.psy.demo.core.phrase.domain.ManageTagManagement
import java.util.*


@Entity
@Table(name = "manage_tags")
class ManageTagJpaEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    private val id: Long? = null,

    @Column(name = "createdBy")
    private val createdBy: Long? = null, //필요x

    @Column(name = "roleCreator")
    private val roleCreator: String? = null, //필요x

    @Column(name = "tagName", length = 255, nullable = false)
    private val tagName: String? = null,

    @Column(name = "totalSelects", nullable = false)
    private val totalSelects: Int = 0,

    @Column(name = "createdAt")
//        @Temporal(TemporalType.TIMESTAMP)
    private val createdAt: Date? = Date(),

    @Column(name = "updatedAt")
//        @Temporal(TemporalType.TIMESTAMP)
    private val updatedAt: Date? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private val status: TagStatus? = null,

    @Column(name = "totalSearchs", nullable = false)
    private val totalSearchs: Int = 0,

    @Column(name = "numOrder", nullable = false)
    private val numOrder: Int = 0,

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 15, columnDefinition = "valchar(15) default 'RECOMMEND'")
    private val type: TagType,

    @Column(name = "eventsId")
    private val eventsId: Long? = null,

    @Column(name = "startAt")
//        @Temporal(TemporalType.TIMESTAMP)
    private val startAt: Date? = null,

    @Column(name = "endAt")
//        @Temporal(TemporalType.TIMESTAMP)
    private val endAt: Date? = null,

    private val isShow: Boolean? = null
) {

    fun mapToDomainManagement(): ManageTagManagement {
        return Companion.mapToDomainManagement(this)
    }

    companion object {
        fun mapToDomain(manageTag: ManageTagJpaEntity) = ManageTag(
            manageTag.id.toString(),
            manageTag.tagName.orEmpty()
        )

        fun mapToDomainManagement(entity: ManageTagJpaEntity) = ManageTagManagement(
            entity.id,
            entity.tagName.orEmpty(),
            entity.createdAt ?: Date(),
            entity.updatedAt ?: Date(),
            entity.type,
            entity.numOrder,
            entity.isShow ?: false,
            entity.createdBy,
            entity.status ?: TagStatus.ACTIVE
        )
    }


}


