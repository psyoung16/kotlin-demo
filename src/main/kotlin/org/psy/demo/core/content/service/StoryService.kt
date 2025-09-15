package org.psy.demo.core.content.service

import org.psy.demo.app.main.usecase.GetStoryUseCase
import org.psy.demo.core.post.domain.Story
import org.psy.demo.core.user.domain.User
import org.psy.demo.infra.repository.StoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
private class GetStoryService (
    private val loadStoryPort: StoryRepository,
) : GetStoryUseCase {

    override fun getStorys(userId: User.UserId) : List<Story> {
        return loadStoryPort.loadStorys(userId)
    }

}


