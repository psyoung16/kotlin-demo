package org.psy.demo.core.phrase.service

import org.psy.demo.admin.response.PhraseManagementResponse
import org.psy.demo.admin.usecase.GetManagementPhraseUseCase
import org.psy.demo.admin.usecase.SettingManagementPhraseUseCase
import org.psy.demo.common.MetaData
import org.psy.demo.common.PageParam
import org.psy.demo.core.phrase.domain.PhraseManagement
import org.psy.demo.infra.repository.PhraseRepository
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component

@Component
private class PhraseManagementService (
    private val loadPhrasePort: PhraseRepository,
) : GetManagementPhraseUseCase, SettingManagementPhraseUseCase {
    override fun getManagePhrases(pageParam: PageParam): PhraseManagementResponse {
        val phraseManagements : Page<PhraseManagement> = loadPhrasePort.loadManagementPhrases("HOME_STICKER", pageParam)

        /*return StickerManagementResponse(
            stickers.content.map(StickerJpaEntity::mapToDomainManagement),
            MetaData.of(pageParam.offset, pageParam.size, stickers.totalElements)
        )*/

        return PhraseManagementResponse(
            phrase = loadPhrasePort.loadManagementPhrase("HOME_STICKER"),
            phrases = phraseManagements.content,
            metaData = MetaData.Companion.of(pageParam.page, pageParam.size, phraseManagements.totalElements)
        )
    }

    override fun getManagePhrase(position: String?): PhraseManagement? {
        return loadPhrasePort.loadManagementPhrase(position)
    }

    override fun createPhraseMangement(phrase: PhraseManagement) {
        loadPhrasePort.createPhraseManagement(phrase)
    }

}