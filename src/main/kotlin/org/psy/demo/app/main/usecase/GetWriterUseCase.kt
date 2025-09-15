package org.psy.demo.app.main.usecase

import org.psy.demo.core.post.domain.Writer


interface GetWriterUseCase {


    fun getMainWriter() : List<Writer>

}
