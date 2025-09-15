package org.psy.demo.core.sdui.mapper.factory.product

import org.psy.demo.core.sdui.mapper.compositor.ProductItemCompositor
import org.psy.demo.core.sdui.mapper.factory.ItemCompositorFactory
import org.psy.demo.app.sdui.translator.Tabs
import org.psy.demo.core.content.domain.Banner
import org.psy.demo.core.goods.domain.Goods
import org.psy.demo.core.post.domain.Post
import org.psy.demo.core.post.domain.Story
import org.psy.demo.core.sticker.domain.StickerCalendar
import org.psy.demo.core.user.domain.Badge
import org.psy.demo.core.user.domain.User


class ProductCompositorFactory {


    companion object {
        val profile: ItemCompositorFactory<User, ProductItemCompositor> = { product ->
            ProductItemCompositor(
                base = UserMixinMapper.baseMixin(product),
                supporting = UserMixinMapper.supportingMixin(product)
            )
        }
        val sticker: ItemCompositorFactory<StickerCalendar, ProductItemCompositor> = { product ->
            ProductItemCompositor(
                base = StickerMixinMapper.baseMixin(product),
                supporting = StickerMixinMapper.supportingMixin(product)
            )
        }
        val posts: ItemCompositorFactory<Post, ProductItemCompositor> = { product ->
            ProductItemCompositor(
                base = PostMixinMapper.baseMixin(product),
                supporting = PostMixinMapper.supportingMixin(product)
            )
        }
        val goodsWithPrice: ItemCompositorFactory<Goods, ProductItemCompositor> = { product ->
            ProductItemCompositor(
                base = GoodsMixinMapper.baseMixin(product),
                supporting = GoodsMixinMapper.supportingMixin(product)
            )
        }
        val postsCuration: ItemCompositorFactory<Post, ProductItemCompositor> = { product ->
            ProductItemCompositor(
                base = CurationMixinMapper.baseMixin(product),
                supporting = CurationMixinMapper.supportingMixin(product)
            )
        }
        val badge: ItemCompositorFactory<Badge, ProductItemCompositor> = { product ->
            ProductItemCompositor(
                base = BadgeMixinMapper.baseMixin(product),
                supporting = BadgeMixinMapper.supportingMixin(product)
            )
        }
        val storys: ItemCompositorFactory<Story, ProductItemCompositor> = { product ->
            ProductItemCompositor(
                base = StoryMixinMapper.baseMixin(product),
                supporting = StoryMixinMapper.supportingMixin(product)
            )
        }
        val tabs: ItemCompositorFactory<Tabs, ProductItemCompositor> = { product ->
            ProductItemCompositor(
                base = TabsMixinMapper.baseMixin(product),
                supporting = TabsMixinMapper.supportingMixin(product)
            )
        }
        val banners: ItemCompositorFactory<Banner, ProductItemCompositor> = { product ->
            ProductItemCompositor(
                base = BannerMixinMapper.baseMixin(product),
                supporting = BannerMixinMapper.supportingMixin(product)
            )
        }

    }

}


