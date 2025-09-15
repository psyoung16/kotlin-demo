package org.psy.demo.core.post.domain

import org.psy.demo.app.sdui.translator.Tabs
import org.psy.demo.app.sdui.translator.container.CommunityUIOrdering
import org.psy.demo.common.PageParam
import org.psy.demo.core.user.domain.User

data class GetCommunityPostQuery(
    val userId: User.UserId,
    val pageParam: PageParam,
    val tabId: Tabs.TAB_ID, //postInfoId로 받...?
    val blockUserId: List<User.UserId>,
    val reportPostId: List<Post.PostId>,
    val ordering: CommunityUIOrdering,
    val postTags: List<String> = emptyList(),
    val styleTags: List<String> = emptyList()
)
