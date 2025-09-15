package org.psy.demo.admin.usecase

import org.psy.demo.core.phrase.domain.PhraseManagement


interface SettingManagementPhraseUseCase {
    fun createPhraseMangement(phrase: PhraseManagement)
}
