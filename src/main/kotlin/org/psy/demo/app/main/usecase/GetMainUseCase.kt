package org.psy.demo.app.main.usecase

import org.psy.demo.app.main.response.MainGoodsManagingResponse
import org.psy.demo.app.main.response.MainWriterResponse
import org.psy.demo.core.common.domain.Code
import org.psy.demo.core.common.domain.ManageTag
import org.psy.demo.core.common.domain.NotiDataResponse
import org.psy.demo.core.content.domain.BannerWithPagination
import org.psy.demo.core.content.domain.ChallengeResponse
import org.psy.demo.core.content.domain.PopupWithPagination
import org.psy.demo.core.phrase.domain.PhraseResponse
import org.psy.demo.core.post.domain.BestPostWithPagination
import org.psy.demo.core.promotion.Promotion
import org.psy.demo.core.user.domain.User
import org.psy.demo.core.vo.TagType

interface GetMainUseCase {


    fun getMainPopupList(userId: User.UserId?): PopupWithPagination?
    fun getMainBannerList(userId: User.UserId?): BannerWithPagination?

    fun getMainCategoryList(): List<Code>
    fun getBestPostList(userId: User.UserId?): BestPostWithPagination?

    fun getMainProWriterWithGoodsList(): List<MainWriterResponse>
    fun getMainGoodsManagingWriterGoods(): List<MainGoodsManagingResponse>

    fun getManageTagList(position: TagType): List<ManageTag>
    fun getMainChallengeList(): List<ChallengeResponse>

    fun getMainPhrase(): List<PhraseResponse>



    data class GetMainResponse(
        val popup: PopupWithPagination?,
        val banner: BannerWithPagination?,
        val banner2: List<Promotion>,
        val categoryList: List<Code>,
        val phrase: List<PhraseResponse>,
        val best: BestPostWithPagination?,
        val proWriter: List<MainWriterResponse>,
        val goodsManagingWriterGoods: List<MainGoodsManagingResponse>,
        val recommendTag: List<ManageTag>,
        val challenge: List<ChallengeResponse>,
        val notiCountData: NotiDataResponse?
    )
}