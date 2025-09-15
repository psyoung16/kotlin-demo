package org.psy.demo.core.user.domain

data class Follow(

    val followId: FollowId,
    val followerId: User.UserId,
    val followingId: User.UserId,


    ) {
    data class FollowId(val id: String) {
        companion object {
            fun of(id: String): FollowId = FollowId(id)
        }
    }
}