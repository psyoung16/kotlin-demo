package org.psy.demo.app.main.usecase

import org.psy.demo.core.phrase.domain.Phrase


interface GetPhraseUseCase {

    fun getPhrase(position: String): Phrase?

}
