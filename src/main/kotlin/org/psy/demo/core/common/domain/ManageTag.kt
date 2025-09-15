package org.psy.demo.core.common.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class ManageTag(
//        var id: ManageTagId?,
        @JsonProperty("id")
        var id: String?,
        @JsonProperty("tagName")
        var tagName: String
) {
//    data class ManageTagId(val number: Long)

    /*fun ManageTag (id: ManageTagId, tagName: String) {
        this.id = id
        this.tagName = tagName
    }*/

}
