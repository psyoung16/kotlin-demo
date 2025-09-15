package org.psy.demo.infra.mapper

import org.psy.demo.core.user.domain.Block
import org.psy.demo.infra.jpaEntity.BlockJpaEntity

object BlockMapper {
    fun mapToDomain(entity: BlockJpaEntity) = Block(
        id = entity.getId() ?: 0,
        blockerId = entity.getBlockerdId(),
        blockerPersonId = entity.getBlockedPersonId(),
        status = entity.getStatus()
    )
}
