package org.psy.demo.infra.mapper

import org.psy.demo.infra.jpaEntity.FileJpaEntity
import org.psy.demo.core.domain.entity.File

object FileMapper {

    fun mapToDomain(entity: FileJpaEntity) = File(
        fileId = File.FileId.of(entity.getId().toString()),
        url = entity.getUrl()
    )


}
