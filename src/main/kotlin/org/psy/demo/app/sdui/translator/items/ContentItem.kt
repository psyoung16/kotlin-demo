package org.psy.demo.app.sdui.translator.items


enum class PostCategory {
    FEED, // 피드
    STAR, // 일러스트
    POST_GOODS, // 굿즈
    ISSUE, // 이슈
    ETC, // 잡담
    VOTE; // 투표

    companion object {
        fun of(postInfoId: String): PostCategory {
            return when (postInfoId) {
                "1" -> FEED
                "2" -> VOTE
                "3" -> ETC
                "4" -> STAR
                "5" -> ISSUE
                "6" -> POST_GOODS
                else -> FEED
            }
        }
    }
}

