package org.psy.demo.infra.jpaRepository

import org.psy.demo.infra.jpaEntity.PostJpaEntity
import org.psy.demo.infra.jpaEntity.ScrapJpaEntity
import org.psy.demo.infra.jpaEntity.StyleTagType
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface PostJpaRepository : JpaRepository<PostJpaEntity, Long>{


    @Query(value="""
                select p from PostJpaEntity p where p.status = 'ACTIVE' and p.createdBy = :userId 
            
            """)
    fun getMyPostsActive(userId: Long?): List<PostJpaEntity?>? //offset 하고

    @Query(value="""
                select count(p) from PostJpaEntity p where p.status = 'ACTIVE' and p.createdBy = :userId            
                """)
    fun getMyPostsActiveCount(userId: Long?): Int //offset 하고

    @Query(value="""
                select p from PostJpaEntity p where p.status = 'ACTIVE' and p.createdBy = :userId
                and p.postInfoId = :postInfoId 
            
            """)
    fun getMyPostsActiveCountByPostInfoType(userId: Long?, postInfoId: Int): List<PostJpaEntity?>? //offset 하고

    @Query(value="""
                select count(p) from PostJpaEntity p where p.status = 'ACTIVE' and p.createdBy = :userId            
                and p.postInfoId = :postInfoId
                
                """)
    fun getMyPostsActiveCountPostInfoType(userId: Long?, postInfoId: Int): Int //offset 하고

    @Query(value="""
                        select p from PostJpaEntity p
                        join ContentManagingJpaEntity cm on cm.postId = p.id
                        where cm.type = 'FEED'
                        and cm.startAt <= :startAt 
                        and cm.endAt >= :endAt
                        and p.status = 'ACTIVE' 
                        and p.thumbnail is not null
                        and cm.cmmmId = 1
                        order by cm.createdAt desc
                    """) //cmmmId 고정으로 일단 사용
    fun findMainBestPosts(startAt: Date, endAt: Date,  pageRequest: PageRequest?): List<PostJpaEntity>
    @Query(value="""
                        select p from PostJpaEntity p
                        join ContentManagingJpaEntity cm on cm.postId = p.id
                        where cm.type = 'FEED'
                        and p.status = 'ACTIVE' 
                        and p.thumbnail is not null
                        order by cm.createdAt desc
                    """)
    fun findMainBestPostsReplace(pageRequest: PageRequest?): List<PostJpaEntity>

    @Query(value="""
                        select p from PostJpaEntity p
                        where p.id in (
                            select pt.postId from PostTagJpaEntity pt 
                            join TagJpaEntity t on t.id = pt.tagId
                            where t.tagName in :tagList 
                        ) and p.id in (
                            select pt.postId from PostTagJpaEntity pt 
                            join TagJpaEntity t on t.id = pt.tagId
                            where t.tagName = :primaryTag 
                        ) 
                        and p.status = 'ACTIVE' 
                        and p.postInfoId in (1,4,6) 
                        order by p.createdAt desc 
                    
                    """)
    fun findByTagList(primaryTag: String, tagList: List<String?>?, pageRequest: PageRequest?): List<PostJpaEntity> //offset 하고
    @Query(value="""
                        select p from PostJpaEntity p
                        where p.id in (
                            select pt.postId from PostTagJpaEntity pt 
                            join TagJpaEntity t on t.id = pt.tagId
                            where t.tagName in :tagList 
                        ) and p.id in (
                            select pt.postId from PostTagJpaEntity pt 
                            join TagJpaEntity t on t.id = pt.tagId
                            where t.tagName = :primaryTag 
                        ) 
                        and p.status = 'ACTIVE' 
                        and p.postInfoId in (1,4,6) 
                        and p.createdAt >= :startAt
                        and p.createdAt <= :endAt
                        order by p.createdAt desc 
                    
                    """)
    fun findByTagListAndStartAtAndEndAt(primaryTag: String, tagList: List<String?>?, startAt: Date, endAt: Date, pageRequest: PageRequest?): List<PostJpaEntity> //offset 하고





    @Query(value="""
                        select p from PostJpaEntity p
                        where p.postInfoId in :postInfoIds
                        and p.createdBy not in :blockUserIds
                        and p.id not in :reportPostIds
                        and p.isKkommingTalk = false 
                        and p.status = 'ACTIVE' 
                    """)
    fun findCommunityPosts( postInfoIds: List<Int>, blockUserIds: List<Long>,
                                   reportPostIds: List<Long>, pageRequest: PageRequest): List<PostJpaEntity>
    @Query(value="""
                        select p from PostJpaEntity p
                        join StyleTagJpaEntity st on st.postId = p.id
                        where p.postInfoId in :postInfoIds
                        and p.createdBy not in :blockUserIds
                        and p.id not in :reportPostIds
                        and p.status = 'ACTIVE' 
                        and p.isKkommingTalk = false
                        and st.type in :styleTags
                    """)
    fun findCommunityPostsByStyleTag( postInfoIds: List<Int>, blockUserIds: List<Long>,
                                   reportPostIds: List<Long>,
                                   styleTags: List<String>,
                                                       pageRequest: PageRequest): List<PostJpaEntity>
    @Query(value="""
                        select p from PostJpaEntity p
                        join PostTagJpaEntity pt on pt.postId = p.id
                        join TagJpaEntity t on t.id = pt.tagId
                        where p.postInfoId in :postInfoIds
                        and p.createdBy not in :blockUserIds
                        and p.id not in :reportPostIds
                        and p.status = 'ACTIVE'
                        and p.isKkommingTalk = false
                        and t.tagName in :postTags
                    """)
    fun findCommunityPostsByPostTag( postInfoIds: List<Int>, blockUserIds: List<Long>,
                                   reportPostIds: List<Long>,
                                   postTags: List<String>,
                                                       pageRequest: PageRequest): List<PostJpaEntity>
    @Query(value="""
                        select p from PostJpaEntity p
                        join PostTagJpaEntity pt on pt.postId = p.id
                        join TagJpaEntity t on t.id = pt.tagId
                        join StyleTagJpaEntity st on st.postId = p.id
                        where p.postInfoId in :postInfoIds
                        and p.createdBy not in :blockUserIds
                        and p.id not in :reportPostIds
                        and p.status = 'ACTIVE' 
                        and st.type in :styleTags
                        and p.isKkommingTalk = false
                        and t.tagName in :postTags
                    """)
    fun findCommunityPostsByPostTagAndStyleTag( postInfoIds: List<Int>, blockUserIds: List<Long>,
                                   reportPostIds: List<Long>,
                                   postTags: List<String>, styleTags: List<String>,
                                                       pageRequest: PageRequest): List<PostJpaEntity>
    //
    @Query(value="""
                        select p from PostJpaEntity p
                        where p.postInfoId in :postInfoIds
                        and p.createdBy not in :blockUserIds
                        and p.id not in :reportPostIds
                        and p.status = 'ACTIVE'
                         and p.isKkommingTalk = false
                        and p.createdAt > :startAt
                    """)
    fun findCommunityPostsOrderingPopular( postInfoIds: List<Int>, blockUserIds: List<Long>,
                                   reportPostIds: List<Long>, pageRequest: PageRequest, startAt: Date): List<PostJpaEntity>
    @Query(value="""
                        select p from PostJpaEntity p
                        join StyleTagJpaEntity st on st.postId = p.id
                        where p.postInfoId in :postInfoIds
                        and p.createdBy not in :blockUserIds
                        and p.id not in :reportPostIds
                        and p.status = 'ACTIVE' 
                        and st.type in :styleTags
                        and p.isKkommingTalk = false
                        and p.createdAt > :startAt
                    """)
    fun findCommunityPostsByStyleTagOrderingPopular( postInfoIds: List<Int>, blockUserIds: List<Long>,
                                   reportPostIds: List<Long>,
                                   styleTags: List<String>,
                                                       pageRequest: PageRequest, startAt: Date): List<PostJpaEntity>
    @Query(value="""
                        select p from PostJpaEntity p
                        join PostTagJpaEntity pt on pt.postId = p.id
                        join TagJpaEntity t on t.id = pt.tagId
                        where p.postInfoId in :postInfoIds
                        and p.createdBy not in :blockUserIds
                        and p.id not in :reportPostIds
                        and p.status = 'ACTIVE'
                        and t.tagName in :postTags
                        and p.isKkommingTalk = false
                        and p.createdAt > :startAt
                    """)
    fun findCommunityPostsByPostTagOrderingPopular( postInfoIds: List<Int>, blockUserIds: List<Long>,
                                   reportPostIds: List<Long>,
                                   postTags: List<String>,
                                                       pageRequest: PageRequest, startAt: Date): List<PostJpaEntity>
    @Query(value="""
                        select p from PostJpaEntity p
                        join PostTagJpaEntity pt on pt.postId = p.id
                        join TagJpaEntity t on t.id = pt.tagId
                        join StyleTagJpaEntity st on st.postId = p.id
                        where p.postInfoId in :postInfoIds
                        and p.createdBy not in :blockUserIds
                        and p.id not in :reportPostIds
                        and p.status = 'ACTIVE' 
                        and st.type in :styleTags
                        and t.tagName in :postTags
                        and p.isKkommingTalk = false
                        and p.createdAt > :startAt
                    """)
    fun findCommunityPostsByPostTagAndStyleTagOrderingPopular( postInfoIds: List<Int>, blockUserIds: List<Long>,
                                   reportPostIds: List<Long>,
                                   postTags: List<String>, styleTags: List<String>,
                                                       pageRequest: PageRequest, startAt: Date): List<PostJpaEntity>
    //

    @Query(value="""
                        select p from PostJpaEntity p
                        join FollowJpaEntity f on f.followingId = p.createdBy
                        where p.postInfoId in :postInfoIds
                        and p.createdBy not in :blockUserIds
                        and p.id not in :reportPostIds
                        and p.status = 'ACTIVE'
                        and f.followerId = :userId
                        and p.isKkommingTalk = false
                        and f.status = 'ACTIVE'
                    """)
    fun findCommunityPostsOrderingFollow( postInfoIds: List<Int>, blockUserIds: List<Long>,
                                   reportPostIds: List<Long>, userId: Long,
                                          pageRequest: PageRequest): List<PostJpaEntity>

    @Query(value="""
                        select p from PostJpaEntity p
                        join FollowJpaEntity f on f.followingId = p.createdBy
                        join StyleTagJpaEntity st on st.postId = p.id
                        where p.postInfoId in :postInfoIds
                        and p.createdBy not in :blockUserIds
                        and p.id not in :reportPostIds
                        and p.status = 'ACTIVE'
                        and st.type in :styleTags
                        and f.followerId = :userId
                        and p.isKkommingTalk = false
                        and f.status = 'ACTIVE'
                    """)
    fun findCommunityPostsByStyleTagOrderingFollow(postInfoIds: List<Int>, blockUserIds: List<Long>,
                                                          reportPostIds: List<Long>, styleTags: List<String>, userId: Long,pageRequest: PageRequest): List<PostJpaEntity>

    @Query(value="""
                        select p from PostJpaEntity p
                        join FollowJpaEntity f on f.followingId = p.createdBy
                        join PostTagJpaEntity pt on pt.postId = p.id
                        join TagJpaEntity t on t.id = pt.tagId
                        where p.postInfoId in :postInfoIds
                        and p.createdBy not in :blockUserIds
                        and p.id not in :reportPostIds
                        and p.status = 'ACTIVE'
                        and t.tagName in :postTags
                        and f.followerId = :userId
                        and p.isKkommingTalk = false
                        and f.status = 'ACTIVE'
                    """)
    fun findCommunityPostsByPostTagOrderingFollow( postInfoIds: List<Int>, blockUserIds: List<Long>,
                                   reportPostIds: List<Long>, postTags: List<String>, userId: Long,pageRequest: PageRequest): List<PostJpaEntity>

    @Query(value="""
                        select p from PostJpaEntity p
                        join FollowJpaEntity f on f.followingId = p.createdBy
                        join PostTagJpaEntity pt on pt.postId = p.id
                        join TagJpaEntity t on t.id = pt.tagId
                        join StyleTagJpaEntity st on st.postId = p.id
                        where p.postInfoId in :postInfoIds
                        and p.createdBy not in :blockUserIds
                        and p.id not in :reportPostIds
                        and p.status = 'ACTIVE'
                        and t.tagName in :postTags
                        and st.type in :styleTags
                        and f.followerId = :userId
                        and p.isKkommingTalk = false
                        and f.status = 'ACTIVE'
                    """)
    fun findCommunityPostsByPostTagAndStyleTagOrderingFollow(postInfoIds: List<Int>, blockUserIds: List<Long>,reportPostIds: List<Long>, postTags: List<String>, styleTags: List<String>,  userId: Long,pageRequest: PageRequest): List<PostJpaEntity>


    @Query(value="""
                        select p from PostJpaEntity p
                        where p.postInfoId in :postInfoIds
                        and p.createdBy not in :blockUserIds
                        and p.id not in :reportPostIds
                        and p.status = 'ACTIVE'
                        and p.createdAt > :createdAtLimit
                        order by p.totalLikes desc, p.createdAt desc
                    """)
    fun findUserLikedFeedPost(postInfoIds: List<Int>, blockUserIds: List<Long>,
                              reportPostIds: List<Long>, createdAtLimit: Date, pageRequest: PageRequest): List<PostJpaEntity>

    @Query(value="""
                        select p 
                            from PostJpaEntity p
                            left join BlockJpaEntity b on p.createdBy = b.blockedPersonId 
                                and b.status = 'ACTIVE' 
                                and b.blockerId = :userId
                            left join ReportJpaEntity r on p.id = r.reportPostId 
                                and r.reportType = 'POST' 
                                and r.status = 'ACTIVE' 
                                and r.reporterId = :userId
                            where p.postInfoId in :postInfoIds
                                and b.blockedPersonId is null
                                and r.reportPostId is null
                                and p.status = 'ACTIVE'
                                and p.createdAt > :createdAtLimit
                            order by p.totalLikes desc, p.createdAt desc
                    """)
    fun findAccessibleUserLikedFeed(postInfoIds: List<Int>, createdAtLimit: Date, userId: Long, pageRequest: PageRequest): List<PostJpaEntity>

    @Query(value="""
                        select p 
                            from PostJpaEntity p
                            left join BlockJpaEntity b on p.createdBy = b.blockedPersonId 
                                and b.status = 'ACTIVE' 
                                and b.blockerId = :userId
                            left join ReportJpaEntity r on p.id = r.reportPostId 
                                and r.reportType = 'POST' 
                                and r.status = 'ACTIVE' 
                                and r.reporterId = :userId
                            where p.postInfoId in :postInfoIds
                                and b.blockedPersonId is null
                                and r.reportPostId is null
                                and p.status = 'ACTIVE'
                                and p.createdAt > :createdAtLimit
                            order by p.totalViews desc, p.createdAt desc
                    """)
    fun findAccessibleUserViewedFeed(postInfoIds: List<Int>, createdAtLimit: Date, userId: Long, pageRequest: PageRequest): List<PostJpaEntity>

    @Query(value="""
                        select p 
                            from PostJpaEntity p
                            left join BlockJpaEntity b on p.createdBy = b.blockedPersonId 
                                and b.status = 'ACTIVE' 
                                and b.blockerId = :userId
                            left join ReportJpaEntity r on p.id = r.reportPostId 
                                and r.reportType = 'POST' 
                                and r.status = 'ACTIVE' 
                                and r.reporterId = :userId
                            where p.postInfoId in :postInfoIds
                                and b.blockedPersonId is null
                                and r.reportPostId is null
                                and p.status = 'ACTIVE'
                                and p.createdAt > :createdAtLimit
                                and p.id in :postIds
                            order by p.totalLikes desc, p.createdAt desc
                    """)
    fun findAccessibleUserLikedFeedSelectPost(postInfoIds: List<Int>, createdAtLimit: Date, userId: Long, postIds: List<Long>, pageRequest: PageRequest): List<PostJpaEntity>



    @Query(value="""
                        select p 
                            from PostJpaEntity p
                            left join BlockJpaEntity b on p.createdBy = b.blockedPersonId 
                                and b.status = 'ACTIVE' 
                                and b.blockerId = :userId
                            left join ReportJpaEntity r on p.id = r.reportPostId 
                                and r.reportType = 'POST' 
                                and r.status = 'ACTIVE' 
                                and r.reporterId = :userId
                            where p.postInfoId in :postInfoIds
                                and b.blockedPersonId is null
                                and r.reportPostId is null
                                and p.status = 'ACTIVE'
                                and p.id in :postIds
                                and p.isKkommingTalk = false 
                            order by p.createdAt desc
                    """)
    fun findAccessibleFeedSelectPostAndNotKkoommingTalk(postInfoIds: List<Int>, userId: Long, postIds: List<Long>, pageRequest: PageRequest): List<PostJpaEntity>

    @Query(value="""
                        select p from PostJpaEntity p
                        where p.postInfoId in :postInfoIds
                        and p.status = 'ACTIVE'
                        and p.createdAt > :createdAtLimit
                        order by p.totalLikes desc, p.createdAt desc
                    """)
    fun findAccessibleUserLikedFeed(postInfoIds: List<Int>, createdAtLimit: Date, pageRequest: PageRequest): List<PostJpaEntity>


    @Query(value="""
                        select p from PostJpaEntity p
                        where p.postInfoId in :postInfoIds
                        and p.status = 'ACTIVE'
                        and p.createdAt > :createdAtLimit
                        order by p.totalViews desc, p.createdAt desc
                    """)
    fun findAccessibleUserViewFeed(postInfoIds: List<Int>, createdAtLimit: Date, pageRequest: PageRequest): List<PostJpaEntity>


    @Query(value="""
                        select p from PostJpaEntity p
                        where p.postInfoId in :postInfoIds 
                        and p.createdBy = :createdBy
                        and p.id not in :reportPostIds
                        and p.status = 'ACTIVE'
                        order by p.createdAt desc
                    """)
    fun findRecentUserPost(postInfoIds: List<Int>,createdBy: Long, reportPostIds: List<Long>, pageRequest: PageRequest): List<PostJpaEntity>

    @Query(value="""
                        select p from PostJpaEntity p
                        join CommentJpaEntity c on c.postId = p.id
                        where p.postInfoId in :postInfoIds 
                        and c.createdBy = :createdBy
                        and p.id not in :reportPostIds
                        and p.status = 'ACTIVE'
                        and c.status = 'ACTIVE'
                        order by p.createdAt desc
                    """)
    fun findRecentCommentPost(postInfoIds: List<Int>, createdBy: Long, reportPostIds: List<Long>, pageRequest: PageRequest): List<PostJpaEntity>

    @Query(value="""
                        select p from PostJpaEntity p
                        join StyleTagJpaEntity st on st.postId = p.id
                        where st.type = :styleTag
                        and p.postInfoId in (1)
                        and p.status = 'ACTIVE'
                        order by p.createdAt desc
                    """) //피드 고정
    fun findPostsByStyleTag( styleTag: StyleTagType, pageRequest: PageRequest): List<PostJpaEntity>
//    fun findAccessiblePostsByStyleTag(reportPostIds: List<Long> , blockUserIds: List<Long>,  styleTag: String, pageRequest: PageRequest): List<PostJpaEntity>


    //작성자 정보가 있는 경우를 join해서 체크할 필요가 있을까?
    @Query(value="""
                        select p from PostJpaEntity p
                        join LikeJpaEntity l on l.postId = p.id
                        where p.status = 'ACTIVE'
                        and l.status = 'ACTIVE'
                        and l.createdBy = :userId
                        order by l.createdAt desc
                    """)
    fun findMyLikedPosts(userId: Long, pageRequest: PageRequest): List<PostJpaEntity>


    @Query(value="""
                        select p from PostJpaEntity p
                        join ScrapJpaEntity s on s.postId = p.id
                        where p.status = 'ACTIVE'
                        and s.status = 'ACTIVE'
                        and s.createdBy = :userId
                        order by s.createdAt desc
                    """)
    fun findMyScrapPosts(userId: Long, pageRequest: PageRequest): List<PostJpaEntity>


    @Query(value="""
                        select p,s from PostJpaEntity p
                        join ScrapJpaEntity s on s.postId = p.id
                        where p.status = 'ACTIVE'
                        and s.status = 'ACTIVE'
                        and s.createdBy = :userId
                        order by p.createdAt desc
                    """)
    fun findMyScrapPosts2(userId: Long, pageRequest: PageRequest): List<Pair<PostJpaEntity?, ScrapJpaEntity?>>


//    fun aaa(): List<Pair<PostJpaEntity, UserJpaEntity>>
//    fun aaa(): Triple<Pair<PostJpaEntity, UserJpaEntity>>

}

/*// PostRepository.kt

한번에 여러개의 entity를 불러오는거 이게 된ㄷ고 ?

interface PostRepository : JpaRepository<Post, Long> {
    @Query("""
        SELECT p, u, i FROM Post p
        LEFT JOIN FETCH User u ON p.createdBy = u.id
        LEFT JOIN FETCH PostInteraction i ON i.postId = p.id AND i.userId = :userId
        WHERE p.id NOT IN :reportedPostIds
        AND p.createdBy NOT IN :blockedUserIds
        -- 추가 필터링 조건들...
    """)
    fun findPostsWithUserAndInteractions(
        userId: Long,
        pageParam: PageParam,
        tabId: String,
        blockedUserIds: List<Long>,
        reportedPostIds: List<Long>,
        ordering: String,
        postTags: List<String>,
        styleTags: List<String>
    ): List<Triple<Post, User, PostInteraction?>>
}*/
//작성자 정보가 있는 경우를 join해서 체크할 필요가 있을까?
