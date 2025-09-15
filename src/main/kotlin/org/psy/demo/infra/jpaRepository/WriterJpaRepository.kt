package org.psy.demo.infra.jpaRepository

import org.psy.demo.infra.jpaEntity.WriterJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface WriterJpaRepository: JpaRepository<WriterJpaEntity, Long> {


    //1:1, 특히 파일은 가져오는걸루...
    @Query(
        value = """
                        select new WriterJpaEntity (
                            w.id, w.userId, w.nickname, w.role, w.linkShare, 
                            w.instarLink, w.facebookLink, w.blogLink, w.youtubeLink, w.bio, 
                            w.createdAt, w.updatedAt, w.status, w.email, w.userType,
                            w.isSync, w.hottracksLink, w.smartStoreLink, w.postypeLink, w.goodsLinkType,
                            f.url, f1.url) from WriterJpaEntity w 
                        join FileJpaEntity f on f.id = w.avatar
                        join FileJpaEntity f1 on f1.id = w.backImg
                        where w.status = 'ACTIVE'
                        and (select count(wg) from WriterGoodsJpaEntity wg where wg.writerId = w.id and wg.status = 'ACTIVE' and wg.isShow = true ) > 0
                        
                    """
    )
    fun getMainProWriterList(): List<WriterJpaEntity>


}
