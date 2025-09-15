package org.psy.demo.infra.mapper

import org.psy.demo.core.user.domain.Report
import org.psy.demo.core.post.domain.Post
import org.psy.demo.infra.jpaEntity.ReportJpaEntity
import org.psy.demo.core.user.domain.User

object ReportMapper {
    fun mapToDomain(entity: ReportJpaEntity) =
        Report(
        reportId = Report.ReportId.of(entity.getId().toString()),
        reporterId = User.UserId(entity.getReporterId().toString()),
        reportedPersonId = entity.getReportedPersonId() ?: 0,
        reportCategoryId = entity.getReportCategoryId() ?: 0,
        reportType = entity.getReportType(),
        reportPostId = Post.PostId(entity.getReportPostId().toString()),
        reportCommentId = entity.getReportCommentId(),
        reportState = entity.getReportState(),
    )
}


/*
object GoodsMapper {
    fun mapToDomain(entity: WriterGoodsJpaEntity) = Goods(
        entity.getId() ?: 0,
        entity.getWriterId() ?: 0,
        entity.getGoodsName().orEmpty(),
        entity.getBrandName().orEmpty(),
        entity.getUrl().orEmpty(),
        entity.priceInfo.getSalePrice(),
        entity.priceInfo.getSalePersent().orEmpty(),
        entity.getCmdtCode().orEmpty(),
        entity.priceInfo.getDefaultPrice(),
        formattingLegacyDate(entity.getCreatedAt()),
        formattingLegacyDate(entity.getUpdatedAt()),
        entity.getNewThumbnailUrl().orEmpty(),
    )*/
