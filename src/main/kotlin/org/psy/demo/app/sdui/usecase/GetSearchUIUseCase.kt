package org.psy.demo.app.sdui.usecase

import org.psy.demo.common.PageParam
import org.psy.demo.infra.jpaEntity.StyleTagType
import org.psy.demo.core.sdui.mapper.compositor.ProductItemCompositor
import org.psy.demo.app.sdui.translator.container.Section
import org.psy.demo.core.user.domain.User

interface GetSearchUIUseCase {

    fun getTagSearchResult(userId : User.UserId, searchTag: String, pageParam: PageParam) : Section<ProductItemCompositor>
    fun getStyleTagSearchResult(userId : User.UserId, searchTag: StyleTagType, pageParam: PageParam) : Section<ProductItemCompositor>




}
