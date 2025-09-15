package org.psy.demo.core.user.domain

import org.psy.demo.infra.jpaEntity.BlockStatus

data class Block(
    val id: Long,
    val blockerId: Long,
    val blockerPersonId: Long,
    val status: BlockStatus
) {

}