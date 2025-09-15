package org.psy.demo.core.common.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class Code (
        //항상 기본값을 설정해줄 수는 없는데...?
         //cache 대비

        //get:JsonProperty("code") 이런식으로 하면 안됨
        //JsonProperty("code") 이런식으로 해야함

        @JsonProperty("code")
        val code: String,
        @JsonProperty("codeName")
        val codeName: String,
        @JsonProperty("iconImgUrl")
        val iconImgUrl: String
) {
    companion object {
    }
}

//db에는 null가능. but front에서는 불가능?
/*
fun CodeJpaEntity.mapToDomain() = Code(
        this.code,
        this.codeName,
        this.iconImgUrl
)
*/
