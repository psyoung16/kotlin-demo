package org.psy.demo.common

import org.psy.demo.common.exception.NOT_AVALIVABLE_DATA
import org.psy.demo.app.sdui.translator.BaseMixin
import org.psy.demo.app.sdui.translator.SupportingMixin
import org.psy.demo.core.sdui.mapper.compositor.ProductItemCompositor
import org.psy.demo.core.sdui.mapper.factory.product.BannerMixinMapper
import org.psy.demo.core.sdui.mapper.factory.product.GoodsMixinMapper
import org.psy.demo.core.sdui.mapper.factory.product.GoodsMixinMapper.GoodsItemSupporting
import org.psy.demo.core.sdui.mapper.factory.product.PostMixinMapper
import org.psy.demo.core.sdui.mapper.factory.product.StickerMixinMapper.IsHasSticker
import org.psy.demo.core.sdui.mapper.factory.product.UserMixinMapper.UserInsightWithFollowed
import org.psy.demo.app.sdui.translator.container.FilterContainer
import org.psy.demo.app.sdui.translator.container.FilterItem
import org.psy.demo.app.sdui.translator.container.FilterTag
import org.psy.demo.app.sdui.translator.container.FilterTagType
import org.psy.demo.app.sdui.translator.Tabs
import org.psy.demo.app.sdui.translator.container.CommunityUIOrdering
import org.psy.demo.app.sdui.translator.container.HeaderUI
import org.psy.demo.app.sdui.translator.container.Section
import org.psy.demo.app.sdui.translator.container.SectionType
import org.psy.demo.app.sdui.translator.items.AlignItem
import org.psy.demo.app.sticker.usecase.GetManageTagByStickerResponse
import org.psy.demo.core.common.domain.ManageTag
import org.psy.demo.core.goods.domain.Goods
import org.psy.demo.core.sticker.domain.StickerCalendar
import org.psy.demo.core.sticker.domain.UserSticker
import java.time.LocalDate

object MockDataSetting {

    fun getMockProductCompositorByProfile(): ProductItemCompositor {
        return ProductItemCompositor(
            base = BaseMixin(
                id = "1243",
                title = "무민잉",
                imageUrl = "https://d16sraug4hsad1.cloudfront.net/PROFILE/efcf9e1d-a2be-4625-8b9a-b7af57fc1667.jpeg",
            ),
            supporting = SupportingMixin(
                UserInsightWithFollowed(
                    totalFollower = 35,
                    totalFollowing = 20,
                    totalReported = 10,
                    bio = "안녕하세요 무민이입니다",
                    isFollowed = null
                )
            )
        )
    }

    fun getMockProductCompositorBySticker(): ProductItemCompositor {
        return ProductItemCompositor(
            base = BaseMixin(
                id = "_N/A_",
                title = "오늘 하루는 어땠나요?",
                imageUrl = "https://d16sraug4hsad1.cloudfront.net/PROFILE/none_sticker_image_1.png",
            ),
            supporting = SupportingMixin(
                IsHasSticker(
                    time = "2024.06.07",
                    isHasSticker = false
                )
            )
        )
    }

    fun getMockProductCompositorByFeed(): Section<ProductItemCompositor> {
        return Section(
            id = NOT_AVALIVABLE_DATA,
            type = SectionType.H_CURATION_IMAGE_BUTTON_ROW,
            headers = HeaderUI(
                title = "최신 업로드한 피드",
                button = false
            ),
            items = listOf(
                ProductItemCompositor(
                    base = BaseMixin(
                        id = "59926",
                        title = "제목",
                        imageUrl = "https://d16sraug4hsad1.cloudfront.net/PROFILE/efcf9e1d-a2be-4625-8b9a-b7af57fc1667.jpeg",
                    ),
                    supporting = SupportingMixin(
                        PostMixinMapper.PostItemSupporting(
                            description = "내용",
                            category = "POST_GOODS",
                            time = "2021.06.07",
                            profileName = "무민잉",
                            profileImageUrl = "https://d16sraug4hsad1.cloudfront.net/PROFILE/efcf9e1d-a2be-4625-8b9a-b7af57fc1667.jpeg",
                            isLike = false,
                            isScrap = false
                        )
                    )
                ),
                ProductItemCompositor(
                    base = BaseMixin(
                        id = "59926",
                        title = "제목",
                        imageUrl = "https://d16sraug4hsad1.cloudfront.net/PROFILE/efcf9e1d-a2be-4625-8b9a-b7af57fc1667.jpeg",
                    ),
                    supporting = SupportingMixin(
                        PostMixinMapper.PostItemSupporting(
                            description = "내용",
                            category = "FEED",
                            time = "2021.06.07",
                            profileName = "무민잉",
                            profileImageUrl = "https://d16sraug4hsad1.cloudfront.net/PROFILE/efcf9e1d-a2be-4625-8b9a-b7af57fc1667.jpeg",
                            isLike = false,
                            isScrap = false
                        )
                    )
                ),
                ProductItemCompositor(
                    base = BaseMixin(
                        id = "59926",
                        title = "제목",
                        imageUrl = "https://d16sraug4hsad1.cloudfront.net/PROFILE/efcf9e1d-a2be-4625-8b9a-b7af57fc1667.jpeg",
                    ),
                    supporting = SupportingMixin(
                        PostMixinMapper.PostItemSupporting(
                            description = "내용",
                            category = "STAR",
                            time = "2021.06.07",
                            profileName = "무민잉",
                            profileImageUrl = "https://d16sraug4hsad1.cloudfront.net/PROFILE/efcf9e1d-a2be-4625-8b9a-b7af57fc1667.jpeg",
                            isLike = false,
                            isScrap = false
                        )
                    )
                )
            )
        )
    }

    fun getMockProductCompositorByPost(): Section<ProductItemCompositor> {
        return Section(
            id = NOT_AVALIVABLE_DATA,
            type = SectionType.H_CURATION_IMAGE_BUTTON_ROW,
            headers = HeaderUI(
                title = "최신 업로드한 게시물",
                button = false
            ),
            items = listOf(
                ProductItemCompositor(
                    base = BaseMixin(
                        id = "59926",
                        title = "제목",
                        imageUrl = "https://d16sraug4hsad1.cloudfront.net/PROFILE/efcf9e1d-a2be-4625-8b9a-b7af57fc1667.jpeg",
                    ),
                    supporting = SupportingMixin(
                        PostMixinMapper.PostItemSupporting(
                            description = "내용",
                            category = "VOTE",
                            time = "2021.06.07",
                            profileName = "무민잉",
                            profileImageUrl = "https://d16sraug4hsad1.cloudfront.net/PROFILE/efcf9e1d-a2be-4625-8b9a-b7af57fc1667.jpeg",
                            isLike = false,
                            isScrap = false
                        )
                    )
                ),
                ProductItemCompositor(
                    base = BaseMixin(
                        id = "59926",
                        title = "제목",
                        imageUrl = "https://d16sraug4hsad1.cloudfront.net/PROFILE/efcf9e1d-a2be-4625-8b9a-b7af57fc1667.jpeg",
                    ),
                    supporting = SupportingMixin(
                        PostMixinMapper.PostItemSupporting(
                            description = "내용",
                            category = "ETC",
                            time = "2021.06.07",
                            profileName = "무민잉",
                            profileImageUrl = "https://d16sraug4hsad1.cloudfront.net/PROFILE/efcf9e1d-a2be-4625-8b9a-b7af57fc1667.jpeg",
                            isLike = false,
                            isScrap = false
                        )
                    )
                )
            )
        )
    }

    fun getMockProductCompositorByStory(): Section<ProductItemCompositor> {
        return Section(
            id = NOT_AVALIVABLE_DATA,
            type = SectionType.H_CURATION_PLUS_IMAGE_TEXT_TOOLTIP_ROW,
            headers = HeaderUI(
                title = "내 스토리",
                button = true
            ),
            items = listOf(
                ProductItemCompositor(
                    base = BaseMixin(
                        id = "20",
                        title = "게시글 제목",
                        imageUrl = "https://d16sraug4hsad1.cloudfront.net/PROFILE/efcf9e1d-a2be-4625-8b9a-b7af57fc1667.jpeg",
                    ),
                    supporting = SupportingMixin(
                        Unit
                    )
                ),
                ProductItemCompositor(
                    base = BaseMixin(
                        id = "20",
                        title = "게시글 제목",
                        imageUrl = "https://d16sraug4hsad1.cloudfront.net/PROFILE/efcf9e1d-a2be-4625-8b9a-b7af57fc1667.jpeg",
                    ),
                    supporting = SupportingMixin(
                        Unit
                    )
                ),
                ProductItemCompositor(
                    base = BaseMixin(
                        id = "20",
                        title = "게시글 제목",
                        imageUrl = "https://d16sraug4hsad1.cloudfront.net/PROFILE/efcf9e1d-a2be-4625-8b9a-b7af57fc1667.jpeg",
                    ),
                    supporting = SupportingMixin(
                        Unit
                    )
                ),
            )
        )
    }

    fun get(): Section<ProductItemCompositor> {
        return Section(
            id = NOT_AVALIVABLE_DATA,
            type = SectionType.H_CURATION_IMAGE_BUTTON_ROW,
            headers = HeaderUI(
                title = "최신 업로드한 게시물",
                button = false
            ),
            items = listOf(
                ProductItemCompositor(
                    base = BaseMixin(
                        id = "59926",
                        title = "제목",
                        imageUrl = "https://d16sraug4hsad1.cloudfront.net/PROFILE/efcf9e1d-a2be-4625-8b9a-b7af57fc1667.jpeg",
                    ),
                    supporting = SupportingMixin(
                        PostMixinMapper.PostItemSupporting(
                            description = "내용",
                            category = "VOTE",
                            time = "2021.06.07",
                            profileName = "무민잉",
                            profileImageUrl = "https://d16sraug4hsad1.cloudfront.net/PROFILE/efcf9e1d-a2be-4625-8b9a-b7af57fc1667.jpeg",
                            isLike = false,
                            isScrap = false
                        )
                    )
                ),
                ProductItemCompositor(
                    base = BaseMixin(
                        id = "59926",
                        title = "제목",
                        imageUrl = "https://d16sraug4hsad1.cloudfront.net/PROFILE/efcf9e1d-a2be-4625-8b9a-b7af57fc1667.jpeg",
                    ),
                    supporting = SupportingMixin(
                        PostMixinMapper.PostItemSupporting(
                            description = "내용",
                            category = "ETC",
                            time = "2021.06.07",
                            profileName = "무민잉",
                            profileImageUrl = "https://d16sraug4hsad1.cloudfront.net/PROFILE/efcf9e1d-a2be-4625-8b9a-b7af57fc1667.jpeg",
                            isLike = false,
                            isScrap = false
                        )
                    )
                )
            )
        )
    }

    fun getMockProductCompositorByBadge(): Section<ProductItemCompositor> {
        return Section(
            id = NOT_AVALIVABLE_DATA,
            type = SectionType.H_CURATION_IMAGE_TEXT_TOOLTIP_ROW,
            headers = HeaderUI(
                title = "꾸밍 배지"
            ),
            items = listOf(
                ProductItemCompositor(
                    base = BaseMixin(
                        id = "123",
                        title = "업로드 1",
                        imageUrl = "https://d16sraug4hsad1.cloudfront.net/PROFILE/bg_upload_1.png",
                    ),
                    supporting = SupportingMixin(Unit)
                )
            )
        )
    }


    fun getMockPostTabs(): Section<ProductItemCompositor> {
        return Section(
            id = NOT_AVALIVABLE_DATA,
            type = SectionType.H_TAB_UNDERLINE_FULL_ROW,
            headers = HeaderUI(
                title = ""
            ),
            items = listOf(
                ProductItemCompositor(
                    base = BaseMixin(
                        id = Tabs.TAB_ID.WRITTEN_POST.name,
                        title = "작성한 글",
                        imageUrl = null,
                    ),
                    supporting = SupportingMixin(Unit)
                ),
                ProductItemCompositor(
                    base = BaseMixin(
                        id = Tabs.TAB_ID.RECENT_COMMENT.name,
                        title = "댓글단 글",
                        imageUrl = null,
                    ),
                    supporting = SupportingMixin(Unit)
                )
            )
        )
    }


    fun getMockStorageTabs(): Section<ProductItemCompositor> {
        return Section(
            id = NOT_AVALIVABLE_DATA,
            type = SectionType.H_TAB_UNDERLINE_FULL_ROW,
            headers = HeaderUI(
                title = ""
            ),
            items = listOf(
                ProductItemCompositor(
                    base = BaseMixin(
                        id = Tabs.TAB_ID.GOODS_WISH.name,
                        title = "찜한 상품",
                        imageUrl = null,
                    ),
                    supporting = SupportingMixin(Unit)
                ),
                ProductItemCompositor(
                    base = BaseMixin(
                        id = Tabs.TAB_ID.LIKED.name,
                        title = "좋아요",
                        imageUrl = null,
                    ),
                    supporting = SupportingMixin(Unit)
                ),
                ProductItemCompositor(
                    base = BaseMixin(
                        id = Tabs.TAB_ID.SCRAP.name,
                        title = "스크랩",
                        imageUrl = null,
                    ),
                    supporting = SupportingMixin(Unit)
                )
            )
        )
    }

    fun getMockTabProductCompositorByCommunity(): List<ProductItemCompositor> {
        return listOf(
            ProductItemCompositor(
                base = BaseMixin(
                    id = Tabs.TAB_ID.FEED.name,
                    title = "피드",
                    imageUrl = null,
                ),
                supporting = SupportingMixin(Unit)
            ),
            ProductItemCompositor(
                base = BaseMixin(
                    id = Tabs.TAB_ID.ETC.name,
                    title = "잡담",
                    imageUrl = null,
                ),
                supporting = SupportingMixin(Unit)
            ),
        )
    }


    fun getMockBannerProductCompositorByCommunity(): List<ProductItemCompositor> {
        //1개

        return listOf(
            ProductItemCompositor(
                base = BaseMixin(
                    id = "1",
                    title = "럭키데이 당첨자 발표",
                    imageUrl = "https://dftgic7kmxqhe.cloudfront.net/SLIDE/62bda206-9db8-4b51-8faa-8f6f7dbf336f.png",
                ),
                supporting = SupportingMixin(
                    BannerMixinMapper.BannerSubMixin(
                        subId = "362",
                        contentLinkType = "EVENT",
                        url = ""
                    )
                )
            ),
        )
    }

    fun getMockSectionProductCompositorByCuration(): Section<ProductItemCompositor> {
        return Section(
            id = NOT_AVALIVABLE_DATA,
            type = SectionType.H_CURATION_IMAGE_BUTTON_ROW,
            headers = HeaderUI(
                title = "커뮤니티",
                button = false
            ),
            items = listOf(
                ProductItemCompositor(
                    base = BaseMixin(
                        id = "1",
                        title = "럭키데이 당첨자 발표",
                        imageUrl = "https://dftgic7kmxqhe.cloudfront.net/SLIDE/62bda206-9db8-4b51-8faa-8f6f7dbf336f.png",
                    ),
                    supporting = SupportingMixin(
                        BannerMixinMapper.BannerSubMixin(
                            subId = "362",
                            contentLinkType = "EVENT",
                            url = ""
                        )
                    )
                ),
                ProductItemCompositor(
                    base = BaseMixin(
                        id = "2",
                        title = "6월 신규 입점 브랜드",
                        imageUrl = "https://dftgic7kmxqhe.cloudfront.net/SLIDE/bbc8a959-2351-4c16-9b5b-b142a95df134.png",
                    ),
                    supporting = SupportingMixin(
                        BannerMixinMapper.BannerSubMixin(
                            subId = "288",
                            contentLinkType = "EVENT",
                            url = ""
                        )
                    )
                ),
                ProductItemCompositor(
                    base = BaseMixin(
                        id = "3",
                        title = "고객 설문조사",
                        imageUrl = "https://forms.gle/7pVEhJMV4Fua7J6c6",
                    ),
                    supporting = SupportingMixin(
                        BannerMixinMapper.BannerSubMixin(
                            subId = null,
                            contentLinkType = "WEB_VIEW",
                            url = ""
                        )
                    )
                ),
            )
        )
    }

    fun getMockFilterContainer(): FilterContainer {
        return FilterContainer(
            id = NOT_AVALIVABLE_DATA,
            layoutIcon = listOf(SectionType.V_CONTENT_MIX_LIST),
            aligns = listOf(
                AlignItem(CommunityUIOrdering.NEW, "최신순"),
                AlignItem(CommunityUIOrdering.POPULAR, "인기순"),
                AlignItem(CommunityUIOrdering.FOLLOWING, "팔로잉")
            ),
            tags = listOf(
                FilterTag(
                    type = FilterTagType.STYLE_TAG,
                    searchKey = "styleTags",
                    headers = HeaderUI(title = "스타일"),
                    items = listOf(
                        FilterItem(
                            id = "KITCH",
                            title = "키치",
                            imageUrl = "https://dftgic7kmxqhe.cloudfront.net/COMMON/ic_style_kitch.png"
                        ),
                        FilterItem(
                            id = "LOVELY_CUTE",
                            title = "러블리큐트",
                            imageUrl = "https://dftgic7kmxqhe.cloudfront.net/COMMON/ic_style_lovely.png"
                        ),
                        FilterItem(
                            id = "CHIC",
                            title = "시크",
                            imageUrl = "https://dftgic7kmxqhe.cloudfront.net/COMMON/ic_style_chic.png"
                        ),
                        FilterItem(
                            id = "EMOTION",
                            title = "감성",
                            imageUrl = "https://dftgic7kmxqhe.cloudfront.net/COMMON/ic_style_emotion.png"
                        ),
                        FilterItem(
                            id = "MINIMAL",
                            title = "미니멀",
                            imageUrl = "https://dftgic7kmxqhe.cloudfront.net/COMMON/ic_style_minimal.png"
                        ),
                        FilterItem(
                            id = "VINTAGE",
                            title = "빈티지",
                            imageUrl = "https://dftgic7kmxqhe.cloudfront.net/COMMON/ic_style_vintage.png"
                        ),
                        FilterItem(
                            id = "MODERN",
                            title = "모던",
                            imageUrl = "https://dftgic7kmxqhe.cloudfront.net/COMMON/ic_style_modern.png"
                        ),
                        FilterItem(
                            id = "CLASSIC",
                            title = "클래식",
                            imageUrl = "https://dftgic7kmxqhe.cloudfront.net/COMMON/ic_style_classic.png"
                        ),
                        FilterItem(
                            id = "CASUAL",
                            title = "캐주얼",
                            imageUrl = "https://dftgic7kmxqhe.cloudfront.net/COMMON/ic_style_casual.png"
                        )
                    )
                )
            )
        )
    }


    fun getMockProductCompositorByCommunityContents(): Section<ProductItemCompositor> {
        return Section(
            id = NOT_AVALIVABLE_DATA,
            type = SectionType.V_CONTENT_IMAGE_GRID,
            headers = null,
            items = listOf(
                ProductItemCompositor(
                    base = BaseMixin(
                        id = "1",
                        title = "도도!",
                        imageUrl = "",
                    ),
                    supporting = SupportingMixin(
                        PostMixinMapper.PostItemSupporting(
                            description = "앞선 사진이 잘려 첨부합니당.",
                            category = "FEED",
                            time = "2021.06.07",
                            profileName = "무민잉",
                            profileImageUrl = "https://d16sraug4hsad1.cloudfront.net/PROFILE/user_profile_3.png",
                            isLike = false,
                            isScrap = false
                        )
                    )
                ),
                ProductItemCompositor(
                    base = BaseMixin(
                        id = "20",
                        title = "게시글 제목",
                        imageUrl = "https://d16sraug4hsad1.cloudfront.net/PROFILE/efcf9e1d-a2be-4625-8b9a-b7af57fc1667.jpeg",
                    ),
                    supporting = SupportingMixin(
                        PostMixinMapper.PostItemSupporting(
                            description = "앞선 사진이 잘려 첨부합니당.",
                            category = "FEED",
                            time = "2021.06.07",
                            profileName = "무민잉",
                            profileImageUrl = "https://d16sraug4hsad1.cloudfront.net/PROFILE/user_profile_3.png",
                            isLike = false,
                            isScrap = false
                        )
                    )
                ),
                ProductItemCompositor(
                    base = BaseMixin(
                        id = "20",
                        title = "게시글 제목",
                        imageUrl = "https://d16sraug4hsad1.cloudfront.net/PROFILE/efcf9e1d-a2be-4625-8b9a-b7af57fc1667.jpeg",
                    ),
                    supporting = SupportingMixin(
                        PostMixinMapper.PostItemSupporting(
                            description = "앞선 사진이 잘려 첨부합니당.",
                            category = "FEED",
                            time = "2021.06.07",
                            profileName = "무민잉",
                            profileImageUrl = "https://d16sraug4hsad1.cloudfront.net/PROFILE/user_profile_3.png",
                            isLike = false,
                            isScrap = false
                        )
                    )
                ),
            )
        )
    }
    fun getMockProductCompositorByCommunityContentsByType(type: SectionType, headers: HeaderUI? = null): Section<ProductItemCompositor> {
        return Section(
            id = NOT_AVALIVABLE_DATA,
            type = type,
            headers = headers,
            items = listOf(
                ProductItemCompositor(
                    base = BaseMixin(
                        id = "1",
                        title = "도도!",
                        imageUrl = "",
                    ),
                    supporting = SupportingMixin(
                        PostMixinMapper.PostItemSupporting(
                            description = "앞선 사진이 잘려 첨부합니당.",
                            category = "FEED",
                            time = "2021.06.07",
                            profileName = "무민잉",
                            profileImageUrl = "https://d16sraug4hsad1.cloudfront.net/PROFILE/user_profile_3.png",
                            isLike = false,
                            isScrap = false
                        )
                    )
                ),
                ProductItemCompositor(
                    base = BaseMixin(
                        id = "20",
                        title = "게시글 제목",
                        imageUrl = "https://d16sraug4hsad1.cloudfront.net/PROFILE/efcf9e1d-a2be-4625-8b9a-b7af57fc1667.jpeg",
                    ),
                    supporting = SupportingMixin(
                        PostMixinMapper.PostItemSupporting(
                            description = "앞선 사진이 잘려 첨부합니당.",
                            category = "FEED",
                            time = "2021.06.07",
                            profileName = "무민잉",
                            profileImageUrl = "https://d16sraug4hsad1.cloudfront.net/PROFILE/user_profile_3.png",
                            isLike = false,
                            isScrap = false
                        )
                    )
                ),
                ProductItemCompositor(
                    base = BaseMixin(
                        id = "20",
                        title = "게시글 제목",
                        imageUrl = "https://d16sraug4hsad1.cloudfront.net/PROFILE/efcf9e1d-a2be-4625-8b9a-b7af57fc1667.jpeg",
                    ),
                    supporting = SupportingMixin(
                        PostMixinMapper.PostItemSupporting(
                            description = "앞선 사진이 잘려 첨부합니당.",
                            category = "FEED",
                            time = "2021.06.07",
                            profileName = "무민잉",
                            profileImageUrl = "https://d16sraug4hsad1.cloudfront.net/PROFILE/user_profile_3.png",
                            isLike = false,
                            isScrap = false
                        )
                    )
                ),
            )
        )
    }
    fun getMockProductCompositorByGoods(type: SectionType, headers: HeaderUI? = null): Section<ProductItemCompositor> {
        return Section(
            id = NOT_AVALIVABLE_DATA,
            type = type,
            headers,
            items = listOf(
                ProductItemCompositor(
                    base = BaseMixin(
                        id = "23",
                        title = "자수 시리즈 마스킹 테이프",
                        imageUrl = "https://shop-phinf.pstatic.net/20230915_59/1694759545771Rumwy_JPEG/43557054509143018_1269087198.jpg?type=f300_300",
                    ),
                    supporting = SupportingMixin(
                        GoodsItemSupporting(
                            description = "누에누에 스튜디오",
                            category = "2310050544491",
                            time = "",
                            profileName = "",
                            profileImageUrl = null,
                            isLike = null,
                            isScrap = null,
                            price = GoodsMixinMapper.PriceItemSupporting(
                                percent = "",
                                normal = "3,000",
                                strike = "",
                                url = "https://www.nyoninyonistudio.com/shop_view/?idx=384",
                                isWish = true
                            )
                        )
                    )
                ),
                ProductItemCompositor(
                    base = BaseMixin(
                        id = "23",
                        title = "자수 시리즈 마스킹 테이프2",
                        imageUrl = "https://shop-phinf.pstatic.net/20230915_59/1694759545771Rumwy_JPEG/43557054509143018_1269087198.jpg?type=f300_300",
                    ),
                    supporting = SupportingMixin(
                        GoodsItemSupporting(
                            description = "누에누에",
                            category = "2310050544493",
                            time = "",
                            profileName = "",
                            profileImageUrl = null,
                            isLike = null,
                            isScrap = null,
                            price = GoodsMixinMapper.PriceItemSupporting(
                                percent = "10%",
                                normal = "2,700",
                                strike = "3,000",
                                isWish = true,
                                url = "https://www.nyoninyonistudio.com/shop_view/?idx=384"
                            )
                        )
                    )
                ),
            )
        )
    }


    fun mockManageTag(): GetManageTagByStickerResponse {
        val tags: List<ManageTag> = listOf(
            ManageTag("324", "#일상"),
            ManageTag("324", "#기분"),
            ManageTag("324", "#헤이모구"),
            ManageTag("324", "#방구석 몬찌즈")

        )
        return GetManageTagByStickerResponse(tags)
    }

    fun mockStikerCalendar() : StickerCalendar {

        return StickerCalendar(
            date = 1,
            fullDate = LocalDate.parse("2021-06-07"),
            userSticker = UserSticker(
                id = "4161",
                title = "스마트폰삼매경",
                description = "오늘 하루는 참 힘들었다",
                stickerId = "5",
                userId = 1243,
                date = "2021-05-05",
                imgUrl = "https://dftgic7kmxqhe.cloudfront.net/STICKER/5.png",
                tagName = "#헤이모구",
                underLineColor = "#F8E1E5"
            )
        )
    }

    fun mockStickerCalendarList(): List<StickerCalendar> {
        return (1..30).map { i ->
            StickerCalendar(
                date = i,
                fullDate = LocalDate.of(2021, 6, i),
                userSticker = when (i) {
                    1, 10 -> UserSticker(
                        id = "4161",
                        title = "스마트폰삼매경",
                        description = "오늘 하루는 참 힘들었다",
                        stickerId = "5",
                        userId = 1243,
                        date = LocalDate.of(2021, 6, i).toString(),
                        imgUrl = "https://dftgic7kmxqhe.cloudfront.net/STICKER/5.png",
                        tagName = "#헤이모구",
                        underLineColor = "#F8E1E5"
                    )
                    else -> null
                }
            )
        }
    }



    fun mockGoods(): List<Goods> {
        /*{
            "id": 2624,
            "writerId": 30,
            "goodsName": "[헤이모구] 투명 스티커 02 / 모서리 스티커",
            "brandName": "Hey,mogoo",
            "url": "https://mhottracks.kyobobook.co.kr/m/gift/detail/2310050544491",
            "salePrice": 3000,
            "salePercent": "0",
            "cmdtCode": "2310050544491",
            "defaultPrice": 3000,
            "createdAt": "2024-06-17T18:00:20.000Z",
            "updatedAt": "2024-06-17T18:00:20.000Z",
            "thumbnailImage": "https://contents.kyobobook.co.kr/sih/fit-in/600x0/gift/pdt/1899/hot1681395547983.jpg"
        },
        {
            "id": 2625,
            "writerId": 30,
            "goodsName": "[헤이모구] 투명 스티커 01",
            "brandName": "Hey,mogoo",
            "url": "https://mhottracks.kyobobook.co.kr/m/gift/detail/2310050544507",
            "salePrice": 3000,
            "salePercent": "0",
            "cmdtCode": "2310050544507",
            "defaultPrice": 3000,
            "createdAt": "2024-06-17T18:00:20.000Z",
            "updatedAt": "2024-06-17T18:00:20.000Z",
            "thumbnailImage": "https://contents.kyobobook.co.kr/sih/fit-in/600x0/gift/pdt/1067/hot1681397595160.png"
        },*/

        return listOf(
            Goods(
                id = 2624,
                writerId = 30,
                goodsName = "[헤이모구] 투명 스티커 02 / 모서리 스티커",
                brandName = "Hey,mogoo",
                url = "https://mhottracks.kyobobook.co.kr/m/gift/detail/2310050544491",
                salePrice = 3000,
                salePercent = "0",
                cmdtCode = "2310050544491",
                defaultPrice = 3000,
                createdAt = "2024-06-17T18:00:20.000Z",
                updatedAt = "2024-06-17T18:00:20.000Z",
                thumbnailImage = "https://contents.kyobobook.co.kr/sih/fit-in/600x0/gift/pdt/1899/hot1681395547983.jpg"
            ),
            Goods(
                id = 2625,
                writerId = 30,
                goodsName = "[헤이모구] 투명 스티커 01",
                brandName = "Hey,mogoo",
                url = "https://mhottracks.kyobobook.co.kr/m/gift/detail/2310050544507",
                salePrice = 3000,
                salePercent = "0",
                cmdtCode = "2310050544507",
                defaultPrice = 3000,
                createdAt = "2024-06-17T18:00:20.000Z",
                updatedAt = "2024-06-17T18:00:20.000Z",
                thumbnailImage = "https://contents.kyobobook.co.kr/sih/fit-in/600x0/gift/pdt/1067/hot1681397595160.png"
            )
        )
    }




}
