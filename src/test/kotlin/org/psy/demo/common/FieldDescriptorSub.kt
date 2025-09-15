package org.psy.demo.common

import org.springframework.restdocs.payload.FieldDescriptor

object FieldDescriptorSub {

    fun defaultData(): Array<FieldDescriptor> {
        return arrayOf(
            "data" type OBJECT means "데이터",
            "status" type NUMBER means "상태",
            "success" type BOOLEAN means "성공 여부",
        )
    }

    fun defaultSectionInfo(path: String, item: Array<FieldDescriptor>): Array<FieldDescriptor> {
        return arrayOf(
            path type OBJECT means "section",
            "${path}.id" type STRING means "section id",
            "${path}.type" type STRING means "section type",
            "${path}.headers" type OBJECT means "section headers",
            "${path}.headers.title" type STRING means "section headers title",
            "${path}.headers.button" type BOOLEAN means "section headers button",
            "${path}.items[]" type ARRAY means "section items",
            *item
        )
    }


    fun defaultSectionInfoAndHeaderOptional(path: String, item: Array<FieldDescriptor>): Array<FieldDescriptor> {
        return arrayOf(
            path type OBJECT means "section",
            "${path}.id" type STRING means "section id",
            "${path}.type" type STRING means "section type",
            "${path}.headers" type OBJECT means "section headers" isOptional true,
            "${path}.headers.title" type STRING means "section headers title",
            "${path}.headers.button" type BOOLEAN means "section headers button",
            "${path}.items[]" type ARRAY means "section items",
            *item
        )
    }

    fun defaultMetaData(path: String): Array<FieldDescriptor> {
        return arrayOf(
            "${path}metadata.previousPage" type NUMBER means "이전 페이지",
            "${path}metadata.currentPage" type NUMBER means "현재 페이지",
            "${path}metadata.nextPage" type NUMBER means "다음 페이지",
            "${path}metadata.lastPage" type NUMBER means "마지막 페이지",
            "${path}metadata.pageSize" type NUMBER means "페이지 사이즈",
            "${path}metadata.firstPage" type NUMBER means "첫 페이지",
            "${path}metadata.totalPages" type NUMBER means "전체 페이지",
            "${path}metadata.totalRecords" type NUMBER means "전체 레코드 수"
        )
    }

    fun myroomProfile(path: String): Array<FieldDescriptor> {
        return arrayOf(
            "${path}.id" type STRING means "id",
            "${path}.title" type STRING means "제목",
            "${path}.imageUrl" type STRING means "해당 프로필 이미지 url",
            "${path}.totalFollower" type NUMBER means "총 팔로워 개수",
            "${path}.totalFollowing" type NUMBER means "총 팔로잉 개수",
            "${path}.totalReported" type NUMBER means "신고횟수",
            "${path}.bio" type STRING means "프로필 소개",
            "${path}.isFollowed" type BOOLEAN means "팔로우 되어 있는지 여부 null:본인이 본인 조회했을 때" isOptional true
        )
    }

    fun myroomSticker(path: String): Array<FieldDescriptor> {
        return arrayOf(
            "${path}.id" type STRING means "id",
            "${path}.title" type STRING means "제목",
            "${path}.imageUrl" type STRING means "스티커 기본 이미지",
            "${path}.time" type STRING means "오늘 날짜 있는 부분에 들어가는 text",
            "${path}.isHasSticker" type BOOLEAN means "오늘 스티커를 찍었는지의 유무"
        )
    }

    fun myroomFeed(path: String): Array<FieldDescriptor> {
        return arrayOf(
            "${path}.id" type STRING means "id",
            "${path}.title" type STRING means "제목",
            "${path}.imageUrl" type STRING means "게시글 썸네일",

            "${path}.description" type STRING means "게시글 내용",
            "${path}.category" type STRING means "게시물 카테고리 [POST_GOODS: 굿즈, FEED: 피드, STAR: 일러스트, ETC: 잡담, VOTE: 투표, ISSUE:이슈뉴스, TALK: 꾸밍톡  ] ",
            "${path}.time" type STRING means "작성 시간",

            "${path}.profileName" type STRING means "프로필 닉네임 ",
            "${path}.profileImageUrl" type STRING means "프로필 썸네일",

            "${path}.isLike" type BOOLEAN means "해당 게시글 좋아요 여부",
            "${path}.isScrap" type BOOLEAN means "해당 게시글 스크랩 여부",

            )
    }
    fun storageWishGoods(path: String): Array<FieldDescriptor> {
        return arrayOf(
            "${path}.id" type STRING means "id",
            "${path}.title" type STRING means "제목",
            "${path}.imageUrl" type STRING means "게시글 썸네일",

            "${path}.description" type STRING means "게시글 내용",
            "${path}.category" type STRING means "게시물 카테고리 [POST_GOODS: 굿즈, FEED: 피드, STAR: 일러스트, ETC: 잡담, VOTE: 투표, ISSUE:이슈뉴스, TALK: 꾸밍톡  ] ",
            "${path}.time" type STRING means "작성 시간",

            "${path}.profileName" type STRING means "프로필 닉네임 ",
            "${path}.profileImageUrl" type STRING means "프로필 썸네일" isOptional true,

            "${path}.isLike" type BOOLEAN means "해당 게시글 좋아요 여부" isOptional true,
            "${path}.isScrap" type BOOLEAN means "해당 게시글 스크랩 여부" isOptional true,


            "${path}.price" type OBJECT means "가격 정보 객체" isOptional true,
            "${path}.price.percent" type STRING means "할인율, 1번쨰",
            "${path}.price.normal" type STRING means "블랙 2번째",
            "${path}.price.strike" type STRING means "회색 3번째",
            "${path}.price.isWish" type BOOLEAN means "상품 좋아요 여부",
            "${path}.price.url" type STRING means "눌렀을때 상품 이동 내부 url",

            )
    }

    fun myroomPost(path: String): Array<FieldDescriptor> {
        return arrayOf(

            "${path}.id" type STRING means "id",
            "${path}.title" type STRING means "제목",
            "${path}.imageUrl" type STRING means "게시글 썸네일",

            "${path}.description" type STRING means "게시글 내용",
            "${path}.category" type STRING means "게시물 카테고리 [ETC: 잡담, VOTE: 투표 ] ",
            "${path}.time" type STRING means "작성 시간",

            "${path}.profileName" type STRING means "프로필 닉네임 ",
            "${path}.profileImageUrl" type STRING means "프로필 썸네일",

            "${path}.isLike" type BOOLEAN means "해당 게시글 좋아요 여부",
            "${path}.isScrap" type BOOLEAN means "해당 게시글 스크랩 여부",
        )
    }

    fun myroomMyBadge(path: String): Array<FieldDescriptor> {
        return arrayOf(
            "${path}.id" type STRING means "id",
            "${path}.title" type STRING means "제목",
            "${path}.imageUrl" type STRING means "뱃지 이미지",
        )
    }

    fun myroomPostTabs(path: String): Array<FieldDescriptor> {
        return arrayOf(
            "${path}.id" type STRING means "탭 id",
            "${path}.title" type STRING means "탭 제목",
            "${path}.imageUrl" type STRING means "이미지 url (사용x)" isOptional true
        )
    }

    fun myroomStory(path: String): Array<FieldDescriptor> {
        return arrayOf(
            "${path}.id" type STRING means "스토리 id",
            "${path}.title" type STRING means "스토리 제목",
            "${path}.imageUrl" type STRING means "스토리 image"
        )
    }

    fun communityTab(path: String): Array<FieldDescriptor> {
        return arrayOf(
            "${path}.id" type STRING means "tab id",
            "${path}.title" type STRING means "tab title",
            "${path}.imageUrl" type STRING means "tab image url" isOptional true
        )
    }

    fun communityBanner(path: String): Array<FieldDescriptor> {
        return arrayOf(
            "${path}.id" type STRING means "banner id",
            "${path}.title" type STRING means "banner title",
            "${path}.imageUrl" type STRING means "banner image url",
            "${path}.subId" type STRING means "contentTypeLink가 event일 경우 eventId로 사용" isOptional true,
            "${path}.contentLinkType" type STRING means "이동하는 방식",
            "${path}.url" type STRING means "이동 url",
        )
    }

    fun communityFilters(path: String): Array<FieldDescriptor> {
        return arrayOf(
            "${path}.id" type STRING means "filter id",
            "${path}.layoutIcon" type ARRAY means "layout icon",
            "${path}.aligns" type ARRAY means "aligns",

            "${path}.aligns[].title" type STRING means "정렬 검색 이름",
            "${path}.aligns[].searchKey" type STRING means "정렬 검색키",

            "${path}.tags[]" type ARRAY means "tags 스타일태그/태그",
            "${path}.tags[].type" type STRING means "tag type 스타일태그/태그 타입 중 1",
            "${path}.tags[].searchKey" type STRING means "tag 검색키",
            "${path}.tags[].headers" type OBJECT means "tag headers",
            "${path}.tags[].headers.title" type STRING means "tag headers의 제목",
            "${path}.tags[].headers.button" type BOOLEAN means "사용x" isOptional true,
            "${path}.tags[].items[]" type ARRAY means "tag items",
            "${path}.tags[].items[].id" type STRING means "tag item id -> 해당 값을 ,로 합쳐서 서버로 전송",
            "${path}.tags[].items[].title" type STRING means "tag item title",
            "${path}.tags[].items[].imageUrl" type STRING means "tag item image url 현재는 style만 붙여져있음" isOptional true,

            )
    }


    fun stickerGoodsOld(path: String) : Array<FieldDescriptor>{
        return arrayOf(
            "${path}.id" type NUMBER means "id",
            "${path}.writerId" type NUMBER means "작성자 id",
            "${path}.goodsName" type STRING means "굿즈 이름",
            "${path}.brandName" type STRING means "브랜드 이름",
            "${path}.url" type STRING means "연결 Url",
            "${path}.salePrice" type NUMBER means "세일 가격",
            "${path}.salePercent" type STRING means "세일 퍼센트",
            "${path}.cmdtCode" type STRING means "고유 코드",
            "${path}.defaultPrice" type NUMBER means "정가",
            "${path}.createdAt" type STRING means "등록 날짜 2024-06-17T18:00:20.000Z",
            "${path}.updatedAt" type STRING means "수정 날짜 2024-06-17T18:00:20.000Z",
            "${path}.thumbnailImage" type STRING means "썸네일 image url",
        )
    }

    fun stickerCalendarData(path: String): Array<FieldDescriptor> {
        return arrayOf(
            "${path}.date" type NUMBER means "작성 날짜",
            "${path}.fullDate" type STRING means "스티커 붙인 full date YYYY-MM-DD",
            "${path}.userSticker" type OBJECT means "붙인 스티커 정보" isOptional true,
            "${path}.userSticker.id" type STRING means "id",
            "${path}.userSticker.title" type STRING means "스티커 제목",
            "${path}.userSticker.description" type STRING means "스티커 내용",
            "${path}.userSticker.stickerId" type STRING means "스티커 id",
            "${path}.userSticker.userId" type NUMBER means "작성자 id",
            "${path}.userSticker.date" type STRING means "스티커 붙인 날짜 yyyy-MM-dd",
            "${path}.userSticker.imgUrl" type STRING means "스티커 ImgUrl",
            "${path}.userSticker.tagName" type STRING means "스티커 태그 이름",
            "${path}.userSticker.underLineColor" type STRING means "스티커 밑줄 색",

        )
    }

    fun errorMessageTemplate() : Array<FieldDescriptor> {
        /*"data" : {
            "messages" : "유효한 값이 아닙니다.",
            "error" : "Invalid date format. Please use yyyy-MM",
            "code" : "0002",
            "isShow" : false,
            "alertMessage" : null
        }*/

        return  arrayOf(
            "data" type OBJECT means "에러 메시지",
            "data.messages" type STRING means "메시지",
            "data.error" type STRING means "에러 내용",
            "data.code" type STRING means "에러 코드",
            "data.isShow" type BOOLEAN means "에러 메시지 노출 여부",
            "data.alertMessage" type STRING means "알림 메시지" isOptional true
        )
    }

    fun getPopupResponseData(path: String): Array<FieldDescriptor>{
        /*{
            "id": 375,
            "type": "EVENT",
            "webViewTitle": "최애브랜드 챌린지 당첨자 발표",
            "linkUrl": "",
            "mainImage": {
            "id": 0,
            "url": "https://dftgic7kmxqhe.cloudfront.net/SLIDE/9d0377a4-f090-4a93-967a-ea9751f1aa81.png",
            "filename": ""
        },
            "subImage": {
            "id": 0,
            "url": "https://dftgic7kmxqhe.cloudfront.net/SLIDE/9d0377a4-f090-4a93-967a-ea9751f1aa81.png",
            "filename": ""
        },
            "popupImage": null,
            "numOrder": 10015,
            "createdBy": 4541,
            "startAt": "2024-07-28T15:00:00.000Z",
            "endAt": "2024-08-10T15:00:00.000Z",
            "position": "MAIN",
            "exposure": "BANNER",
            "eventsId": 302,
            "status": "ACTIVE",
            "createdAt": "2024-07-29T01:26:15.000Z",
            "updatedAt": "2024-07-29T01:26:44.000Z"
        },*/
        return arrayOf(
            "${path}.id" type NUMBER means "팝업 id",
            "${path}.type" type STRING means "팝업 타입",
            "${path}.webViewTitle" type STRING means "웹뷰 타이틀",
            "${path}.linkUrl" type STRING means "링크 url",

            "${path}.mainImage" type OBJECT means "메인 이미지" isOptional true,
            "${path}.mainImage.id" type NUMBER means "메인 이미지 id" isOptional true,
            "${path}.mainImage.url" type STRING means "메인 이미지 url" isOptional true,
            "${path}.mainImage.filename" type STRING means "메인 이미지 파일명" isOptional true,

            "${path}.subImage" type OBJECT means "서브 이미지" isOptional true,
            "${path}.subImage.id" type NUMBER means "서브 이미지 id" isOptional true,
            "${path}.subImage.url" type STRING means "서브 이미지 url" isOptional true,
            "${path}.subImage.filename" type STRING means "서브 이미지 파일명" isOptional true,

            "${path}.popupImage" type OBJECT means "팝업 이미지" isOptional true,
            "${path}.popupImage.id" type NUMBER means "팝업 이미지 id" isOptional true,
            "${path}.popupImage.url" type STRING means "팝업 이미지 url" isOptional true,
            "${path}.popupImage.filename" type STRING means "팝업 이미지 파일명" isOptional true,

            "${path}.numOrder" type NUMBER means "순서",
            "${path}.createdBy" type NUMBER means "생성자",
            "${path}.startAt" type STRING means "시작일",
            "${path}.endAt" type STRING means "종료일",
            "${path}.position" type STRING means "위치",
            "${path}.exposure" type STRING means "노출",
            "${path}.eventsId" type NUMBER means "이벤트 id" isOptional true,
            "${path}.status" type STRING means "상태",
            "${path}.createdAt" type STRING means "생성일",
            "${path}.updatedAt" type STRING means "수정일"
        )
    }
    fun getBannerResponseData(path: String): Array<FieldDescriptor>{
        /*{
            "id": 375,
            "type": "EVENT",
            "webViewTitle": "최애브랜드 챌린지 당첨자 발표",
            "linkUrl": "",
            "mainImage": {
            "id": 0,
            "url": "https://dftgic7kmxqhe.cloudfront.net/SLIDE/9d0377a4-f090-4a93-967a-ea9751f1aa81.png",
            "filename": ""
        },
            "subImage": {
            "id": 0,
            "url": "https://dftgic7kmxqhe.cloudfront.net/SLIDE/9d0377a4-f090-4a93-967a-ea9751f1aa81.png",
            "filename": ""
        },
            "popupImage": null,
            "numOrder": 10015,
            "createdBy": 4541,
            "startAt": "2024-07-28T15:00:00.000Z",
            "endAt": "2024-08-10T15:00:00.000Z",
            "position": "MAIN",
            "exposure": "BANNER",
            "eventsId": 302,
            "status": "ACTIVE",
            "createdAt": "2024-07-29T01:26:15.000Z",
            "updatedAt": "2024-07-29T01:26:44.000Z"
        },*/
        return arrayOf(
            "${path}.id" type NUMBER means "팝업 id",
            "${path}.type" type STRING means "팝업 타입",
            "${path}.webViewTitle" type STRING means "웹뷰 타이틀",
            "${path}.linkUrl" type STRING means "링크 url",

            "${path}.mainImage" type OBJECT means "메인 이미지" isOptional true,
            "${path}.mainImage.id" type NUMBER means "메인 이미지 id",
            "${path}.mainImage.url" type STRING means "메인 이미지 url",
            "${path}.mainImage.filename" type STRING means "메인 이미지 파일명",

            "${path}.subImage" type OBJECT means "서브 이미지" isOptional true,
            "${path}.subImage.id" type NUMBER means "서브 이미지 id",
            "${path}.subImage.url" type STRING means "서브 이미지 url",
            "${path}.subImage.filename" type STRING means "서브 이미지 파일명",

            "${path}.popupImage" type OBJECT means "팝업 이미지"  isOptional true,
            "${path}.popupImage.id" type NUMBER means "팝업 이미지 id" isOptional true,
            "${path}.popupImage.url" type STRING means "팝업 이미지 url" isOptional true,
            "${path}.popupImage.filename" type STRING means "팝업 이미지 파일명" isOptional true,

            "${path}.numOrder" type NUMBER means "순서",
            "${path}.createdBy" type NUMBER means "생성자",
            "${path}.startAt" type STRING means "시작일",
            "${path}.endAt" type STRING means "종료일",
            "${path}.position" type STRING means "위치",
            "${path}.exposure" type STRING means "노출",
            "${path}.eventsId" type NUMBER means "이벤트 id" isOptional true,
            "${path}.status" type STRING means "상태",
            "${path}.createdAt" type STRING means "생성일",
            "${path}.updatedAt" type STRING means "수정일"
        )
    }

    fun getCodeData(path: String): Array<FieldDescriptor>{
        return arrayOf(
            "${path}.code" type STRING means "id",
            "${path}.codeName" type STRING means "title",
            "${path}.iconImgUrl" type STRING means "imageUrl"
        )
    }

    fun getPhraseResponseData(path: String): Array<FieldDescriptor>{
        return arrayOf(
            "${path}.text" type STRING means "text",
            "${path}.weight" type STRING means "폰트 weight"
        )
    }


    fun getBestPostResponseData(path: String): Array<FieldDescriptor>{
        return arrayOf(
            "${path}.id" type NUMBER means "id",
            "${path}.description" type STRING means "게시글 내용",
            "${path}.title" type STRING means "게시글 썸네일",

            "${path}.postInfoId" type NUMBER means "postInfoId",
            "${path}.thumbnailImage" type STRING,

            "${path}.owner" type OBJECT isOptional true,
            "${path}.album[]" type ARRAY
        )
    }


    fun getMainWriterResponseData(path: String): Array<FieldDescriptor>{
        /*"id": 21,
        "nickname": "만타박스",
        "avatarUrl": "https://dftgic7kmxqhe.cloudfront.net/PROFILE/925d757f-6583-489d-b09d-7cdfb8ad9334.jpeg",
        "backgroundImg": "https://dftgic7kmxqhe.cloudfront.net/WRITER/f38bbf0e-6ac6-48e8-a078-f7bc2ad40d11.jpeg",
        "goodsList": [
        {
            "id": 50094,
            "writerId": 21,
            "goodsName": "찌그리 데코 키스컷",
            "brandName": "mantabox",
            "url": "https://mhottracks.kyobobook.co.kr/m/gift/detail/2300186438726",
            "salePrice": 6500,
            "salePercent": "0",
            "cmdtCode": "2300186438726",
            "defaultPrice": 6500,
            "createdAt": "2024-07-29T18:01:00.000Z",
            "updatedAt": "2024-07-29T18:01:00.000Z",
            "thumbnailImage": "https://contents.kyobobook.co.kr/sih/fit-in/600x0/gift/pdt/1340/hot1717989074360.jpg"
        },
        {
            "id": 50093,
            "writerId": 21,
            "goodsName": "알파벳 키스컷",
            "brandName": "mantabox",
            "url": "https://mhottracks.kyobobook.co.kr/m/gift/detail/2300186438733",
            "salePrice": 6500,
            "salePercent": "0",
            "cmdtCode": "2300186438733",
            "defaultPrice": 6500,
            "createdAt": "2024-07-29T18:01:00.000Z",
            "updatedAt": "2024-07-29T18:01:00.000Z",
            "thumbnailImage": "https://contents.kyobobook.co.kr/sih/fit-in/600x0/gift/pdt/1113/hot1717988686439.jpg"
        }
        ]*/

        return arrayOf(
            "${path}.id" type NUMBER means "id",
            "${path}.nickname" type STRING means "닉네임",
            "${path}.avatarUrl" type STRING means "아바타 url",
            "${path}.backgroundImg" type STRING means "배경 이미지 url",
            *stickerGoodsOld("${path}.goodsList[]")
        )
    }

    fun goodsManagingWriterGoods(path: String): Array<FieldDescriptor>{
        /*"id": 21,
        "nickname": "만타박스",
        "avatarUrl": "https://dftgic7kmxqhe.cloudfront.net/PROFILE/925d757f-6583-489d-b09d-7cdfb8ad9334.jpeg",
        "backgroundImg": "https://dftgic7kmxqhe.cloudfront.net/WRITER/f38bbf0e-6ac6-48e8-a078-f7bc2ad40d11.jpeg",
        "goodsList": [
        {
            "id": 50094,
            "writerId": 21,
            "goodsName": "찌그리 데코 키스컷",
            "brandName": "mantabox",
            "url": "https://mhottracks.kyobobook.co.kr/m/gift/detail/2300186438726",
            "salePrice": 6500,
            "salePercent": "0",
            "cmdtCode": "2300186438726",
            "defaultPrice": 6500,
            "createdAt": "2024-07-29T18:01:00.000Z",
            "updatedAt": "2024-07-29T18:01:00.000Z",
            "thumbnailImage": "https://contents.kyobobook.co.kr/sih/fit-in/600x0/gift/pdt/1340/hot1717989074360.jpg"
        },
        {
            "id": 50093,
            "writerId": 21,
            "goodsName": "알파벳 키스컷",
            "brandName": "mantabox",
            "url": "https://mhottracks.kyobobook.co.kr/m/gift/detail/2300186438733",
            "salePrice": 6500,
            "salePercent": "0",
            "cmdtCode": "2300186438733",
            "defaultPrice": 6500,
            "createdAt": "2024-07-29T18:01:00.000Z",
            "updatedAt": "2024-07-29T18:01:00.000Z",
            "thumbnailImage": "https://contents.kyobobook.co.kr/sih/fit-in/600x0/gift/pdt/1113/hot1717988686439.jpg"
        }
        ]*/

        return arrayOf(
            "${path}.id" type NUMBER means "id",
            "${path}.title" type STRING means "닉네임",
            "${path}.startAt" type STRING means "아바타 url",
            "${path}.endAt" type STRING means "배경 이미지 url",
            "${path}.orderNum" type NUMBER means "순서",
            *stickerGoodsOld("${path}.writerGoods[]")
        )
    }


    fun getNotiDataResponseData(path: String): Array<FieldDescriptor>{
        return arrayOf(
            "${path}.isReadEvent" type BOOLEAN means "이벤트 알림",
            "${path}.isReadChallenge" type BOOLEAN means "챌린지 알림",
            "${path}.isReadTalk" type BOOLEAN means "톡 알림",
            "${path}.isReadIssue" type BOOLEAN means "이슈 알림"
        )
    }

    fun recomendTag(path: String): Array<FieldDescriptor> {
        return arrayOf(
            "${path}.id" type STRING means "id",
            "${path}.tagName" type STRING means "title",
        )
    }
    fun challenge(path: String): Array<FieldDescriptor> {
        return arrayOf(
            "${path}.id" type STRING means "id",
            "${path}.tagName" type STRING means "title",
        )
    }
}
