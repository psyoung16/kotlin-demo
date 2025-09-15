package org.psy.demo.infra.repository

import org.psy.demo.core.common.domain.Code
import org.psy.demo.infra.jpaEntity.CodeJpaEntity
import org.psy.demo.infra.jpaRepository.CodeJpaRepository
import org.springframework.stereotype.Component

@Component
class CodeRepository (
     private val codeJpaRepository: CodeJpaRepository
) {

    fun loadCodeList(): List<Code> {
        val codes : List<CodeJpaEntity> = codeJpaRepository.findCodeDepth0()
        return codes.map(CodeJpaEntity::mapToDomain)
    }
}
