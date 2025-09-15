package org.psy.demo.core.post.domain

data class Writer(
        val id: Long,
        val nickname: String,
        val instarLink: String,
        val facebookLink: String,
        val twitterLink: String,
        val blogLink: String,
        val youtubeLink: String,
        val bio: String,
        val status: WriterStatus,
        val email: String,
        // val userType: UserType,
        val avatarUrl: String,
        val backgroundImg: String)
data class WriterId(val id: String) {
    companion object {
//        fun of(id: String): WriterId = WriterId(id)
    }
}
enum class WriterStatus {
    ACTIVE, DEACTIVATED, BLOCKED, REMOVED
}
