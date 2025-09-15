package org.psy.demo.app.sdui.translator.container

import com.fasterxml.jackson.annotation.JsonProperty


data class Section<T>(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("type")
    val type: SectionType,
    @JsonProperty("headers")
    val headers: HeaderUI?,
    @JsonProperty("items")
    val items: List<T>
)


data class HeaderUI(
    @JsonProperty("title")
    val title: String?,
    @JsonProperty("button")
    val button: Boolean = false
)

//https://kyobobook.atlassian.net/wiki/spaces/DeptPlatformBizDev/pages/974422182
enum class SectionType {
    NONE,

    //community
    H_TAB_UNDERLINE_ROW,
    H_BANNER_IMAGE_CAROUSEL,
    H_CURATION_IMAGE_ROW,
    H_CURATION_IMAGE_TEXT_ROW,
    H_FILTER_CONTENT,
    V_CONTENT_IMAGE_GRID, //이미지만
    V_CONTENT_IMAGE_TEXT_LIST, //이미지 + 텍스트
    V_CONTENT_MIX_LIST, // 이미지 + 텍스트 + 기타 댓글 아이콘 등등..
    V_CONTENT_IMAGE_PRICE_GRID, //찜한 상품

    //mykkomming
    H_TAB_UNDERLINE_FULL_ROW, //게시물 탭 전체
    H_CURATION_IMAGE_BUTTON_ROW, //마이꾸밍 main에 사용
    H_CURATION_IMAGE_TEXT_TOOLTIP_ROW, //마이꾸밍 main에 사용 > 뱃지
    H_CURATION_PLUS_IMAGE_TEXT_TOOLTIP_ROW, //마이꾸밍 > 피드 story 사용
    H_CURATION_IMAGE_TEXT_BUTTON_ROW //마이꾸밍 main에 사용



}

enum class CommunityUIOrdering {
    NEW, //최신순
    POPULAR, //인기순
    FOLLOWING, //팔로잉
}

enum class CommunityUIFilterType {
    V_CONTENT_IMAGE_GRID,
    V_CONTENT_IMAGE_TEXT_LIST,
    V_CONTENT_MIX_LIST
}
