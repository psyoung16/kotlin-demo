package org.psy.demo.app.main

import org.psy.demo.app.main.usecase.GetCodeUseCase
import org.psy.demo.app.main.usecase.GetEventUseCase
import org.psy.demo.app.main.usecase.GetGoodsManagingUseCase
import org.psy.demo.app.main.usecase.GetGoodsUseCase
import org.psy.demo.app.main.usecase.GetMainUseCase
import org.psy.demo.app.main.usecase.GetManageTagUseCase
import org.psy.demo.app.main.usecase.GetNotiDataUseCase
import org.psy.demo.app.main.usecase.GetNotificationBadeUseCase
import org.psy.demo.app.main.usecase.GetPhraseUseCase
import org.psy.demo.app.main.usecase.GetPostUseCase
import org.psy.demo.app.main.usecase.GetSlideUseCase
import org.psy.demo.app.main.usecase.GetWriterUseCase
import org.psy.demo.core.post.domain.BestPostWithPagination
import org.psy.demo.core.content.domain.ChallengeResponse
import org.psy.demo.core.common.domain.Code
import org.psy.demo.app.main.response.MainGoodsManagingResponse
import org.psy.demo.app.main.response.MainWriterResponse
import org.psy.demo.core.common.domain.ManageTag
import org.psy.demo.core.common.domain.NotiDataResponse
import org.psy.demo.core.content.domain.BannerWithPagination
import org.psy.demo.core.phrase.domain.Phrase
import org.psy.demo.core.phrase.domain.PhraseResponse
import org.psy.demo.core.phrase.domain.PhraseWeight
import org.psy.demo.core.content.domain.PopupWithPagination
import org.psy.demo.core.vo.NotificationType
import org.psy.demo.core.vo.TagType
import org.psy.demo.core.user.domain.User
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GetMainFacade(
    private val getSlideUseCase: GetSlideUseCase,
    private val getManageTagUseCase: GetManageTagUseCase,
    private val getPostUseCase: GetPostUseCase,
    private val getCodeUseCase: GetCodeUseCase,
    private val getWriterUseCase: GetWriterUseCase,
    private val getGoodsUseCase: GetGoodsUseCase,
    private val getGoodsManagingUseCase: GetGoodsManagingUseCase,
    private val getEventUseCase: GetEventUseCase,
    private val getPhraseUseCase: GetPhraseUseCase,
    private val getNotificationBadeUseCase: GetNotificationBadeUseCase,
) : GetMainUseCase, GetNotiDataUseCase {

    override fun getMainPopupList(userId: User.UserId?): PopupWithPagination? {
        return getSlideUseCase.getMainSlidePopups(userId)
    }

    override fun getMainBannerList(userId: User.UserId?): BannerWithPagination? {
        return getSlideUseCase.getMainSlideBanners(userId)
    }

    @Cacheable(value = ["MainCategoryList"], key = "#root.method.name")
    override fun getMainCategoryList(): List<Code> {
        return getCodeUseCase.getCategoryList()
    }

    override fun getBestPostList(userId: User.UserId?): BestPostWithPagination? {
        return getPostUseCase.mainBestPost(userId ?: User.UserId(""))
    }

    @Cacheable(value = ["MainProWriterWithGoodsList"], key = "#root.method.name")
    override fun getMainProWriterWithGoodsList(): List<MainWriterResponse> {
        val writers = getWriterUseCase.getMainWriter()

        return writers.map {  writer ->
            MainWriterResponse.Companion.of(
                writer,
                getGoodsUseCase.getCodeListByWriterId(writer.id)
            )
        }
    }

//    @Cacheable(value = ["MainGoodsManagingWriterGoods"], key = "#root.method.name")
    override fun getMainGoodsManagingWriterGoods(): List<MainGoodsManagingResponse> {
        val gmList = getGoodsManagingUseCase.getMainGoodsManaiging()

        // in query 바꿀게요...
        return gmList.map { gm ->
            MainGoodsManagingResponse.Companion.of(
                gm,
                getGoodsUseCase.getCodeListByGmId(gm.id)
            )
        }
    }

    @Cacheable(value = ["ManageTagList"], key = "#root.method.name + #position")
    override fun getManageTagList(position: TagType): List<ManageTag> {
        return getManageTagUseCase.getRecommandManageTag(position)
    }

    override fun getMainChallengeList(): List<ChallengeResponse> {
        val challengeList = getEventUseCase.getMainChallenges()

        //in query
        return challengeList.map { event ->
            ChallengeResponse.Companion.of(
                event,
                getPostUseCase.getPostsByChallenge(event)
            )
        }

    }

    override fun getMainPhrase(): List<PhraseResponse> {

        val phraseobj: Phrase? = getPhraseUseCase.getPhrase("HOME_STICKER")

        //let은 phraseobj이 Null이 아닐 경우에만 사용
        val responses = phraseobj?.let { phrase ->
            val boldPhrase = phrase.boldPhrase
            val parts = boldPhrase.let { splitPhrase -> phrase.phrase.split(splitPhrase) }

            mutableListOf<PhraseResponse>().apply {
                add(PhraseResponse(parts[0], PhraseWeight.REGULAR))
                add(PhraseResponse(boldPhrase, PhraseWeight.BOLD))
                if (parts.size == 2) {
                    add(PhraseResponse(parts[1], PhraseWeight.REGULAR))
                }
            }
        } ?: listOf(
            PhraseResponse("오늘 하루", PhraseWeight.REGULAR),
            PhraseResponse("를 스티커로 표현해보세요!", PhraseWeight.REGULAR)
        )

        return responses
    }

    override fun getMainNotiData(userId: User.UserId): NotiDataResponse {
        return NotiDataResponse(
            getNotificationBadeUseCase.getNotificationBadge(userId, NotificationType.EVENT),
            getNotificationBadeUseCase.getNotificationBadge(userId, NotificationType.CHALLENGE),
            getNotificationBadeUseCase.getNotificationBadge(userId, NotificationType.TALK),
            getNotificationBadeUseCase.getNotificationBadge(userId, NotificationType.ISSUE)
        )
    }

}