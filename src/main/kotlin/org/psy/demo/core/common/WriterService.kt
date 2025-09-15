package org.psy.demo.core.common

import org.psy.demo.app.main.usecase.GetWriterUseCase
import org.psy.demo.core.post.domain.Writer
import org.psy.demo.infra.repository.WriterRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class GetWriterService(
    private val loadWriterPort: WriterRepository
) : GetWriterUseCase{
    override fun getMainWriter(): List<Writer> {
        return  loadWriterPort.loadMainWriterList()
    }

}
