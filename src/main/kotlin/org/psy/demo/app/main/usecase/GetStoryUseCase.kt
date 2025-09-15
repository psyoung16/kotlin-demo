package org.psy.demo.app.main.usecase

import org.psy.demo.core.post.domain.Story
import org.psy.demo.core.user.domain.User

interface GetStoryUseCase {

    fun getStorys(userId: User.UserId) : List<Story>

}
