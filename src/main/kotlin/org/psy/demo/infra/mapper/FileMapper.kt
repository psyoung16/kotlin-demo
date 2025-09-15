package org.psy.demo.infra.mapper

import org.psy.demo.core.common.domain.File
import org.psy.demo.infra.jpaEntity.FileJpaEntity

object FileMapper {

    fun mapToDomain(entity: FileJpaEntity) = File(
        fileId = File.FileId.of(entity.getId().toString()),
        url = entity.getUrl()
    )


}
