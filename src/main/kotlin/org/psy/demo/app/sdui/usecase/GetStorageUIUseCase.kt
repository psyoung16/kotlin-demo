package org.psy.demo.app.sdui.usecase

import org.psy.demo.common.PageParam
import org.psy.demo.core.sdui.mapper.compositor.ProductItemCompositor
import org.psy.demo.app.sdui.translator.Tabs
import org.psy.demo.app.sdui.translator.container.Section
import org.psy.demo.core.user.domain.User

interface GetStorageUIUseCase {

    fun getLikeStorageUseCase(
        userId: User.UserId,
        pageParam: PageParam
    ) : Section<ProductItemCompositor>

    fun getScrapStorageUseCase(
        userId: User.UserId,
        pageParam: PageParam
    ) : Section<ProductItemCompositor>


    fun getWishStorageUseCase(
        userId: User.UserId,
        pageParam: PageParam
    ) : Section<ProductItemCompositor>

    fun getStorageItems(
        userId: User.UserId,
        tabId: Tabs.TAB_ID,
        pageParam: PageParam
    ) : Section<ProductItemCompositor>

    fun getStoargeTabs(
        userId: User.UserId
    ) :  Section<ProductItemCompositor>

}
