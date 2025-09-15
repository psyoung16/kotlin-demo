package org.psy.demo.infra.jpaEntity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "style_post_tags")
class StyleTagJpaEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    private var id: Long?,

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private var type: StyleTagType,

    @Column(nullable = false)
    private var postId: Long,

    @Column(nullable = false)
    private var createdAt: Date,

    @Column(nullable = false)
    private var updatedAt: Date,

    @Column(nullable = false)
    private var createdBy: Long,

)

enum class StyleTagType {
    KITCH,
    LOVELY_CUTE,
    CHIC,
    EMOTION,
    DREAM,
    VINTAGE,
    NATURAL,
    ANIMAL,
    TYPO,
    SIMPLE
}
