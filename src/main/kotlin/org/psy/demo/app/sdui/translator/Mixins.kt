package org.psy.demo.app.sdui.translator

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonUnwrapped

data class BaseMixin(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("imageUrl")
    val imageUrl: String?,
)

data class SupportingMixin<T>(
    @JsonUnwrapped
    val supportingMixin: T
)
