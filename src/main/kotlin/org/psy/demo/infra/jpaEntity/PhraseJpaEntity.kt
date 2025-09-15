package org.psy.demo.infra.jpaEntity

import jakarta.persistence.*
import org.psy.demo.core.phrase.domain.Phrase
import org.psy.demo.core.phrase.domain.PhraseManagement
import java.util.*


@Entity
@Table(name = "phrases_settings")
class PhraseJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private var id: Int? = null,

    @Column(name = "startAt")
    @Temporal(TemporalType.TIMESTAMP)
    private var startAt: Date? = null, //추후 삭제

    @Column(name = "endAt")
    @Temporal(TemporalType.TIMESTAMP)
    private var endAt: Date? = null, //추후 삭제

    @Column(name = "createdAt")
    @Temporal(TemporalType.TIMESTAMP)
    private var createdAt: Date? = null,

    @Column(name = "updatedAt")
    @Temporal(TemporalType.TIMESTAMP)
    private var updatedAt: Date? = null,

    @Column(name = "phrase", length = 200)
    private var phrase: String? = null,

    @Column(name = "position", length = 20)
    private var position: String? = null,

    @Column(name = "createdBy")
    private var createdBy: Int? = null,

    @Column(name = "boldPhrase", length = 200)
    private var boldPhrase: String? = null,
) {


    fun mapToDomainManagement(): PhraseManagement {
        return Companion.mapToDomainManagement(this)
    }

    companion object {
        fun mapToDomain(entity: PhraseJpaEntity) = Phrase(
            entity.id,
            entity.phrase.orEmpty(),
            entity.position.orEmpty(),
            entity.boldPhrase.orEmpty()

        )

        fun mapToDomainManagement(entity: PhraseJpaEntity) = PhraseManagement(
            entity.id.toString(),
            entity.phrase.orEmpty(),
            entity.position.orEmpty(),
            entity.boldPhrase.orEmpty(),
            entity.createdAt
        )
    }
}



