package org.psy.demo.core.content.service

import org.psy.demo.app.main.usecase.GetEventUseCase
import org.psy.demo.core.content.domain.Challenge
import org.psy.demo.infra.repository.EventRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
private class GetEventService (
    private val eventRepository: EventRepository
) : GetEventUseCase {

    @Cacheable(value = ["MainChallengeList"], key = "#root.method.name")
    override fun getMainChallenges(): List<Challenge> {
        return eventRepository.loadMainChallengeList()
    }
}
