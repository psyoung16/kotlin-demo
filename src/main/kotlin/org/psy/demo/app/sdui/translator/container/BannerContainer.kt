package org.psy.demo.app.sdui.translator.container

import org.psy.demo.core.vo.ContentLinkType

data class BannerContainer(
    val id: String,
    val subId: String?,
    val contentLinkType: ContentLinkType, //배너 컨텐츠 타입
    val imageUrl: String, //이미지 Url
    val url: String,
)
