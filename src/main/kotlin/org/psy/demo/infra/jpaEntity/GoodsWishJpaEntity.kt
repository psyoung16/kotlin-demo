package org.psy.demo.infra.jpaEntity

import jakarta.persistence.*
import java.util.Date


@Entity
@Table(name = "goods_wish")
class GoodsWishJpaEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null,

    @Column(nullable = false)
    private val createdBy : Long,

    @Column(nullable = false)
    private val goodsId : Long,

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private var createdAt: Date,

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private val updatedAt: Date,


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private var status: GoodsWishStatus? = GoodsWishStatus.ACTIVE,


) {

}


enum class GoodsWishStatus {
    ACTIVE, REMOVED
}
