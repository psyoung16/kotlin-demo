package org.psy.demo.core.post.domain

import org.psy.demo.common.MetaData

//main 전용, 추후 Post나 simplePost로 변경필요..

data class BestPost(
        val id: Long,
        val description: String,
        val title: String,
        val postInfoId: Int,
        val thumbnailImage: String,
        val owner: Owner?,
        val album: List<String>

){
    data class Owner(
            val id: Int,
            val accountType: String,
            val nickname: String,
            val memberID: String,
            val role: String,
            val linkShare: String,
            val bio: String,
            val isOnline: Boolean,
            val avatar: String
    )
}

data class BestPostWithPagination(
        val posts: List<BestPost>,
        val metadata: MetaData
) {
}
