package org.psy.demo.app.main.usecase

import org.psy.demo.core.common.domain.Code

interface GetCodeUseCase {
    fun getCategoryList(): List<Code>
}
