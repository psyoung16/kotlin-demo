package org.psy.demo.infra.jpaRepository

import org.psy.demo.infra.jpaEntity.FileJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component

@Component
interface FileJpaRepository : JpaRepository<FileJpaEntity, Long> {


    fun findByIdIn(ids: List<Long>): List<FileJpaEntity>

}