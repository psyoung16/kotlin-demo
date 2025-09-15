package org.psy.demo.infra.jpaRepository

import org.psy.demo.infra.jpaEntity.ReportJpaEntity
import org.psy.demo.infra.jpaEntity.ReportType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReportJpaRepository : JpaRepository<ReportJpaEntity, Long>{

    fun findByReporterIdAndReportType(reporterId: Long, reportType: ReportType): List<ReportJpaEntity>

    fun findByReporterIdAndReportTypeAndReportedPersonId(reporterId: Long, reportType: ReportType, reportedPersonId: Long): ReportJpaEntity?

}
