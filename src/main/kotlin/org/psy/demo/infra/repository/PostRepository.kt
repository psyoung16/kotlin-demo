package org.psy.demo.infra.repository

import org.psy.demo.common.*
import org.psy.demo.infra.mapper.PostMapper
import org.psy.demo.infra.jpaEntity.PostJpaEntity
import org.psy.demo.infra.jpaEntity.StyleTagType
import org.psy.demo.app.sdui.translator.Tabs
import org.psy.demo.app.sdui.translator.container.CommunityUIOrdering
import org.psy.demo.core.post.domain.BestPost
import org.psy.demo.core.post.domain.GetCommunityPostQuery
import org.psy.demo.core.post.domain.Post
import org.psy.demo.core.post.domain.PostsSimple
import org.psy.demo.core.user.domain.User
import org.psy.demo.infra.jpaRepository.PostJpaRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import java.util.*
import org.springframework.data.domain.Sort
import kotlin.collections.isNotEmpty
import kotlin.collections.map

@Component
class PostRepository(
    private val postJpaRepository: PostJpaRepository
) {
    fun getMainBestPostList(userId: User.UserId?, param: PageParam): List<BestPost> {
        val postList: List<PostJpaEntity> = postJpaRepository.findMainBestPosts(Date(), Date(), param.toPageRequest())

        //없으면 한번더 조회 //db에 제대로 저장 안되었을 경우 대비 //주로 개발서버에서 사용됨
        if (postList.isEmpty()) {
            val postSubList: List<PostJpaEntity> = postJpaRepository.findMainBestPostsReplace(param.toPageRequest())
            return postSubList.map { PostJpaEntity.mapToDomain(it) }
        }

        //list에서 썸네일이 null(아직 안만들어진 경우에는 안가져옴)
        //대신 상세 조회할 때는 썸네일이 null인경우 따로 만들어서 가져옴
        return postList.map(PostJpaEntity::mapToDomain)
    }

    fun getPostsByChallenge(primaryTag: String, tagName: List<String>, startAt: Date, endAt: Date): List<PostsSimple> {
        //postDAta
        val postList: List<PostJpaEntity> = postJpaRepository.findByTagListAndStartAtAndEndAt(
            primaryTag, tagName,
            startAt, endAt,
            PageRequest.of(0, 12)
        )

        return postList.map(PostJpaEntity::mapToPostRes)
    }

    fun getCommunityPosts(
        command: GetCommunityPostQuery
    ): List<Post> {

        val ordering = command.ordering
        val postTags = command.postTags
        val styleTags = command.styleTags

        val blockUserIds = if (command.blockUserId.isNotEmpty()) command.blockUserId.map { it.id.toLong() } else listOf(0L)
        val reportPostIds = if (command.reportPostId.isNotEmpty()) command.reportPostId.map { it.id.toLongOrNull() ?: 0L } else listOf(0L)
        val pageRequest = when (command.ordering) {
            CommunityUIOrdering.NEW -> command.pageParam.copy(sort = Sort.by(Sort.Direction.DESC, "createdAt"))
            CommunityUIOrdering.POPULAR -> command.pageParam.copy(sort = Sort.by(Sort.Direction.DESC, "totalLikes"))
            CommunityUIOrdering.FOLLOWING -> command.pageParam.copy(sort = Sort.by(Sort.Direction.DESC, "createdAt"))
        }.toPageRequest()

        val postInfoIds: List<Int> = when (command.tabId) {
            Tabs.TAB_ID.FEED -> postInfoIdsByFeed
            else -> postInfoIdsByPosts
        }

        return when {
            ordering == CommunityUIOrdering.FOLLOWING && styleTags.isEmpty() && postTags.isEmpty() -> {
                postJpaRepository.findCommunityPostsOrderingFollow(
                    postInfoIds, blockUserIds, reportPostIds, command.userId.id.toLong(), pageRequest
                )
            }

            ordering == CommunityUIOrdering.FOLLOWING && styleTags.isNotEmpty() && postTags.isEmpty() -> {
                postJpaRepository.findCommunityPostsByStyleTagOrderingFollow(
                    postInfoIds, blockUserIds, reportPostIds, styleTags, command.userId.id.toLong(), pageRequest
                )
            }

            ordering == CommunityUIOrdering.FOLLOWING && styleTags.isEmpty() && postTags.isNotEmpty() -> {
                postJpaRepository.findCommunityPostsByPostTagOrderingFollow(
                    postInfoIds, blockUserIds, reportPostIds, postTags, command.userId.id.toLong(), pageRequest
                )
            }

            ordering == CommunityUIOrdering.FOLLOWING && styleTags.isNotEmpty() && postTags.isNotEmpty() -> {
                postJpaRepository.findCommunityPostsByPostTagAndStyleTagOrderingFollow(
                    postInfoIds, blockUserIds, reportPostIds, postTags, styleTags, command.userId.id.toLong(), pageRequest
                )
            }

            ordering == CommunityUIOrdering.POPULAR && styleTags.isEmpty() && postTags.isEmpty() -> {
                postJpaRepository.findCommunityPostsOrderingPopular(
                    postInfoIds, blockUserIds, reportPostIds, pageRequest,
                    Date().of("2024-01-01")
                )
            }

            ordering == CommunityUIOrdering.POPULAR && styleTags.isNotEmpty() && postTags.isEmpty() -> {
                postJpaRepository.findCommunityPostsByStyleTagOrderingPopular(
                    postInfoIds, blockUserIds, reportPostIds, styleTags, pageRequest, Date().of("2024-01-01")
                )
            }

            ordering == CommunityUIOrdering.POPULAR && styleTags.isEmpty() && postTags.isNotEmpty() -> {
                postJpaRepository.findCommunityPostsByPostTagOrderingPopular(
                    postInfoIds, blockUserIds, reportPostIds, postTags, pageRequest, Date().of("2024-01-01")
                )
            }

            ordering == CommunityUIOrdering.POPULAR && styleTags.isNotEmpty() && postTags.isNotEmpty() -> {
                postJpaRepository.findCommunityPostsByPostTagAndStyleTagOrderingPopular(
                    postInfoIds, blockUserIds, reportPostIds, postTags, styleTags, pageRequest, Date().of("2024-01-01")
                )
            }

            styleTags.isNotEmpty() && postTags.isEmpty() -> {
                postJpaRepository.findCommunityPostsByStyleTag(
                    postInfoIds, blockUserIds, reportPostIds, styleTags, pageRequest
                )
            }

            styleTags.isEmpty() && postTags.isNotEmpty() -> {
                postJpaRepository.findCommunityPostsByPostTag(
                    postInfoIds, blockUserIds, reportPostIds, postTags, pageRequest
                )
            }

            styleTags.isNotEmpty() -> {
                postJpaRepository.findCommunityPostsByPostTagAndStyleTag(
                    postInfoIds, blockUserIds, reportPostIds, postTags, styleTags, pageRequest
                )
            }

            else -> {
                postJpaRepository.findCommunityPosts(
                    postInfoIds, blockUserIds, reportPostIds, pageRequest
                )
            }
        }.map(PostMapper::mapToDomain)
    }

    fun loadUserLikedFeedPosts(
        userId: User.UserId?,
        param: PageParam,
        blockUserId: List<User.UserId>,
        reportPostId: List<Post.PostId>
    ): List<Post> {
        val date = Calendar.getInstance()   // 19-01-2018
        date.add(Calendar.DATE, -7)

        val postList: List<PostJpaEntity> = postJpaRepository.findUserLikedFeedPost(
            postInfoIds = listOf(1, 4, 6),
            blockUserIds = if (blockUserId.isNotEmpty()) blockUserId.map { it.id.toLong() } else listOf(0L), //not in이니까 사용 가능한 방법ㅂ....,
            reportPostIds = if (reportPostId.isNotEmpty()) reportPostId.map { it.id.toLongOrNull() ?: 0L } else listOf(0L), // 더 발전되면
            date.time,
            param.toPageRequest())
        return postList.map(PostMapper::mapToDomain)
    }

    fun loadAccessibleUserLikedFeed(
        userId: User.UserId?,
        param: PageParam,
        postInfoIds: List<Int>
    ): List<Post> {
        //utc 체크 필요

        val calendar = Calendar.getInstance()  // 현재 날짜와 시간
        calendar.add(Calendar.DATE, -7)        // 7일 전으로 설정
        val date = calendar.time
        val utcDate = date

        return when (userId) {
            null -> {
                postJpaRepository.findAccessibleUserLikedFeed(
                    postInfoIds,
                    utcDate,
                    param.toPageRequest()
                )
            }

            else -> {
                postJpaRepository.findAccessibleUserLikedFeed(
                    postInfoIds,
                    utcDate,
                    userId = userId.id.toLong(),
                    param.toPageRequest()
                )
            }
        }.map(PostMapper::mapToDomain)
    }

    fun loadAccessibleUserViewsFeedNow(
        userId: User.UserId?,
        param: PageParam,
        postInfoIds: List<Int>
    ): List<Post> {
        val calendar = Calendar.getInstance()  // 현재 날짜와 시간
        calendar.add(Calendar.DATE, -7)        // 7일 전으로 설정
        val date = calendar.time
        val utcDate = date

        return when (userId) {
            null -> {
                postJpaRepository.findAccessibleUserViewFeed(
                    postInfoIds,
                    utcDate,
                    param.toPageRequest()
                )
            }

            else -> {
                postJpaRepository.findAccessibleUserViewedFeed(
                    postInfoIds,
                    utcDate,
                    userId = userId.id.toLong(),
                    param.toPageRequest()
                )
            }
        }.map(PostMapper::mapToDomain)
    }

    fun loadRecentUploadFeed(
        userId: User.UserId,
        param: PageParam,
        reportPostId: List<Post.PostId>,
        postInfoIds: List<Int>
    ): List<Post> {
        val postList: List<PostJpaEntity> = postJpaRepository.findRecentUserPost(
            postInfoIds = postInfoIds,
            createdBy = userId.id.toLong(),
            reportPostIds = if (reportPostId.isNotEmpty()) reportPostId.map { it.id.toLongOrNull() ?: 0L } else listOf(0L), // 더 발전되면
            param.toPageRequest())
        return postList.map(PostMapper::mapToDomain)
    }

    fun loadRecentCommentPost(
        userId: User.UserId,
        param: PageParam,
        reportPostId: List<Post.PostId>,
        postInfoIds: List<Int>
    ): List<Post> {
        val postList: List<PostJpaEntity> = postJpaRepository.findRecentCommentPost(
            postInfoIds = postInfoIds,
            createdBy = userId.id.toLong(),
            reportPostIds = if (reportPostId.isNotEmpty()) reportPostId.map { it.id.toLongOrNull() ?: 0L } else listOf(0L), // 더 발전되면
            param.toPageRequest())
        return postList.map(PostMapper::mapToDomain)
    }

    fun loadPostsByPostIds(
        postIds: List<Post.PostId>,
        pageParam: PageParam,
        userId: User.UserId,
        postInfoIds: List<Int>,
    ): List<Post> {

        val postList: List<PostJpaEntity> = postJpaRepository.findAccessibleFeedSelectPostAndNotKkoommingTalk(
            postInfoIds = postInfoIds,
            userId = userId.id.toLong(),
            postIds = postIds.map { it.id.toLongOrNull() ?: 0L },
            pageParam.toPageRequest()
        )
        return postList.map(PostMapper::mapToDomain)

    }

    fun loadPostsByStyleTag(
        userId: User.UserId,
        styleTag: StyleTagType,
        pageParam: PageParam
    ): List<Post> {
        val postList: List<PostJpaEntity> = postJpaRepository.findPostsByStyleTag(
            styleTag = styleTag,
            pageParam.toPageRequest()
        )
        return postList.map(PostMapper::mapToDomain)
    }

    fun loadMyLikedPosts(
        userId: User.UserId,
        pageParam: PageParam
    ): List<Post> {
        val postList: List<PostJpaEntity> = postJpaRepository.findMyLikedPosts(
            userId = userId.id.toLong(),
            pageParam.toPageRequest()
        )
        return postList.map(PostMapper::mapToDomain)
    }

    fun loadMyScrapPosts(
        userId: User.UserId,
        pageParam: PageParam
    ): List<Post> {
        val postList: List<PostJpaEntity> = postJpaRepository.findMyScrapPosts(
            userId = userId.id.toLong(),
            pageParam.toPageRequest()
        )
        return postList.map(PostMapper::mapToDomain)
    }


}
