package org.psy.demo.app.sdui.translator.container

import com.fasterxml.jackson.annotation.JsonProperty
import org.psy.demo.app.sdui.translator.items.AlignItem

//filter들은 각각에 종속

data class FilterContainer (
    @JsonProperty("id")
    val id: String,
    @JsonProperty("layoutIcon")
    val layoutIcon: List<SectionType>,
    @JsonProperty("aligns")
    val aligns: List<AlignItem>,
    @JsonProperty("tags")
    val tags: List<FilterTag>
)

data class FilterTag (
    @JsonProperty("type")
    val type: FilterTagType,
    @JsonProperty("searchKey")
    val searchKey: String,
    @JsonProperty("headers")
    val headers: HeaderUI,
    @JsonProperty("items")
    val items: List<FilterItem>
)

data class FilterItem (
    @JsonProperty("id")
    val id: String,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("imageUrl")
    val imageUrl: String?
)


enum class FilterTagType {
    TAG, STYLE_TAG
}


object FilterItems {

    fun styleItems() : List<FilterItem> = listOf(
        FilterItem(
            id = "KITCH",
            title = "키치",
            imageUrl = "https://dftgic7kmxqhe.cloudfront.net/COMMON/ic_style_kitch.png"
        ),
        FilterItem(
            id = "LOVELY_CUTE",
            title = "러블리큐트",
            imageUrl = "https://dftgic7kmxqhe.cloudfront.net/COMMON/ic_style_lovely.png"
        ),
        FilterItem(
            id = "CHIC",
            title = "시크",
            imageUrl = "https://dftgic7kmxqhe.cloudfront.net/COMMON/ic_style_chic.png"
        ),
        FilterItem(
            id = "EMOTION",
            title = "감성",
            imageUrl = "https://dftgic7kmxqhe.cloudfront.net/COMMON/ic_style_emotion.png"
        ),
        FilterItem(
            id = "DREAM",
            title = "몽환",
            imageUrl = "https://dftgic7kmxqhe.cloudfront.net/COMMON/ic_style_dreamy.png"
        ),
        FilterItem(
            id = "VINTAGE",
            title = "빈티지",
            imageUrl = "https://dftgic7kmxqhe.cloudfront.net/COMMON/ic_style_vintage.png"
        ),
        FilterItem(
            id = "NATURAL",
            title = "자연",
            imageUrl = "https://dftgic7kmxqhe.cloudfront.net/COMMON/ic_style_nature.png"
        ),
        FilterItem(
            id = "ANIMAL",
            title = "동물",
            imageUrl = "https://dftgic7kmxqhe.cloudfront.net/COMMON/ic_style_animal.png"
        ),
        FilterItem(
            id = "TYPO",
            title = "타이포",
            imageUrl = "https://dftgic7kmxqhe.cloudfront.net/COMMON/ic_style_typo.png"
        ),
        FilterItem(
            id = "SIMPLE",
            title = "심플",
            imageUrl = "https://dftgic7kmxqhe.cloudfront.net/COMMON/ic_style_simple.png"
        )
    )
}
