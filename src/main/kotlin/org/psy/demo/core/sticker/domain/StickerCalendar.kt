package org.psy.demo.core.sticker.domain

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import java.time.LocalDate

data class StickerCalendar(
    val date: Int,  //date 1.2,3...
    @field:JsonSerialize(using = LocalDateSerializer::class)
    @field:JsonDeserialize(using = LocalDateDeserializer::class)
    val fullDate: LocalDate,  //전체 date
    val userSticker: UserSticker?
) {
}
