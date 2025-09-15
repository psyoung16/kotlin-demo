package org.psy.demo.core.sdui.mapper.compositor

import com.fasterxml.jackson.annotation.JsonUnwrapped
import org.psy.demo.app.sdui.translator.BaseMixin
import org.psy.demo.app.sdui.translator.SupportingMixin

data class ProductItemCompositor(
    @JsonUnwrapped
    val base: BaseMixin,
    @JsonUnwrapped
    val supporting: SupportingMixin<*>
) {
}
