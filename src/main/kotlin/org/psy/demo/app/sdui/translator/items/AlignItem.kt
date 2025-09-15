package org.psy.demo.app.sdui.translator.items

import com.fasterxml.jackson.annotation.JsonProperty
import org.psy.demo.app.sdui.translator.container.CommunityUIOrdering

data class AlignItem(
    @JsonProperty("searchKey")
    val searchKey: CommunityUIOrdering,
    @JsonProperty("title")
    val title: String,
)

object AlignItems {
    fun feedItems() : List<AlignItem> {
        return listOf(
            AlignItem(CommunityUIOrdering.NEW, "최신순"),
            AlignItem(CommunityUIOrdering.POPULAR, "인기순"),
            AlignItem(CommunityUIOrdering.FOLLOWING, "팔로잉"),
        )
    }
    fun etcItems() : List<AlignItem> {
        return listOf(
            AlignItem(CommunityUIOrdering.NEW, "최신순"),
            AlignItem(CommunityUIOrdering.POPULAR, "인기순")
        )
    }
}
