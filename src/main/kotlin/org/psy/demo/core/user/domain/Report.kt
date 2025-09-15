package org.psy.demo.core.user.domain

import org.psy.demo.core.domain.entity.post.Post
import org.psy.demo.infra.jpaEntity.ReportState
import org.psy.demo.infra.jpaEntity.ReportType

data class Report(
    val reportId: ReportId,
    val reporterId: User.UserId,
    val reportedPersonId: Long,
    val reportCategoryId: Int,
    val reportType: ReportType,
    val reportPostId: Post.PostId?,
    val reportCommentId: Long?,
    val reportState: ReportState,
){
    data class ReportId(val id: String) {
        companion object {
            fun of(id: String): ReportId = ReportId(id)
        }
    }
}
