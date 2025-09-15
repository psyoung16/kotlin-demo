package org.psy.demo.core.common

import org.psy.demo.app.main.usecase.GetCodeUseCase
import org.psy.demo.core.common.domain.Code
import org.psy.demo.infra.repository.CodeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GetCodeService(
    private val codeRepository: CodeRepository
) : GetCodeUseCase {

    override fun getCategoryList(): List<Code> {
        return codeRepository.loadCodeList()
    }
}
