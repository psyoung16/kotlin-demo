package org.psy.demo.infra.jpaRepository

import io.lettuce.core.dynamic.annotation.Param
import org.psy.demo.infra.jpaEntity.WriterGoodsJpaEntity
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface GoodsJpaRepository : JpaRepository<WriterGoodsJpaEntity, Long> {

    @Query(
        value = """
            select new WriterGoodsJpaEntity (
                wg.id, wg.brandName, wg.goodsName, wg.writerId, wg.url,
                wg.createdAt, wg.updatedAt, wg.status, wg.defaultPrice,
                wg.salePrice, wg.salePersent, wg.code, wg.cmdtCode, wg.thumbnailGoodsFile, 
                wg.isShow, wg.wishCount, wg.orderNum,
                gf.url
            )from WriterGoodsJpaEntity wg 
            join GoodsFileJpaEntity gf on wg.thumbnail = gf.id
            where wg.writerId = :writerId 
            and wg.status = 'ACTIVE' and wg.isShow = true 
            order by wg.createdAt desc
            """
    )
    fun findGoodsListByWriterId(writerId: Long, pageRequest: PageRequest?): List<WriterGoodsJpaEntity>

    @Query(
        value = """
            select wg from WriterGoodsJpaEntity wg 
            join GoodsFileJpaEntity gf on wg.thumbnail = gf.id
            join GoodsManagingWriterGoodsJpaEntity gmwg on gmwg.writerGoodsId = wg.id
            where wg.status = 'ACTIVE'
            and gmwg.goodsManagingId = :gmId 
            and wg.status = 'ACTIVE'
            order by gmwg.orderNum asc 
            
            """
    )
    fun findGoodsListByGmId(gmId: Int, pageRequest: PageRequest?): List<WriterGoodsJpaEntity>

    @Query(
        value = """
            select new WriterGoodsJpaEntity (
                wg.id, wg.brandName, wg.goodsName, wg.writerId, wg.url,
                wg.createdAt, wg.updatedAt, wg.status, wg.defaultPrice, wg.salePrice, 
                wg.salePersent,
                wg.code, wg.cmdtCode, wg.thumbnailGoodsFile, wg.isShow, wg.wishCount, wg.orderNum,
                gf.url
            )from WriterGoodsJpaEntity wg 
            join GoodsFileJpaEntity gf on wg.thumbnail = gf.id
            where wg.status = 'ACTIVE'
            and wg.isShow = true 
            and wg.status = 'ACTIVE'
            and wg.id in :goodsIds
            order by wg.createdAt desc
            
            """
    )
    fun findGoodsListByGoodsIds(goodsIds: List<Long>): List<WriterGoodsJpaEntity>


    @Query(
        value = """
            select wg.id from WriterGoodsJpaEntity wg
            where wg.status = 'ACTIVE' and wg.isShow = true 
            and wg.code like '0001%'
            and wg.newThumbnailUrl is not null
            order by wg.createdAt desc
    
    """
    )
    fun findAvaliableGoodsIdAndCategory(request: PageRequest?): List<Long>

    @Query(
        value = """
            select wg.id from WriterGoodsJpaEntity wg 
            join WriterJpaEntity w on wg.writerId = w.id
            where w.nickname = :writerName and wg.status = 'ACTIVE' and wg.isShow = true
            and wg.code like '0001%'
            and wg.newThumbnailUrl is not null
            order by wg.createdAt desc
    
    """
    )
    fun findAvaliableGoodsIdAndCategoryAndWriterName(writerName: String?, request: PageRequest?): List<Long>


    @Query(
        value = """
            select wg from WriterGoodsJpaEntity wg 
            join GoodsWishJpaEntity gwj on gwj.goodsId = wg.id
            where gwj.createdBy = :userId and gwj.status = 'ACTIVE'
            order by gwj.createdAt desc
            """
    )
    fun findGoodsListByWish(userId: Long, pageRequest: PageRequest): List<WriterGoodsJpaEntity>


    @Query(
        value = """
        SELECT id FROM writer_goods wg1
            WHERE (
                SELECT COUNT(*)
                FROM writer_goods wg2
                WHERE wg2.writerid = wg1.writerid
                  AND (wg2.createdAt > wg1.createdAt 
                       OR (wg2.createdAt = wg1.createdAt AND wg2.id > wg1.id))
            ) < 2
            AND wg1.writerid IN (:writerIds)
            ORDER BY wg1.writerid, wg1.createdAt DESC, wg1.id DESC
            """,
        countQuery = "SELECT 0",
        nativeQuery = true
    )
    fun findTop2REcentGoodsIdByWriterId(@Param("writerIds") writerIds: List<Long>): List<Long>
}