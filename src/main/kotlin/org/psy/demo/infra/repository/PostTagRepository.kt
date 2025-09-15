package org.psy.demo.infra.repository

import org.psy.demo.infra.jpaEntity.PostTagJpaEntity
import org.psy.demo.core.post.domain.Post
import org.psy.demo.infra.jpaRepository.PostTagJpaRepository
import org.springframework.stereotype.Component

@Component
class PostTagRepository (
    private val postTagJpaRepository: PostTagJpaRepository
){

    fun getPostIdsByPostTag(searchTag: String): List<Post.PostId> {
        val postTagJpaEntity: List<PostTagJpaEntity> = postTagJpaRepository.findByTag(searchTag)
        return postTagJpaEntity.map { Post.PostId(it.getPostId().toString()) }
    }


}
