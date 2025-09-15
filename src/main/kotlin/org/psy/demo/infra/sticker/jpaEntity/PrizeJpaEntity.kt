package org.psy.demo.infra.sticker.jpaEntity

import jakarta.persistence.*
import org.psy.demo.core.sticker.domain.Prize
import kotlin.jvm.Transient

@Entity
@Table(name = "prizes")
class PrizeJpaEntity (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    private var id: Int = 0,
    private val name: String? = null, //경품 이름
    private val imgUrl: String? = null, //이미지 url
    private val monthYear: String? = null, //경품 당첨 월
    private val status: String? = null, //상태
    @Transient
    private val isSelect: Boolean = false //스티커 선택 여부
) {


    fun mapToDomain(): Prize {
        return PrizeJpaEntity.mapToDomain(this)
    }
    companion object {
        fun mapToDomain(prizeJpaEntity: PrizeJpaEntity): Prize {
            with(prizeJpaEntity){
                return Prize(
                    id = id.toString(),
                    name = name.orEmpty(),
                    imgUrl = imgUrl.orEmpty(),
                    monthYear = monthYear ?: "2024-01",
                    isSelect = isSelect
                )
            }
        }
    }
}
