package org.psy.demo.common

import org.psy.demo.core.vo.PostInfoType
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

fun formattingLegacyDate(date: Date?): String {

    // 1단계: Date를 LocalDateTime으로 변환
//    val originalFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S")
    // Date.toString()의 결과가 원래의 포맷터와 정확히 일치하지 않을 수 있으므로, Instant를 사용하여 LocalDateTime으로 변환하는 것이 좋습니다.
    val localDateTime = LocalDateTime.ofInstant(date?.toInstant() ?: Date().toInstant(), ZoneId.systemDefault())

    // 2단계: LocalDateTime을 ZonedDateTime으로 변환하고 UTC로 설정
    val zonedDateTime = localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"))

    // 3단계: ZonedDateTime을 원하는 형식의 문자열로 포맷
    val targetFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val formattedString = zonedDateTime.format(targetFormatter)

    return formattedString
}
fun Date.formatLegacyDate(): String {
    val localDateTime = LocalDateTime.ofInstant(this.toInstant() ?: Date().toInstant(), ZoneId.systemDefault())

    // 2단계: LocalDateTime을 ZonedDateTime으로 변환하고 UTC로 설정
    val zonedDateTime = localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"))

    // 3단계: ZonedDateTime을 원하는 형식의 문자열로 포맷
    val targetFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val formattedString = zonedDateTime.format(targetFormatter)

    return formattedString
}

fun stringToInt(postInfoType: PostInfoType): Int {
    return when (postInfoType) {
        PostInfoType.FEED -> 1
        PostInfoType.VOTE -> 2
        PostInfoType.ETC -> 3
        PostInfoType.STAR -> 4
        PostInfoType.ISSUE -> 5
        PostInfoType.POST_GOODS -> 6
        else -> 1
    }
}

fun intToType(postInfoId: Int): PostInfoType {
    return when (postInfoId) {
        1 -> PostInfoType.FEED
        2 -> PostInfoType.VOTE
        3 -> PostInfoType.ETC
        4 -> PostInfoType.STAR
        5 -> PostInfoType.ISSUE
        6 -> PostInfoType.POST_GOODS
        else -> PostInfoType.FEED
    }
}

fun Int.formatPrice(): String =
    toString().reversed().chunked(3).joinToString(",").reversed()
fun String.addPercent(): String =
    "$this%"
