package org.psy.demo.common

import org.psy.demo.app.main.response.MainGoodsManagingResponse
import org.psy.demo.app.main.response.MainWriterResponse
import org.psy.demo.core.common.domain.Code
import org.psy.demo.core.common.domain.ManageTag
import org.psy.demo.core.common.domain.NotiDataResponse
import org.psy.demo.core.content.domain.Banner
import org.psy.demo.core.content.domain.BannerWithPagination
import org.psy.demo.core.content.domain.ChallengeResponse
import org.psy.demo.core.content.domain.Popup
import org.psy.demo.core.content.domain.PopupWithPagination
import org.psy.demo.core.goods.domain.Goods
import org.psy.demo.core.phrase.domain.PhraseResponse
import org.psy.demo.core.phrase.domain.PhraseWeight
import org.psy.demo.core.post.domain.BestPost
import org.psy.demo.core.post.domain.BestPostWithPagination
import org.psy.demo.core.vo.SlideExposure
import org.psy.demo.core.vo.SlidePosition
import org.psy.demo.core.vo.SlideSimpleImageResponse
import org.psy.demo.core.vo.SlideStatus
import org.psy.demo.infra.jpaEntity.SlideJpaEntity

object MainMockDataSetting {


    fun getMainPopupList(): PopupWithPagination? {
        return PopupWithPagination(
            listOf(
                Popup(
                    id = 375,
                    type = SlideJpaEntity.SlideType.EVENT,
                    webViewTitle = "최애브랜드 챌린지 당첨자 발표",
                    linkUrl = "",
                    mainImage = null,
                    subImage = null,
                    popupImage = SlideSimpleImageResponse(
                        id = 0,
                        url = "https://dftgic7kmxqhe.cloudfront.net/SLIDE/9d0377a4-f090-4a93-967a-ea9751f1aa81.png",
                        filename = ""
                    ),
                    numOrder = 10015,
                    createdBy = 4541,
                    startAt = "2024-07-28T15:00:00.000Z",
                    endAt = "2024-08-10 T15:00:00.000Z",
                    position = SlidePosition.MAIN,
                    exposure = SlideExposure.BANNER,
                    eventsId = 302,
                    status = SlideStatus.ACTIVE,
                    createdAt = "2024-07-29T01:26:15.000Z",
                    updatedAt = "2024-07-29T01:26:44.000Z"
                )
            ),
            MetaData(
                previousPage = 1,
                currentPage = 1,
                nextPage = 2,
                lastPage = 1,
                pageSize = 1,
                totalPages = 1,
                totalRecords = 1
            )
        )
    }
    fun getMainBannerList(): BannerWithPagination? {
        return BannerWithPagination(
            listOf(
                Banner(
                    id = 375,
                    type = SlideJpaEntity.SlideType.EVENT,
                    webViewTitle = "최애브랜드 챌린지 당첨자 발표",
                    linkUrl = "",
                    mainImage = SlideSimpleImageResponse(
                        id = 0,
                        url = "https://dftgic7kmxqhe.cloudfront.net/SLIDE/9d0377a4-f090-4a93-967a-ea9751f1aa81.png",
                        filename = ""
                    ),
                    subImage = SlideSimpleImageResponse(
                        id = 0,
                        url = "https://dftgic7kmxqhe.cloudfront.net/SLIDE/9d0377a4-f090-4a93-967a-ea9751f1aa81.png",
                        filename = ""
                    ),
                    popupImage = null,
                    numOrder = 10015,
                    createdBy = 4541,
                    startAt = "2024-07-28T15:00:00.000Z",
                    endAt = "2024-08-10 T15:00:00.000Z",
                    position = SlidePosition.MAIN,
                    exposure = SlideExposure.BANNER,
                    eventsId = 302,
                    status = SlideStatus.ACTIVE,
                    createdAt = "2024-07-29T01:26:15.000Z",
                    updatedAt = "2024-07-29T01:26:44.000Z"
                ),
                Banner(
                    id = 141,
                    type = SlideJpaEntity.SlideType.WEB_VIEW,
                    webViewTitle = "고객 설문조사 (유저피드백)",
                    linkUrl = "https://forms.gle/7pVEhJMV4Fua7J6c6",
                    mainImage = SlideSimpleImageResponse(
                        id = 0,
                        url = "https://dftgic7kmxqhe.cloudfront.net/SLIDE/d1987c4c-b4ac-4d6c-815a-c52437801628.png",
                        filename = ""
                    ),
                    subImage = SlideSimpleImageResponse(
                        id = 0,
                        url = "https://dftgic7kmxqhe.cloudfront.net/SLIDE/d1987c4c-b4ac-4d6c-815a-c52437801628.png",
                        filename = ""
                    ),
                    popupImage = null,
                    numOrder = 1021,
                    createdBy = 4541,
                    startAt = "2023-01-09T15:00:00.000Z",
                    endAt = "2025-01-30T15:00:00.000Z",
                    position = SlidePosition.MAIN,
                    exposure = SlideExposure.BANNER,
                    eventsId = null,
                    status = SlideStatus.ALWAYS,
                    createdAt = "2023-01-10T04:14:39.000Z",
                    updatedAt = "2024-05-02T05:47:41.000Z"
                )
            ),
            MetaData(
                previousPage = 1,
                currentPage = 1,
                nextPage = 2,
                lastPage = 1,
                pageSize = 1,
                totalPages = 1,
                totalRecords = 1
            )
        )
    }

    fun getMainCategoryList(): List<Code> {
        return listOf(
            Code(
                code = "0009",
                codeName = "할인 상품",
                iconImgUrl = "https://dftgic7kmxqhe.cloudfront.net/COMMON/4c7df76d-4645-476f-bbc9-d476acd9e4c7.png"
            ),
            Code(
                code = "0000",
                codeName = "키링",
                iconImgUrl = "https://dftgic7kmxqhe.cloudfront.net/COMMON/cfa52197-0cf6-4e5a-80c6-d25accfc5a05.png"
            )

        )
    }

    /*[
    {
        "text": "오늘 하루를 \n",
        "weight": "REGULAR"
    },
    {
        "text": "스티커",
        "weight": "BOLD"
    },
    {
        "text": "로 꾸며 보세요!",
        "weight": "REGULAR"
    }
    ],*/
    fun getPhraseCode(): List<PhraseResponse> {
        return listOf(
            PhraseResponse(
                text = "오늘 하루를 \n",
                weight = PhraseWeight.REGULAR
            ),
            PhraseResponse(
                text = "스티커",
                weight = PhraseWeight.BOLD
            ),
            PhraseResponse(
                text = "로 꾸며 보세요!",
                weight = PhraseWeight.REGULAR
            )
        )
    }

    /*{
        "id": 65979,
        "description": "암튼 왕귀엽다!",
        "title": "요즘 포케몬이 왕 귀엽다는거 아시나요?",
        "postInfoId": 1,
        "thumbnailImage": "https://dftgic7kmxqhe.cloudfront.net/POST/87a90d59-7278-46fa-a47a-05bf8398fe59.png",
        "owner": null,
        "album": []
    },*/
    fun getBestPosts(): BestPostWithPagination? {
        return BestPostWithPagination(
            listOf(
                BestPost(
                    id = 65979,
                    description = "베스트 게시물 설명",
                    title = "요즘 포케몬이 왕 귀엽다는거 아시나요?",
                    postInfoId = 1,
                    thumbnailImage = "https://dftgic7kmxqhe.cloudfront.net/POST/87a90d59-7278-46fa-a47a-05bf8398fe59.png",
                    owner = null,
                    album = listOf()
                ),
                BestPost(
                    id = 65980,
                    description = "베스트 게시물 설명",
                    title = "요즘 포케몬이 왕 귀엽다는거 아시나요?",
                    postInfoId = 1,
                    thumbnailImage = "https://dftgic7kmxqhe.cloudfront.net/POST/87a90d59-7278-46fa-a47a-05bf8398fe59.png",
                    owner = null,
                    album = listOf()
                )
            ),
            MetaData(
                previousPage = 1,
                currentPage = 1,
                nextPage = 2,
                lastPage = 1,
                pageSize = 1,
                totalPages = 1,
                totalRecords = 1
            )
        )
    }

    /*{
        "id": 29,
        "nickname": "밤즈",
        "avatarUrl": "https://dftgic7kmxqhe.cloudfront.net/PROFILE/26ae6615-dfb1-4c3c-8441-a10a81b05268.jpg",
        "backgroundImg": "https://dftgic7kmxqhe.cloudfront.net/PROFILE/c47c457d-d61a-4022-adeb-1d8a35339c94.jpg",
        "goodsList": [
        {
            "id": 33907,
            "writerId": 29,
            "goodsName": "밤즈 반짝이는 여름해변 스티커",
            "brandName": "BALMS",
            "url": "https://mhottracks.kyobobook.co.kr/m/gift/detail/2895827902204",
            "salePrice": 2000,
            "salePercent": "20",
            "cmdtCode": "2895827902204",
            "defaultPrice": 2500,
            "createdAt": "2024-07-29T18:00:41.000Z",
            "updatedAt": "2024-07-29T18:00:41.000Z",
            "thumbnailImage": "https://contents.kyobobook.co.kr/sih/fit-in/600x0/gift/pdt/1937/hot1718704272990.png"
        },
        {
            "id": 33904,
            "writerId": 29,
            "goodsName": "밤즈 달콤상콤 과일 스티커",
            "brandName": "BALMS",
            "url": "https://mhottracks.kyobobook.co.kr/m/gift/detail/2895827902211",
            "salePrice": 2000,
            "salePercent": "20",
            "cmdtCode": "2895827902211",
            "defaultPrice": 2500,
            "createdAt": "2024-07-29T18:00:41.000Z",
            "updatedAt": "2024-07-29T18:00:41.000Z",
            "thumbnailImage": "https://contents.kyobobook.co.kr/sih/fit-in/600x0/gift/pdt/1792/hot1718702434979.png"
        }
        ]
    },*/
    fun getProWirter(): List<MainWriterResponse> {
        return listOf(
            MainWriterResponse(
                id = 29,
                nickname = "밤즈",
                avatarUrl = "https://dftgic7kmxqhe.cloudfront.net/PROFILE/26ae6615-dfb1-4c3c-8441-a10a81b05268.jpg",
                backgroundImg = "https://dftgic7kmxqhe.cloudfront.net/PROFILE/c47c457d-d61a-4022-adeb-1d8a35339c94.jpg",
                goodsList = listOf(
                    Goods(
                        id = 33907,
                        writerId = 29,
                        goodsName = "밤즈 반짝이는 여름해변 스티커",
                        brandName = "BALMS",
                        url = "https://mhottracks.kyobobook.co.kr/m/gift/detail/2895827902204",
                        salePrice = 2000,
                        salePercent = "20",
                        cmdtCode = "2895827902204",
                        defaultPrice = 2500,
                        createdAt = "2024-07-29T18:00:41.000Z",
                        updatedAt = "2024-07-29T18:00:41.000Z",
                        thumbnailImage = "https://contents.kyobobook.co.kr/sih/fit-in/600x0/gift/pdt/1937/hot1718704272990.png"
                    ),
                )
            ),
        )
    }

    /*{
        "id": 179,
        "title": "시원한 숲속으로 모여요 🌲",
        "startAt": "2024-07-26T07:03:27.000Z",
        "endAt": "2099-12-31T00:00:00.000Z",
        "orderNum": 112,
        "writerGoods": [
        {
            "id": 20279,
            "writerId": 126,
            "goodsName": "숲속 쉼터 / 반딧불이 정원 엽서 포스터 벽꾸미기",
            "brandName": "우니복이",
            "url": "https://smartstore.naver.com/woonis_store/products/9811379785",
            "salePrice": 1000,
            "salePercent": "0",
            "cmdtCode": "9811379785",
            "defaultPrice": 1000,
            "createdAt": "2024-01-15T07:47:24.000Z",
            "updatedAt": "2024-07-29T18:00:06.000Z",
            "thumbnailImage": "https://shop-phinf.pstatic.net/20240115_259/17053047423064OvDr_JPEG/106440584929466589_216210776.jpg?type=f300_300"
        },
        {
            "id": 16199,
            "writerId": 18,
            "goodsName": "숲속 친구들",
            "brandName": "뇨니뇨니스튜디오",
            "url": "https://mhottracks.kyobobook.co.kr/m/gift/detail/2300186151670",
            "salePrice": 2500,
            "salePercent": "0",
            "cmdtCode": "2300186151670",
            "defaultPrice": 2500,
            "createdAt": "2024-06-06T18:00:27.000Z",
            "updatedAt": "2024-07-29T20:02:59.000Z",
            "thumbnailImage": "https://contents.kyobobook.co.kr/sih/fit-in/600x0/gift/pdt/1190/hot1641970626912.jpg"
        },
        {
            "id": 16214,
            "writerId": 18,
            "goodsName": "숲 속 우체부",
            "brandName": "뇨니뇨니스튜디오",
            "url": "https://mhottracks.kyobobook.co.kr/m/gift/detail/2300186216423",
            "salePrice": 2300,
            "salePercent": "0",
            "cmdtCode": "2300186216423",
            "defaultPrice": 2300,
            "createdAt": "2024-06-06T18:00:27.000Z",
            "updatedAt": "2024-07-29T18:00:07.000Z",
            "thumbnailImage": "https://contents.kyobobook.co.kr/sih/fit-in/600x0/gift/pdt/1843/hot1661315377867.jpg"
        },
        {
            "id": 50125,
            "writerId": 41,
            "goodsName": "숲속 물장구 씰스티커",
            "brandName": "루니뽀 스튜디오",
            "url": "https://smartstore.naver.com/loonyppo/products/10603766089",
            "salePrice": 2500,
            "salePercent": "11",
            "cmdtCode": "10603766089",
            "defaultPrice": 2800,
            "createdAt": "2024-07-17T08:06:40.000Z",
            "updatedAt": "2024-07-29T18:00:40.000Z",
            "thumbnailImage": "https://shop-phinf.pstatic.net/20240717_283/1721203362000aaCkh_JPEG/67837145232181327_858889139.jpg?type=f300_300"
        },
        {
            "id": 16247,
            "writerId": 4,
            "goodsName": "숲속의 도서관",
            "brandName": "ozziday",
            "url": "https://mhottracks.kyobobook.co.kr/m/gift/detail/2310044797728",
            "salePrice": 2800,
            "salePercent": "0",
            "cmdtCode": "2310044797728",
            "defaultPrice": 2800,
            "createdAt": "2024-06-06T18:00:31.000Z",
            "updatedAt": "2024-07-29T18:00:07.000Z",
            "thumbnailImage": "https://contents.kyobobook.co.kr/sih/fit-in/600x0/gift/pdt/1649/hot1631179180318.png"
        },
        {
            "id": 16205,
            "writerId": 18,
            "goodsName": "숲속 꾸미기",
            "brandName": "뇨니뇨니스튜디오",
            "url": "https://mhottracks.kyobobook.co.kr/m/gift/detail/2300186169453",
            "salePrice": 2500,
            "salePercent": "0",
            "cmdtCode": "2300186169453",
            "defaultPrice": 2500,
            "createdAt": "2024-06-06T18:00:27.000Z",
            "updatedAt": "2024-07-29T18:00:07.000Z",
            "thumbnailImage": "https://contents.kyobobook.co.kr/sih/fit-in/600x0/gift/pdt/1235/hot1644583064673.png"
        },
        {
            "id": 7873,
            "writerId": 112,
            "goodsName": "숲속 도서관 스티커",
            "brandName": "황다람",
            "url": "https://www.hwang-daram.com/56/?idx=622",
            "salePrice": 3000,
            "salePercent": "0",
            "cmdtCode": "622",
            "defaultPrice": 3000,
            "createdAt": "2024-06-09T18:00:48.000Z",
            "updatedAt": "2024-07-29T18:00:03.000Z",
            "thumbnailImage": "https://cdn.imweb.me/thumbnail/20231110/5c056bef2485e.jpg"
        },
        {
            "id": 7774,
            "writerId": 112,
            "goodsName": "깊은 숲속 캠핑 스티커 (라인)",
            "brandName": "황다람",
            "url": "https://www.hwang-daram.com/56/?idx=491",
            "salePrice": 3000,
            "salePercent": "0",
            "cmdtCode": "491",
            "defaultPrice": 3000,
            "createdAt": "2024-06-09T18:00:45.000Z",
            "updatedAt": "2024-07-29T18:00:03.000Z",
            "thumbnailImage": "https://cdn.imweb.me/thumbnail/20220729/43fd121e9932e.jpg"
        },
        {
            "id": 7803,
            "writerId": 112,
            "goodsName": "수상한 숲속에서 모임 엽서",
            "brandName": "황다람",
            "url": "https://www.hwang-daram.com/56/?idx=539",
            "salePrice": 1500,
            "salePercent": "0",
            "cmdtCode": "539",
            "defaultPrice": 1500,
            "createdAt": "2024-06-09T18:00:46.000Z",
            "updatedAt": "2024-07-29T18:00:03.000Z",
            "thumbnailImage": "https://cdn.imweb.me/thumbnail/20221013/626442e2dda95.jpg"
        }
        ]
    },*/
    fun getGoodsManagingWriterGoods(): List<MainGoodsManagingResponse> {

        return listOf(
            MainGoodsManagingResponse(
                id = 179,
                title = "시원한 숲속으로 모여요 🌲",
                startAt = "2024-07-26T07:03:27.000Z",
                endAt = "2099-12-31",
                orderNum = 112,
                writerGoods = listOf(
                    Goods(
                        id = 20279,
                        writerId = 126,
                        goodsName = "숲속 쉼터 / 반딧불이 정원 엽서 포스터 벽꾸미기",
                        brandName = "우니복이",
                        url = "https://smartstore.naver.com/woonis_store/products/9811379785",
                        salePrice = 1000,
                        salePercent = "0",
                        cmdtCode = "9811379785",
                        defaultPrice = 1000,
                        createdAt = "2024-01-15T07:47:24.000Z",
                        updatedAt = "2024-07-29T18:00:06.000Z",
                        thumbnailImage = "https://shop-phinf.pstatic.net/20240115_259/17053047423064OvDr_JPEG/106440584929466589_216210776.jpg?type=f300_300"
                    ),
                    Goods(
                        id = 16199,
                        writerId = 18,
                        goodsName = "숲속 친구들",
                        brandName = "뇨니뇨니스튜디오",
                        url = "https://mhottracks.kyobobook.co.kr/m/gift/detail/2300186151670",
                        salePrice = 2500,
                        salePercent = "0",
                        cmdtCode = "2300186151670",
                        defaultPrice = 2500,
                        createdAt = "2024-06-06T18:00:27.000Z",
                        updatedAt = "2024-07-29T20:02:59.000Z",
                        thumbnailImage = "https://contents.kyobobook.co.kr/sih/fit-in/600x0/gift/pdt/1190/hot1641970626912.jpg"
                    ),
                ),
            )
        )
    }


    /*{
        "id": "2",
        "tagName": "#꾸밍챌린지"
    },
    {
        "id": "357",
        "tagName": "#꾸미기"
    },
    {
        "id": "263",
        "tagName": "#일러스트"
    },
    {
        "id": "321",
        "tagName": "#굿즈"
    },
    {
        "id": "237",
        "tagName": "#스티커"
    },
    {
        "id": "320",
        "tagName": "#다꾸"
    },
    {
        "id": "319",
        "tagName": "#키링"
    },
    {
        "id": "322",
        "tagName": "#캐릭터"
    }*/
    fun getRecommendTag(): List<ManageTag> {
        return listOf(
            ManageTag(
                id = "2",
                tagName = "#꾸밍챌린지"
            ),
            ManageTag(
                id = "357",
                tagName = "#꾸미기"
            ),
            ManageTag(
                id = "263",
                tagName = "#일러스트"
            ),
            ManageTag(
                id = "321",
                tagName = "#굿즈"
            ),
            ManageTag(
                id = "237",
                tagName = "#스티커"
            ),
            ManageTag(
                id = "320",
                tagName = "#다꾸"
            ),
            ManageTag(
                id = "319",
                tagName = "#키링"
            ),
            ManageTag(
                id = "322",
                tagName = "#캐릭터"

            )
        )
    }

    fun getMainChallengeList(): List<ChallengeResponse> {
        return listOf(
        )
    }

    /*"isReadEvent": false,
    "isReadChallenge": false,
    "isReadTalk": false,
    "isReadIssue": false*/
    fun getNotiCountData(): NotiDataResponse {
        return NotiDataResponse(
            isReadEvent = false,
            isReadChallenge = false,
            isReadTalk = false,
            isReadIssue = false
        )
    }


}
