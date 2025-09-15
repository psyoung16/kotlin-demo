package org.psy.demo.core.phrase.service

import org.psy.demo.app.main.usecase.GetManageTagUseCase
import org.psy.demo.core.common.domain.ManageTag
import org.psy.demo.core.vo.TagType
import org.psy.demo.infra.repository.ManageTagRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
private class GetManageTagService(
    private val loadManageTagPort: ManageTagRepository
)  : GetManageTagUseCase{

    override fun getRecommandManageTag(tagType: TagType): List<ManageTag> {
        return loadManageTagPort.loadManageTag(tagType)
    } //여기다가 두는건가? facade에 두는건가?

}
