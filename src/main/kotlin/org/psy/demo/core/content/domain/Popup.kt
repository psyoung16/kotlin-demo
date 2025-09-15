package org.psy.demo.core.content.domain

import org.psy.demo.common.MetaData
import org.psy.demo.infra.jpaEntity.SlideJpaEntity
import org.psy.demo.core.vo.SlideExposure
import org.psy.demo.core.vo.SlidePosition
import org.psy.demo.core.vo.SlideSimpleImageResponse
import org.psy.demo.core.vo.SlideStatus

data class Popup(
    val id: Long,
//    val bannerId: BannerId,
    var type: SlideJpaEntity.SlideType,
    val webViewTitle: String,
    var linkUrl: String,

    val mainImage: SlideSimpleImageResponse?,
    val subImage: SlideSimpleImageResponse?,
    val popupImage: SlideSimpleImageResponse,

    val numOrder: Int,
    val createdBy: Long,

    val startAt: String,
    val endAt: String,
    val position: SlidePosition,
    val exposure: SlideExposure,
    val eventsId: Long?,

    val status: SlideStatus,
    val createdAt: String,
    val updatedAt: String,
//    val mainImg: Object,
//    val subImg: Object
//        val eventAction: EventAction
) {


    companion object {
    }


}

data class PopupWithPagination(
    val slides: List<Popup>,
    val metadata: MetaData
) {}
