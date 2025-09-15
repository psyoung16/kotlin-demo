package org.psy.demo.infra.repository

import org.psy.demo.core.content.domain.Challenge
import org.psy.demo.infra.jpaEntity.EventJpaEntity
import org.psy.demo.infra.jpaRepository.EventJpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class EventRepository(
    private val eventJpaRepository: EventJpaRepository
) {
    fun loadMainChallengeList(): List<Challenge> {
        val events = eventJpaRepository.findByChallenge(Date(), Date())
        return events.map(EventJpaEntity::mapToDomain)
    }


}
