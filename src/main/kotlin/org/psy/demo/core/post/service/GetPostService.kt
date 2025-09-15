package org.psy.demo.core.post.service

import org.psy.demo.common.MetaData
import org.psy.demo.common.PageParam
import org.psy.demo.common.postInfoIdsByFeed
import org.psy.demo.common.postInfoIdsByPosts
import org.psy.demo.infra.jpaEntity.StyleTagType
import org.psy.demo.app.main.usecase.GetPostUseCase
import org.psy.demo.app.sdui.usecase.GetCommunityUIContentsQuery
import org.psy.demo.app.sdui.translator.Tabs
import org.psy.demo.core.post.domain.BestPostWithPagination
import org.psy.demo.core.content.domain.Challenge
import org.psy.demo.core.post.domain.GetCommunityPostQuery
import org.psy.demo.core.post.domain.Post
import org.psy.demo.core.post.domain.PostInteraction
import org.psy.demo.core.post.domain.PostsSimple
import org.psy.demo.core.user.domain.User
import org.psy.demo.infra.repository.BlockRepository
import org.psy.demo.infra.repository.ManageTagRepository
import org.psy.demo.infra.repository.PostInteractionRepository
import org.psy.demo.infra.repository.PostRepository
import org.psy.demo.infra.repository.ReportRepository
import org.psy.demo.infra.repository.UserRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


@Component
@Transactional(readOnly = true)
private class PostService(

    private val loadUserPost: UserRepository,
    private val loadPostPort: PostRepository,
    private val loadReportPort: ReportRepository,
    private val loadPostInteractionPost: PostInteractionRepository,
    private val loadBlockPort: BlockRepository,
    private val loadManageTagPort: ManageTagRepository

) : GetPostUseCase {

    @Cacheable(value = ["getCommunityCuration"], key = "#requestUserId.id + #tabs")
    override fun getCommunityCuration(
        requestUserId: User.UserId,
        tabs: Tabs.TAB_ID
    ): List<Post> {
        val posts: List<Post> = loadPostPort.run {
            when (tabs) {
                Tabs.TAB_ID.FEED -> loadAccessibleUserLikedFeed(requestUserId, PageParam.of(1, 6), postInfoIdsByFeed)
                else -> loadAccessibleUserViewsFeedNow(requestUserId, PageParam.of(1, 2), postInfoIdsByPosts)
            }
        }
        val postCreatedInfos: List<User> = loadUserPost.loadUserByUserIds(posts.map {
            it.createdBy
        })
        return posts.map { post ->
            post.withPostCreatedInfo(postCreatedInfos.find { it.userId == post.createdBy })
        }
    }

    override fun getCoummunityContents(request: GetCommunityUIContentsQuery): List<Post> {
        var posts: List<Post> = loadPostPort.getCommunityPosts(
            GetCommunityPostQuery(
                request.userId,
                request.pageParam,
                request.tabId,
                blockUserId = loadBlockPort.loadBlockUsers(request.userId)
                    .map { User.UserId(it.blockerPersonId.toString()) }, //차단한 사용자들
                reportPostId = loadReportPort.loadReportPosts(request.userId)
                    .mapNotNull { it.reportPostId },  //신고한 PostId
                request.ordering,
                request.postTags,
                request.styleTags
            )
        )

        //게시글들의 user정보 가져오기
        val postCreatedInfos: List<User> = loadUserPost.loadUserByUserIds(posts.map {
            it.createdBy
        })
        //게시글의 interaction info가져오기
        val postInteractions: List<PostInteraction> =
            loadPostInteractionPost.loadPostInteractions(posts.map { it.postId }, request.userId)

        return posts.map { post ->
            val interaction = postInteractions.find { it.postId == post.postId }
            val postCreatedInfo = postCreatedInfos.find { it.userId == post.createdBy }

            post.withPostInteraction(interaction)
                .withPostCreatedInfo(postCreatedInfo)
        }
    }

    override fun getPostsMyLiked(
        requestUserId: User.UserId,
        page: PageParam
    ): List<Post> {
        val posts: List<Post> = loadPostPort.loadMyLikedPosts(requestUserId, page)
        val postCreatedInfos: List<User> = loadUserPost.loadUserByUserIds(posts.map {
            it.createdBy
        })
        return posts.map { post ->
            post.withPostCreatedInfo(postCreatedInfos.find { it.userId == post.createdBy })
        }
    }

    override fun getPostsMyScraped(
        requestUserId: User.UserId,
        page: PageParam
    ): List<Post> {
        val posts: List<Post> = loadPostPort.loadMyScrapPosts(requestUserId, page)
        val postCreatedInfos: List<User> = loadUserPost.loadUserByUserIds(posts.map {
            it.createdBy
        })
        return posts.map { post ->
            post.withPostCreatedInfo(postCreatedInfos.find { it.userId == post.createdBy })
        }
    }


    override fun getPostsWithPostInteractionWithPostCreatedInfo(
        requestUserId: User.UserId,
        targetUserId: User.UserId,
        pageParam: PageParam,
        postInfoIds: List<Int>
    ): List<Post> {

        //내가 신고한거면 안보여야겠지...?
        val reportPostIds: List<Post.PostId> = listOf()
        if (requestUserId != targetUserId) {
            loadReportPort.loadReportPosts(requestUserId).forEach {
                reportPostIds.plus(it.reportPostId)
            }
        }

        val posts: List<Post> =
            loadPostPort.loadRecentUploadFeed(targetUserId, pageParam, reportPostIds, postInfoIds)
        val postInteractions: List<PostInteraction> =
            loadPostInteractionPost.loadPostInteractions(posts.map { it.postId }, requestUserId)
        val postCreatedInfos: List<User> = loadUserPost.loadUserByUserIds(posts.map {
            it.createdBy
        })
        return posts.map { post ->
            post.withPostInteraction(postInteractions.find { it.postId == post.postId })
                .withPostCreatedInfo(postCreatedInfos.find { it.userId == post.createdBy })
        }
    }

    override fun getPostsWithPostInteractionWithPostCreatedInfoOrderingComment(
        requestUserId: User.UserId,
        targetUserId: User.UserId,
        pageParam: PageParam,
        postInfoIds: List<Int>
    ): List<Post> {
        //요청하는 사람이 신고한거면 안보여야겠지...?
        val reportPostIds: List<Post.PostId> = listOf()
        if (requestUserId != targetUserId) {
            loadReportPort.loadReportPosts(requestUserId).forEach {
                reportPostIds.plus(it.reportPostId)
            }
        }

        val posts: List<Post> =
            loadPostPort.loadRecentCommentPost(targetUserId, pageParam, reportPostIds, postInfoIds)
        val postInteractions: List<PostInteraction> =
            loadPostInteractionPost.loadPostInteractions(posts.map { it.postId }, requestUserId)
        val postCreatedInfos: List<User> = loadUserPost.loadUserByUserIds(posts.map {
            it.createdBy
        })

        return posts.map { post ->
            post.withPostInteraction(postInteractions.find { it.postId == post.postId })
                .withPostCreatedInfo(postCreatedInfos.find { it.userId == post.createdBy })
        }
    }

    override fun getPostsWithPostInteractionWithPostCreatedInfo(
        requestUserId: User.UserId,
        page: PageParam,
        postInfoIds: List<Int>,
        postIds: List<Post.PostId>
    ): List<Post> {


        val posts: List<Post> = loadPostPort.loadPostsByPostIds(postIds, page, requestUserId, postInfoIds)
        val postInteractions: List<PostInteraction> =
            loadPostInteractionPost.loadPostInteractions(posts.map { it.postId }, requestUserId)
        val postCreatedInfos: List<User> = loadUserPost.loadUserByUserIds(posts.map {
            it.createdBy
        })

        return posts.map { post ->
            post.withPostInteraction(postInteractions.find { it.postId == post.postId })
                .withPostCreatedInfo(postCreatedInfos.find { it.userId == post.createdBy })
        }
    }

    override fun getStyleTagPostWithPostInteractionWithPostCreatedInfo(
        requestUserId: User.UserId,
        styleTag: StyleTagType,
        page: PageParam,
    ): List<Post> {

        val posts: List<Post> = loadPostPort.loadPostsByStyleTag(requestUserId, styleTag, page)
        val postInteractions: List<PostInteraction> =
            loadPostInteractionPost.loadPostInteractions(posts.map { it.postId }, requestUserId)
        val postCreatedInfos: List<User> = loadUserPost.loadUserByUserIds(posts.map {
            it.createdBy
        })

        return posts.map { post ->
            post.withPostInteraction(postInteractions.find { it.postId == post.postId })
                .withPostCreatedInfo(postCreatedInfos.find { it.userId == post.createdBy })
        }

    }

    override fun mainBestPost(requestUserId: User.UserId): BestPostWithPagination {
        val pageParam = PageParam.of(1, 6)
        return loadPostPort.getMainBestPostList(requestUserId, pageParam).let { bestPosts ->
            BestPostWithPagination(
                bestPosts,
                MetaData.of(pageParam.page, pageParam.size, bestPosts.size.toLong())
            )
        }
    }

    override fun getPostsByChallenge(challenge: Challenge): List<PostsSimple> {
        return loadPostPort.getPostsByChallenge(
            challenge.primaryTag.orEmpty(), loadManageTagPort.loadManageTagByChallenge(challenge.id).map { it.tagName },
            challenge.startAt, challenge.endAt
        )
    }

}
