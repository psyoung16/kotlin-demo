package org.psy.demo.core.sdui.facade

import org.psy.demo.common.PageParam
import org.psy.demo.common.exception.NOT_AVALIVABLE_DATA
import org.psy.demo.common.postInfoIdsByFeed
import org.psy.demo.infra.jpaEntity.StyleTagType
import org.psy.demo.app.main.usecase.GetPostTagUseCase
import org.psy.demo.core.sdui.mapper.compositor.ProductItemCompositor
import org.psy.demo.app.sdui.usecase.GetSearchUIUseCase
import org.psy.demo.app.sdui.translator.container.Section
import org.psy.demo.core.user.domain.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.psy.demo.app.main.usecase.GetPostUseCase
import org.psy.demo.core.sdui.mapper.factory.product.ProductCompositorFactory
import org.psy.demo.app.sdui.translator.container.HeaderUI
import org.psy.demo.app.sdui.translator.container.SectionType
import org.psy.demo.core.post.domain.Post


@Service
@Transactional(readOnly = true)
class GetSearchUIFacade (
    private val getPostUseCase: GetPostUseCase,
    private val getPostTagUseCase: GetPostTagUseCase,

) :GetSearchUIUseCase {

    //태그 기반 검색
    override fun getTagSearchResult(
        userId: User.UserId,
        searchTag: String,
        pageParam: PageParam
    ): Section<ProductItemCompositor> {

        val postsIds : List<Post.PostId> = getPostTagUseCase.getPostIdsByPostTag(searchTag)

        val posts : List<Post> = getPostUseCase.getPostsWithPostInteractionWithPostCreatedInfo(
            requestUserId = userId,
            page = pageParam,
            postInfoIds = postInfoIdsByFeed,
            postIds = postsIds
        )

        return createSectionBySearchUI(
            entities = posts,
            sectionType = SectionType.V_CONTENT_IMAGE_TEXT_LIST,
        )
    }

    override fun getStyleTagSearchResult(
        userId: User.UserId,
        searchTag: StyleTagType,
        pageParam: PageParam
    ): Section<ProductItemCompositor> {

        val posts : List<Post> = getPostUseCase.getStyleTagPostWithPostInteractionWithPostCreatedInfo(
            requestUserId = userId,
            page = pageParam,
            styleTag = searchTag
        )

        return createSectionBySearchUI(
            entities = posts,
            sectionType = SectionType.V_CONTENT_IMAGE_TEXT_LIST,
        )
    }


}



fun <T> createSectionBySearchUI(
    entities: List<T>,
    sectionType: SectionType,
    title: String? = null,
    button: Boolean? = null
): Section<ProductItemCompositor> {
    return Section(
        id = NOT_AVALIVABLE_DATA,
        type = sectionType,
        headers = HeaderUI(title = title, button = button == true),
        items = entities.map { entity ->
            when {
                entity is Post -> ProductCompositorFactory.posts(entity)
                else -> throw IllegalArgumentException("Unsupported entity type")
            }
        }
    )
}
