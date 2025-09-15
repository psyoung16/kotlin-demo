package org.psy.demo.infra.repository

import org.psy.demo.infra.mapper.ReportMapper
import org.psy.demo.core.user.domain.Report
import org.psy.demo.infra.jpaEntity.ReportJpaEntity
import org.psy.demo.infra.jpaEntity.ReportType
import org.psy.demo.core.user.domain.User
import org.psy.demo.infra.jpaRepository.ReportJpaRepository
import org.springframework.stereotype.Component

@Component
class ReportRepository(
   private val reportJpaRepository: ReportJpaRepository
){
    fun loadReportPosts(userId: User.UserId): List<Report> {
        val entity : List<ReportJpaEntity>  = reportJpaRepository.findByReporterIdAndReportType(userId.id.toLong(), ReportType.POST)
        return entity.map(ReportMapper::mapToDomain)
    }


}
