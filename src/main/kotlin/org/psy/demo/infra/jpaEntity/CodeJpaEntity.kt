package org.psy.demo.infra.jpaEntity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.psy.demo.core.common.domain.Code

@Entity
@Table(name = "common_code")
class CodeJpaEntity(
        @Id
        @Column(name = "code", length = 10, nullable = false)
        var code: String,

        @Column(name = "codeName", length = 100)
        var codeName: String,

        @Column(name = "isShow", columnDefinition = "tinyint(1) default 0", nullable = false)
        var isShow: Boolean,

        @Column(name = "orderNum", columnDefinition = "int unsigned default 0", nullable = false)
        var orderNum: Int,

        @Column(name = "iconImgId")
        var iconImgId: Long? = null, // iconImgId가 null일 수 있도록 수정

        val iconImgUrl: String,
) {
    //보조
    constructor(code: String, codeName: String, isShow: Boolean, orderNum: Int, iconImgUrl: String) : this(code, codeName, isShow, orderNum, null, iconImgUrl)

    companion object {

        /*init {
            code = code
        }*/


        fun mapToDomain(code: CodeJpaEntity) = Code(
            code.code,
            code.codeName,
            code.iconImgUrl
        )
        /*fun cretate(code : String,
                    codeName : String,
                    isShow: Boolean,
                    orderNum: Int,
                    iconImgUrl: String) = CodeJpaEntity(
                code = code,
                codeName = codeName,
                isShow = isShow,
                orderNum = orderNum,
                iconImgId = 0,
                iconImgUrl = iconImgUrl
        )*/
    }


}
