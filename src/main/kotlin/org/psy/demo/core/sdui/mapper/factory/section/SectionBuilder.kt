package org.psy.demo.core.sdui.mapper.factory.section

import org.psy.demo.core.sdui.mapper.compositor.ProductItemCompositor
import org.psy.demo.core.sdui.mapper.factory.product.ProductCompositorFactory
import org.psy.demo.app.sdui.translator.Tabs
import org.psy.demo.app.sdui.translator.container.HeaderUI
import org.psy.demo.app.sdui.translator.container.Section
import org.psy.demo.app.sdui.translator.container.SectionType
import org.psy.demo.core.user.domain.User
import org.psy.demo.common.exception.NOT_AVALIVABLE_DATA
import org.psy.demo.core.domain.Banner
import org.psy.demo.core.goods.domain.Goods
import org.psy.demo.core.post.domain.Story
import org.psy.demo.core.post.domain.Post
import org.psy.demo.core.sticker.domain.StickerCalendar
import org.psy.demo.core.user.domain.Badge

object SectionBuilder {
    fun <T> createSection(
        entities: List<T>,
        sectionType: SectionType,
        title: String? = null,
        button: Boolean = false
    ): Section<ProductItemCompositor> = Section(
        id = NOT_AVALIVABLE_DATA,
        type = sectionType,
        headers = HeaderUI(title = title, button = button),
        items = entities.map { createProductItemCompositor(it, sectionType) }
    )
    private fun <T> createProductItemCompositor(entity: T, sectionType: SectionType): ProductItemCompositor =
        when (entity) {
            is Tabs -> ProductCompositorFactory.tabs(entity)
            is Banner -> ProductCompositorFactory.banners(entity)
            is Post -> createPostCompositor(entity, sectionType)
            is StickerCalendar -> ProductCompositorFactory.sticker(entity)
            is User -> ProductCompositorFactory.profile(entity)
            is Badge -> ProductCompositorFactory.badge(entity)
            is Story -> ProductCompositorFactory.storys(entity)
            is Goods -> ProductCompositorFactory.goodsWithPrice(entity)
            else -> throw IllegalArgumentException("Unsupported entity type: ${entity?.javaClass?.simpleName}")
        }

    private fun createPostCompositor(post: Post, sectionType: SectionType): ProductItemCompositor =
        when (sectionType) {
            SectionType.H_CURATION_IMAGE_ROW, SectionType.H_CURATION_IMAGE_TEXT_ROW ->
                ProductCompositorFactory.postsCuration(post)
            else -> ProductCompositorFactory.posts(post)
        }

    // Fallback function for any other type
    private fun Any.toProductItemCompositor(sectionType: SectionType): ProductItemCompositor =
        throw IllegalArgumentException("Unsupported entity type: ${this::class.simpleName}")
}


/*
모든 entity가 모든 sectionType을 다 받게끔 코드상에는 설계 되어있는데 실제로 그렇게 사용하지는 않아서 제약사항을 둔다면? 하고 물어봤을 때
object SectionFactory {
    // 각 엔티티 타입에 대해 허용된 섹션 타입을 정의
    private val allowedSectionTypes = mapOf(
        Post::class to setOf(SectionType.V_CONTENT_IMAGE_GRID, SectionType.V_CONTENT_IMAGE_TEXT_LIST,
            SectionType.H_CURATION_IMAGE_ROW, SectionType.H_CURATION_IMAGE_TEXT_ROW),
        Banner::class to setOf(SectionType.H_CURATION_IMAGE_ROW),
        Tabs::class to setOf(SectionType.V_CONTENT_IMAGE_GRID),
        StickerCalendar::class to setOf(SectionType.V_CONTENT_IMAGE_GRID),
        User::class to setOf(SectionType.V_CONTENT_IMAGE_TEXT_LIST),
        Badge::class to setOf(SectionType.V_CONTENT_IMAGE_GRID),
        Story::class to setOf(SectionType.H_CURATION_IMAGE_TEXT_ROW)
    )

    // 빌더 클래스 정의
    class SectionBuilder<T : Any> private constructor(private val entityType: Class<T>) {
        private var entities: List<T>? = null
        private var sectionType: SectionType? = null
        private var title: String? = null
        private var button: Boolean = false

        fun setEntities(entities: List<T>) = apply { this.entities = entities }
        fun setSectionType(sectionType: SectionType) = apply {
            require(allowedSectionTypes[entityType.kotlin]?.contains(sectionType) == true) {
                "Invalid section type ${sectionType} for entity type ${entityType.simpleName}"
            }
            this.sectionType = sectionType
        }
        fun setTitle(title: String?) = apply { this.title = title }
        fun setButton(button: Boolean) = apply { this.button = button }

        fun build(): Section<ProductItemCompositor> {
            require(entities != null) { "Entities must be set" }
            require(sectionType != null) { "Section type must be set" }

            return Section(
                id = NOT_AVAILABLE_DATA,
                type = sectionType!!,
                headers = HeaderUI(title = title, button = button),
                items = entities!!.map { it.toProductItemCompositor(sectionType!!) }
            )
        }

        companion object {
            fun <T : Any> forType(type: Class<T>): SectionBuilder<T> = SectionBuilder(type)
        }
    }

    private fun Post.toProductItemCompositor(sectionType: SectionType): ProductItemCompositor =
        when (sectionType) {
            SectionType.H_CURATION_IMAGE_ROW, SectionType.H_CURATION_IMAGE_TEXT_ROW ->
                ProductCompositorFactory.postsCuration(this)
            else -> ProductCompositorFactory.posts(this)
        }

    private fun Banner.toProductItemCompositor(sectionType: SectionType): ProductItemCompositor =
        ProductCompositorFactory.banners(this)

    private fun Tabs.toProductItemCompositor(sectionType: SectionType): ProductItemCompositor =
        ProductCompositorFactory.tabs(this)

    private fun StickerCalendar.toProductItemCompositor(sectionType: SectionType): ProductItemCompositor =
        ProductCompositorFactory.sticker(this)

    private fun User.toProductItemCompositor(sectionType: SectionType): ProductItemCompositor =
        ProductCompositorFactory.profile(this)

    private fun Badge.toProductItemCompositor(sectionType: SectionType): ProductItemCompositor =
        ProductCompositorFactory.badge(this)

    private fun Story.toProductItemCompositor(sectionType: SectionType): ProductItemCompositor =
        ProductCompositorFactory.storys(this)
}

// Usage example
class CommunityUIService(private val getPostUseCase: GetPostUseCase) {
    fun getCommunityUIContents(request: GetCommunityUIContentsQuery): Section<ProductItemCompositor> {
        val posts: List<Post> = getPostUseCase.getCommunityContents(request)
        return SectionFactory.SectionBuilder.forType(Post::class.java)
            .setEntities(posts)
            .setSectionType(when (request.tabId) {
                TabId.FEED -> SectionType.V_CONTENT_IMAGE_GRID
                TabId.ETC -> SectionType.V_CONTENT_IMAGE_TEXT_LIST
                else -> SectionType.V_CONTENT_IMAGE_GRID
            })
            .setTitle("Community Posts")
            .build()
    }
}*/
