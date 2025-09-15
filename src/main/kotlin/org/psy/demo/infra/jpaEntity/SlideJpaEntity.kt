package org.psy.demo.infra.jpaEntity

//import org.psy.kotlinhexagonaldemo.common.DefaultImgUtil
import org.psy.demo.common.formattingLegacyDate
import org.psy.demo.core.domain.entity.Banner
import org.psy.demo.core.domain.entity.Popup
import org.psy.demo.core.domain.entity.vo.SlideExposure
import org.psy.demo.core.domain.entity.vo.SlidePosition
import org.psy.demo.core.domain.entity.vo.SlideSimpleImageResponse
import org.psy.demo.core.domain.entity.vo.SlideStatus
import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "slides")
class SlideJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private var id: Long? = null,

    @Column(name = "webViewTitle", length = 100)
    private var webViewTitle: String? = null,

    @Column(name = "webViewImage")
    private var webViewImage: Long? = null,

    @Column(name = "createdAt", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private var createdAt: Date? = null,

    @Column(name = "updatedAt")
    @Temporal(TemporalType.TIMESTAMP)
    private var updatedAt: Date? = null,

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private var status: SlideStatus = SlideStatus.ACTIVE,

    @Column(name = "numOrder", nullable = false)
    private var numOrder: Int = 0,

    @Column(name = "webViewUrl", length = 255)
    private var webViewUrl: String? = null,

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private var type: SlideType = SlideType.URL,

    @Column(name = "createdBy", nullable = false)
    private var createdBy: Long? = null,

    @Column(name = "startAt")
    @Temporal(TemporalType.TIMESTAMP)
    private var startAt: Date? = null,

    @Column(name = "endAt")
    @Temporal(TemporalType.TIMESTAMP)
    private var endAt: Date? = null,

    @Column(name = "eventsId")
    private var eventsId: Long? = null,

    @Column(name = "position")
    @Enumerated(EnumType.STRING)
    private var position: SlidePosition? = null,

    @Column(name = "subImage")
    private var subImage: Long? = null,

    @Column(name = "exposure")
    @Enumerated(EnumType.STRING)
    private var exposure: SlideExposure = SlideExposure.BANNER,

    @Column(name = "popupImage")
    private var popupImage: Long? = null,

    @Column(name = "linkUrl", length = 255)
    private var linkUrl: String? = null,

    @Column(name = "mainImage")
    private var mainImage: Long? = null,

    private val urlSetting: String? = null,
) {

    constructor(
        id: Long?,
        webViewTitle: String?,
        createdAt: Date?,
        updatedAt: Date?,
        status: SlideStatus,
        numOrder: Int,
        type: SlideType,
        createdBy: Long?,
        startAt: Date?,
        endAt: Date?,
        eventsId: Long?,
        position: SlidePosition?,
        subImage: Long?,
        exposure: SlideExposure,
        linkUrl: String?,
        urlSetting: String?
    ) : this(
        id,
        webViewTitle,
        null,
        createdAt,
        updatedAt,
        status,
        numOrder,
        null,
        type,
        createdBy,
        startAt,
        endAt,
        eventsId,
        position,
        subImage,
        exposure,
        null,
        linkUrl,
        null,
        urlSetting
    )

    fun mapToPopup(): Popup {
        return Companion.mapToPopup(this)
    }
    fun mapToBanner(): Banner {
        return Companion.mapToBanner(this)
    }

    companion object {
        fun mapToPopup(entity: SlideJpaEntity): Popup {

            /*if (entity.type == SlideType.EVENT && entity.linkUrl.contains("eventsId")){
                entity.type = SlideType.WEB_VIEW
//                entity.linkUrl = entity.linkUrl + "&id=" +
            }*/


            return Popup(
                id = entity.id ?: 0, //접근제한자 때문에 여기에 둘 수밖에 없구나..?
                type = entity.type,
                webViewTitle = entity.webViewTitle.orEmpty(),
                linkUrl = entity.linkUrl.orEmpty(),
                mainImage = null,
                subImage = null,
                popupImage = SlideSimpleImageResponse(0L, entity.urlSetting.orEmpty(), ""),

                numOrder = entity.numOrder,
                createdBy = entity.createdBy ?: 0,

                startAt = formattingLegacyDate(entity.startAt),
                endAt = formattingLegacyDate(entity.endAt),

                position = entity.position ?: SlidePosition.MAIN,
                exposure = entity.exposure,
                eventsId = entity.eventsId,
                status = entity.status,
                createdAt = formattingLegacyDate(entity.createdAt),
                updatedAt = formattingLegacyDate(entity.updatedAt),
            )
        }
        fun mapToBanner(entity: SlideJpaEntity): Banner {

            /*if (entity.type == SlideType.EVENT && entity.linkUrl.contains("eventsId")){
                entity.type = SlideType.WEB_VIEW
//                entity.linkUrl = entity.linkUrl + "&id=" +
            }*/


            return Banner(
                id = entity.id ?: 0, //접근제한자 때문에 여기에 둘 수밖에 없구나..?
                type = entity.type,
                webViewTitle = entity.webViewTitle.orEmpty(),
                linkUrl = entity.linkUrl.orEmpty(),
                mainImage = SlideSimpleImageResponse(0L, entity.urlSetting.orEmpty(), ""),
                subImage = SlideSimpleImageResponse(0L, entity.urlSetting.orEmpty(), ""),
                popupImage = null,

                numOrder = entity.numOrder,
                createdBy = entity.createdBy ?: 0,

                startAt = formattingLegacyDate(entity.startAt),
                endAt = formattingLegacyDate(entity.endAt),

                position = entity.position ?: SlidePosition.MAIN,
                exposure = entity.exposure,
                eventsId = entity.eventsId,
                status = entity.status,
                createdAt = formattingLegacyDate(entity.createdAt),
                updatedAt = formattingLegacyDate(entity.updatedAt),
            )
        }
    }


    enum class SlideType {
        URL, WEB_VIEW,

        //    BANNER_WEB_VIEW, BANNER_DECORATION_CHANLLENGE,
        EVENT, CHALLENGE,  //    SHOP_MAIN, POST_GOODS
    }
}
