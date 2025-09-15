package org.psy.demo.core.phrase.service

import org.psy.demo.app.main.usecase.GetPhraseUseCase
import org.psy.demo.core.phrase.domain.Phrase
import org.psy.demo.infra.repository.PhraseRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
private class GetPhraseService (
    private val loadPhrasePort: PhraseRepository
) : GetPhraseUseCase {
    override fun getPhrase(position: String): Phrase? {
        return loadPhrasePort.loadPhrase("HOME_STICKER")
    }


}
