package org.psy.demo.app.main.response

import com.fasterxml.jackson.annotation.JsonProperty
import org.psy.demo.core.post.domain.Writer
import org.psy.demo.core.goods.domain.Goods

data class MainWriterResponse
    (
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("nickname")
    val nickname: String,
    @JsonProperty("avatarUrl")
    val avatarUrl: String,
    @JsonProperty("backgroundImg")
    val backgroundImg: String,
    @JsonProperty("goodsList")
    val goodsList: List<Goods>
) {

    companion object {
        fun of(writer: Writer, goods: List<Goods> = emptyList()): MainWriterResponse {
            return with(writer) {
                MainWriterResponse(
                    id = id,
                    nickname = nickname,
                    avatarUrl = avatarUrl,
                    backgroundImg = backgroundImg,
                    goodsList = goods
                )
            }
        }
    }
}
