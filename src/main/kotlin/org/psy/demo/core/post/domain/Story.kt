package org.psy.demo.core.post.domain

data class Story (
    val storyId: StoryId,
    val title: String,
    val description: String,

    val thumbnailUrl: String,


    val storyPosts: List<Post>
){
    data class StoryId(val id: String) {
        companion object {
            fun of(id: String): StoryId = StoryId(id)
        }
    }

    fun withPosts(posts: List<Post>): Story {
        return this.copy(storyPosts = posts)
    }

    fun withThumbnail(url: String): Story {
        return this.copy(thumbnailUrl = url)
    }

}