package org.psy.demo.core.post.service

import org.psy.demo.app.main.usecase.GetPostTagUseCase
import org.psy.demo.core.post.domain.Post
import org.psy.demo.infra.repository.PostTagRepository
import org.springframework.stereotype.Service


@Service
private class PostTagService(
    private val loadPostTagPort: PostTagRepository,

    ) : GetPostTagUseCase {
    override fun getPostIdsByPostTag(searchTag: String): List<Post.PostId> {
        return loadPostTagPort.getPostIdsByPostTag(searchTag)
    }
}
