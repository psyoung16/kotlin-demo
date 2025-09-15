package org.psy.demo.infra.sticker.jpaEntity

import jakarta.persistence.*
import org.psy.demo.core.sticker.domain.UserPrize
import java.util.*


@Entity
@Table(name = "user_prizes")
class UserPrizeJpaEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    private var id: Long? = null,
    private val createdBy: Long? = null,
    private val prizeId: Long = 0,
    private val monthYear: String? = null,
    val createdAt: Date? = null,
    val updatedAt: Date? = null,
    private val isFinalSelect: Boolean = false, //최종 선택 여부
) {
    fun mapToDomain(): UserPrize {
        return Companion.mapToDomain(this)
    }

    companion object {
        fun mapToDomain(entity: UserPrizeJpaEntity): UserPrize {
            return with(entity) {
                UserPrize(
                    id = id.toString(),
                    createdBy = createdBy ?: 0L,
                    prizeId = prizeId.toString(),
                    monthYear = monthYear.orEmpty(),
                )
            }
        }
    }


}
