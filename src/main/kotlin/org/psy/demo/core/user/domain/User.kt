package org.psy.demo.core.user.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class User(

    val userId: UserId,
    val accountType: AccountType,
    val nickname: String,
    val bio: String,
    val linkShare: String,

    val avatarImg: String,
    val backgroundImg: String?,

    val insights: UserInsights,
    val followedInfo: UserInteractionStatus,

    ) {

    data class UserId(
        @JsonProperty("id")
        val id: String
    )

    data class UserInsights(
        val totalFollower: Int = 0,
        val totalFollowing: Int = 0,
        val totalReported: Int = 0,
        val bio: String = "",
    )

    data class UserInteractionStatus(
        var isFollowed: Boolean?,
    )

    fun withIsFollowed(isFollowed: Boolean): User {
        return this.copy(followedInfo = UserInteractionStatus(isFollowed = isFollowed))
    }

}

enum class AccountType {
    NORMAL, GOOGLE, APPLE, NAVER, KAKAO
}

enum class UserType {
    NORMAL, RECOMMEND
}

enum class Role {
    USER, ADMIN, MANAGER, WRITER
}
