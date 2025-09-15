package org.psy.demo.core.sticker.domain

import org.psy.demo.infra.sticker.jpaEntity.UserPrizeJpaEntity

data class UserPrize(
    val id: String,
    val createdBy: Long,
    val prizeId: String,
    val monthYear: String,
//    val isFinalSelect: Boolean = false
){

    fun mapToJpaEntity(): UserPrizeJpaEntity {
        return Companion.mapToJpaEntity(this)
    }


    companion object {

        //여기가 맞을까?
        fun mapToJpaEntity(entity: UserPrize): UserPrizeJpaEntity {
            return with(entity) {
                UserPrizeJpaEntity(
                    id = id.toLong(),
                    createdBy = createdBy,
                    prizeId = prizeId.toLong(),
                    monthYear = monthYear,
                )
            }
        }
    }
}
