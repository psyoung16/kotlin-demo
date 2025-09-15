package org.psy.demo.app.main.usecase

import org.psy.demo.core.common.domain.ManageTag
import org.psy.demo.core.vo.TagType

interface GetManageTagUseCase {

    fun getRecommandManageTag(tagType: TagType): List<ManageTag>

}
