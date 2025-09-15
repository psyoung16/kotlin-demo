package org.psy.demo.app.main.usecase

import org.psy.demo.common.PageParam
import org.psy.demo.infra.jpaEntity.StyleTagType
import org.psy.demo.app.sdui.usecase.GetCommunityUIContentsQuery
import org.psy.demo.app.sdui.translator.Tabs
import org.psy.demo.core.post.domain.BestPostWithPagination
import org.psy.demo.core.content.domain.Challenge
import org.psy.demo.core.post.domain.Post
import org.psy.demo.core.post.domain.PostsSimple
import org.psy.demo.core.user.domain.User

interface GetPostUseCase {

    //curation은 별도로 분리하는게 좋아보임
    fun getCommunityCuration(
        requestUserId: User.UserId,
        tabs: Tabs.TAB_ID
    ): List<Post>

    fun getCoummunityContents(
        request: GetCommunityUIContentsQuery
    ): List<Post>


    fun getPostsMyLiked(
        requestUserId: User.UserId,
        page: PageParam
    ): List<Post>

    fun getPostsMyScraped(
        requestUserId: User.UserId,
        page: PageParam
    ): List<Post>


    fun getPostsWithPostInteractionWithPostCreatedInfo(
        requestUserId: User.UserId,
        targetUserId: User.UserId,
        page: PageParam,
        postInfoIds: List<Int>
    ): List<Post>

    fun getPostsWithPostInteractionWithPostCreatedInfoOrderingComment(
        requestUserId: User.UserId,
        targetUserId: User.UserId,
        page: PageParam,
        postInfoIds: List<Int>
    ): List<Post>

    fun getPostsWithPostInteractionWithPostCreatedInfo(
        requestUserId: User.UserId,
        page: PageParam,
        postInfoIds: List<Int>,
        postIds: List<Post.PostId>
    ): List<Post>

    fun getStyleTagPostWithPostInteractionWithPostCreatedInfo(
        requestUserId: User.UserId,
        styleTag: StyleTagType,
        page: PageParam
    ): List<Post>

    fun mainBestPost(
        requestUserId: User.UserId,
    ): BestPostWithPagination

    fun getPostsByChallenge(challenge: Challenge
    ): List<PostsSimple>


}
