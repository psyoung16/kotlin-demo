package org.psy.demo.infra.repository

import org.psy.demo.infra.mapper.BlockMapper
import org.psy.demo.core.user.domain.Block
import org.psy.demo.infra.jpaEntity.BlockJpaEntity
import org.psy.demo.infra.jpaEntity.BlockStatus
import org.psy.demo.core.user.domain.User
import org.psy.demo.infra.jpaRepository.BlockJpaRepository
import org.springframework.stereotype.Component


@Component
class BlockRepository (
    private val blockJpaRepository: BlockJpaRepository
) {

    fun loadBlockUsers(userId: User.UserId): List<Block> {
        val blocks: List<BlockJpaEntity> = blockJpaRepository.findByBlockerIdAndStatus(userId.id.toLong(), BlockStatus.ACTIVE)
        return blocks.map(BlockMapper::mapToDomain)
    }

}
