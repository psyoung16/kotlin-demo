package org.psy.demo.app.main.usecase

import org.psy.demo.core.content.domain.Challenge

interface GetEventUseCase {

    fun getMainChallenges(): List<Challenge>
}
