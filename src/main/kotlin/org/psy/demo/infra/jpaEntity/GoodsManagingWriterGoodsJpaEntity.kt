package org.psy.demo.infra.jpaEntity

import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "goods_managing_writer_goods")
class GoodsManagingWriterGoodsJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private var id: Long? = null

    @Column(name = "createdAt")
    @Temporal(TemporalType.TIMESTAMP)
    private var createdAt: Date? = null

    @Column(name = "updatedAt")
    @Temporal(TemporalType.TIMESTAMP)
    private var updatedAt: Date? = null

    @Column(name = "writerGoodsId")
    private var writerGoodsId: Long? = null

    @Column(name = "goodsManagingId")
    private var goodsManagingId: Int? = 0

    @Column(name = "orderNum")
    private var orderNum: Int? = null


}
