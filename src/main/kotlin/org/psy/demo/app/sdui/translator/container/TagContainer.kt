package org.psy.demo.app.sdui.translator.container

import org.psy.demo.app.sdui.translator.items.TagItem

data class TagsContainer(
    val type: FilterTagType,
    val searchKey: String,
    val headers: HeaderUI,
    val items: List<TagItem>
)

