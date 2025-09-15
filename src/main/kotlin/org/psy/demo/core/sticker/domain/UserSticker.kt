package org.psy.demo.core.sticker.domain


data class UserSticker(
    val id: String?,
    val title: String,
    val description: String?,
    val stickerId: String,
    val userId: Long, //작성자 id
    val date: String,
    val imgUrl: String,
    val tagName: String, //캘린더 리스트 및 상세 조회시 사용하는 태그 네임
    val underLineColor: String
//    val createdAt: String,
) {

    companion object {

    }

}
