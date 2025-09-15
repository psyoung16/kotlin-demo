package org.psy.demo.admin.usecase

import org.psy.demo.common.PageParam
import org.psy.demo.admin.response.PhraseManagementResponse
import org.psy.demo.core.phrase.domain.PhraseManagement

interface GetManagementPhraseUseCase {
    fun getManagePhrases(pagePara: PageParam): PhraseManagementResponse
    fun getManagePhrase(position: String?): PhraseManagement?
}
