package org.psy.demo.infra.jpaEntity

import org.psy.demo.common.formattingLegacyDate
import jakarta.persistence.*
import org.psy.demo.core.goods.domain.Goods
import java.util.*
import kotlin.jvm.Transient

@Entity
@Table(name = "writer_goods")
class WriterGoodsJpaEntity (


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private var id: Long? = null,

    @Column(name = "brandName")
    private var brandName: String? = null,

    @Column(name = "goodsName")
    private var goodsName: String? = null,

    @Column(name = "writerId")
    private var writerId: Long? = null,

    @Column(name = "url", nullable = false)
    private var url: String? = null,

    @Column(name = "thumbnail")
    private var thumbnail: Long? = null,

    @Column(name = "createdAt")
    private var createdAt: Date? = null,

    @Column(name = "updatedAt")
    private var updatedAt: Date? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "ENUM('ACTIVE', 'DEACTIVATED', 'BLOCKED', 'REMOVED') default 'ACTIVE'")
    private var status: WriterGoodsStatus? = null,

    @Column(name = "defaultPrice", nullable = false)
    private var defaultPrice: Int = 0,

    @Column(name = "salePrice" , nullable = false)
    private var salePrice: Int = 0,

    @Column(name = "salePersent", columnDefinition = "VARCHAR(10) default '0'")
    private var salePersent: String? = null,

    @Column(name = "code")
    private var code: String? = null,

    @Column(name = "cmdtCode")
    private var cmdtCode: String? = null,

    @Column(name = "isSync", columnDefinition = "TINYINT(1) default 1")
    private var isSync: Boolean? = false,

    @Column(name = "thumbnailGoodsFile")
    private var thumbnailGoodsFile: Long? = null,

    @Column(name = "isShow", columnDefinition = "TINYINT(1) default 1")
    private var isShow: Boolean? = false,

    @Column(name = "wishCount")
    private var wishCount: Int? = 0,

    @Column(name = "totalView")
    private var totalView: Int = 0,

    @Column(name = "totalWish")
    private var totalWish: Int = 0,

    @Column(name = "isManualUpdate", columnDefinition = "TINYINT(1) default 0", nullable = false)
    private var isManualUpdate: Boolean? = false,

    @Column(name = "hottracksCode")
    private var hottracksCode: String? = null,

    @Column(name = "orderNum")
    private var orderNum: Int = 0,

    @Column(name = "newThumbnailUrl")
    private val newThumbnailUrl: String? = null,

    @Transient
    private val thumbnailUrl: String? = null, //db가져오는 것 때문에 생성, @transient는 조회시 무시되기때문에 생략 , 저장할때 유의 필요

){

    constructor(
        id: Long, brandName: String?, goodsName: String?, writerId: Long?, url: String?,
        createdAt: Date?, updatedAt: Date?, status: WriterGoodsStatus?, defaultPrice: Int,
        salePrice: Int, salePersent: String?, code: String?, cmdtCode: String?,
        thumbnailGoodsFile: Long?, isShow: Boolean, wishCount: Int?, orderNum: Int, thumbnailUrl: String?
    ) : this(
        id, brandName, goodsName, writerId, url, null,
        createdAt, updatedAt, status, defaultPrice, salePrice,
        salePersent, code, cmdtCode, true, thumbnailGoodsFile,
        isShow, wishCount, 0, 0, false,
        "", orderNum, thumbnailUrl
    )

    companion object {
        fun mapToDomain(writerGoods: WriterGoodsJpaEntity) = Goods(
            writerGoods.id ?: 0,
            writerGoods.writerId ?: 0,
            writerGoods.goodsName.orEmpty(),
            writerGoods.brandName.orEmpty(),
            writerGoods.url.orEmpty(),
            writerGoods.salePrice,
            writerGoods.salePersent.orEmpty(),
            writerGoods.cmdtCode.orEmpty(),
            writerGoods.defaultPrice,
            formattingLegacyDate(writerGoods.createdAt),
            formattingLegacyDate(writerGoods.updatedAt),
            writerGoods.thumbnailUrl ?: writerGoods.newThumbnailUrl.orEmpty(),
        )

    }
}
enum class WriterGoodsStatus{
    ACTIVE, DEACTIVATED, BLOCKED, REMOVED
}
