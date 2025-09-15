package org.psy.demo.infra.repository

import org.psy.demo.core.post.domain.Writer
import org.psy.demo.infra.jpaEntity.WriterJpaEntity
import org.psy.demo.infra.jpaRepository.WriterJpaRepository
import org.springframework.stereotype.Component

@Component
class WriterRepository(
    private val writerJpaRepository: WriterJpaRepository
){
    fun loadMainWriterList(): List<Writer> {
        //현재 전체 조회 후 랜덤으로 돌리기 추후 작가 수가 많으면 다른 방법 고려
        //이중에 랜덤 18개만 뽑기 --> 추후 작가 리스트가 많아지면 다른 방법 고려
        val writers: List<WriterJpaEntity> = writerJpaRepository.getMainProWriterList().shuffled().take(18)

        return writers.map(WriterJpaEntity::mapToDomain)
    }


}
