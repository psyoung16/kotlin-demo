package org.psy.demo.infra.jpaEntity

import jakarta.persistence.*
import org.psy.demo.core.goods.domain.GoodsManaging
import java.util.*


@Entity
@Table(name = "goods_managing")
class GoodsManagingJpaEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    @Column(name = "createdAt")
    @Temporal(TemporalType.TIMESTAMP)
    private var createdAt: Date? = null

    @Column(name = "startAt")
    @Temporal(TemporalType.TIMESTAMP)
    private var startAt: Date? = null

    @Column(name = "endAt")
    @Temporal(TemporalType.TIMESTAMP)
    private var endAt: Date? = null

    @Column(name = "updatedAt")
    @Temporal(TemporalType.TIMESTAMP)
    private var updatedAt: Date? = null

    @Column(name = "orderNum")
    private var orderNum: Int? = null

    @Column(name = "createdBy")
    private var createdBy: Int? = null

    @Column(name = "title", length = 25)
    private var title: String? = null

    @Column(name = "type", length = 20)
    private var type: String? = null


    companion object    {
        fun mapToDomain(goodsManaging: GoodsManagingJpaEntity) = GoodsManaging(
            goodsManaging.id ?: 0L,
            goodsManaging.title.orEmpty(),
            goodsManaging.startAt ?: Date(),
            goodsManaging.endAt ?: Date(),
            goodsManaging.orderNum ?: 0
        )
    }



}
