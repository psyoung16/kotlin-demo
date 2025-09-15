package org.psy.demo.common

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.time.temporal.ChronoUnit

fun Date.of(monthYear: String): Date {


    val format = SimpleDateFormat("yyyy-MM-dd")
    val date: Date = format.parse(monthYear)

    val string = date.toInstant().atZone(ZoneId.of("GMT+9")).toLocalDateTime()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

    // 포맷된 문자열을 다시 Date 객체로 변환
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return sdf.parse(string)
}


//확장함수
fun LocalDate.toKoreaDate(): LocalDate {
    val koreaZoneId = ZoneId.of("Asia/Seoul")
    val nowInKorea = ZonedDateTime.now(koreaZoneId)
    return nowInKorea.toLocalDate()
}
fun LocalDateTime.toKoreaDate(): LocalDateTime {
    val koreaZoneId = ZoneId.of("Asia/Seoul")
    val nowInKorea = ZonedDateTime.now(koreaZoneId)
    return nowInKorea.toLocalDateTime()
}

fun LocalDateTime.toUTCDate(): LocalDateTime {
    val utcZoneId = ZoneId.of("UTC")
    val nowInKorea = ZonedDateTime.now(utcZoneId)
    return nowInKorea.toLocalDateTime()
}

fun String.strTime(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")
    val givenDateTime = LocalDateTime.parse(this, formatter)
    val now = LocalDateTime.now()

    return when (val seconds = ChronoUnit.SECONDS.between(givenDateTime, now)) {
        in 0..59 -> "${seconds}초 전"
        in 60..3599 -> "${seconds / 60}분 전"
        in 3600..86399 -> "${seconds / 3600}시간 전"
        else -> givenDateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
    }
}
