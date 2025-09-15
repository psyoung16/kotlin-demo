package org.psy.demo.infra.jpaEntity

import jakarta.persistence.*
import org.psy.demo.core.sticker.domain.vo.CommonStatus
import java.util.*

@Entity
@Table(name = "reports")
class ReportJpaEntity (

    @Column(nullable = false, name = "reporterId")
    private val reporterId: Long,

    private val reportedPersonId: Long,

    private val reportCategoryId: Int,

    @Column(nullable = false, name = "createdAt")
    private val createdAt: Date,

    @Column(nullable = false, name = "updatedAt")
    private val updatedAt: Date,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private val status: CommonStatus,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private val reportType: ReportType,

    private val reportPostId : Long? = null,
    private val reportCommentId : Long? = null,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private val reportState : ReportState,
    @Column(nullable = false)
    private val isSeen : Boolean = false


    ){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    fun getId(): Long? {
        return id
    }
    fun getReporterId(): Long {
        return reporterId
    }
    fun getReportedPersonId(): Long {
        return reportedPersonId
    }
    fun getReportCategoryId(): Int {
        return reportCategoryId
    }
    fun getCreatedAt(): Date {
        return createdAt
    }
    fun getUpdatedAt(): Date {
        return updatedAt
    }
    fun getStatus(): CommonStatus {
        return status
    }
    fun getReportType(): ReportType {
        return reportType
    }
    fun getReportPostId(): Long? {
        return reportPostId
    }
    fun getReportCommentId(): Long? {
        return reportCommentId
    }
    fun getReportState(): ReportState {
        return reportState
    }

}

enum class ReportType {
    PROFILE, POST, COMMENT
}

enum class ReportState {
    REPORTED, HOLD, WARNING,
}

