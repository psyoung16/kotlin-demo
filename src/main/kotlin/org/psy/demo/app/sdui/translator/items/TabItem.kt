package org.psy.demo.app.sdui.translator.items

import com.fasterxml.jackson.annotation.JsonProperty

data class TabItem(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("title")
    val title: String,
)


//db 연결 안하기 떄문에 임의 세팅
/*
object CommunityUIConfig {
    private val configMap = mapOf(
        "FEED" to CommunityUIInfo(
            tabId = "FEED",
            tabTitle = "피드",
            curationTitle = "유저들이 좋아하는 콘텐츠",
            curationType = SectionType.H_CURATION_IMAGE_ROW,
            filterContentLayouts = listOf( SectionType.V_CONTENT_IMAGE_GRID,SectionType.V_CONTENT_IMAGE_TEXT_LIST), //여기 있는 first index와 contents 조회했을 때 index 맞춰야함
            filterAligns = listOf(
                AlignItem(CommunityUIOrdering.NEW, "최신순"),
                AlignItem(CommunityUIOrdering.POPULAR, "인기순"),
                AlignItem(CommunityUIOrdering.FOLLOWING, "팔로잉")
            ),
        ),
        "ETC" to CommunityUIInfo(
            tabId = "ETC",
            tabTitle = "잡담",
            curationTitle = "지금 뜨고 있는 글",
            curationType = SectionType.H_CURATION_IMAGE_TEXT_ROW,
            filterContentLayouts = listOf(SectionType.V_CONTENT_MIX_LIST),
            filterAligns = listOf(
                AlignItem(CommunityUIOrdering.NEW, "최신순"), AlignItem(CommunityUIOrdering.POPULAR, "인기순")
            )
        )
    )

    fun info(tabId: String): CommunityUIInfo {
        return configMap[tabId] ?: CommunityUIInfo("UNKNOWN", "알 수 없음", "알 수 없음", SectionType.NONE, listOf(SectionType.NONE) )
    }
}
*/

/*data class CommunityUIInfo(
    @JsonProperty("tabId")
    val tabId: String,
    @JsonProperty("tabTitle")
    val tabTitle: String,

    @JsonProperty("curationTitle")
    val curationTitle: String,
    @JsonProperty("curationType")
    val curationType: SectionType,

    @JsonProperty("filterContentLayouts")
    val filterContentLayouts: List<SectionType>, //하단 content에서 먼저 보여줄 layout type
    @JsonProperty("filterAligns")
    val filterAligns: List<AlignItem> = listOf(),
//    val tags: List<TagsContainer> = listOf(),
//    val filterLayout:  //하단 content에서 먼저 보여줄 layout type
)*/
