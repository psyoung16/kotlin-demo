package org.psy.demo.infra.jpaRepository

import org.psy.demo.infra.jpaEntity.CodeJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CodeJpaRepository : JpaRepository<CodeJpaEntity, Long> {

    @Query(value = """
        select new CodeJpaEntity (
            c.code, c.codeName, c.isShow, c.orderNum, cf.url
        ) from CodeJpaEntity c
        join  CommonFileJpaEntity cf on c.iconImgId = cf.id
        where c.isShow = true and length(c.code) = 4
        order by c.orderNum asc
    """)
    fun findCodeDepth0(): List<CodeJpaEntity>



}