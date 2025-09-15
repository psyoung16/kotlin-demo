package org.psy.demo.admin.response

import org.psy.demo.common.MetaData
import org.psy.demo.core.phrase.domain.PhraseManagement

class PhraseManagementResponse( //1페이지 전체 한번에 조회
    val phrase: PhraseManagement?,
    val phrases: List<PhraseManagement>,
    val metaData: MetaData
) {
}