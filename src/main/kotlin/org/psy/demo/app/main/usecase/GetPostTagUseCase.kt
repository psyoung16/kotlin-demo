package org.psy.demo.app.main.usecase

import org.psy.demo.core.post.domain.Post

interface GetPostTagUseCase {

    fun getPostIdsByPostTag(searchTag: String): List<Post.PostId>


}
