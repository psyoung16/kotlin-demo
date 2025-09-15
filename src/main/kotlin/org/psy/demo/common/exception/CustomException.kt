package org.psy.demo.common.exception

import org.springframework.http.HttpStatus

// CustomException
class CustomException(
    val errorCode: ErrorCode
) : RuntimeException()

// ErrorCode - 내용 구체화
enum class ErrorCode(val status: HttpStatus,
                     val message: String,
                     val code: String, val isShow: Boolean = false) {

    //1000
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "이 사용자는 존재하지 않거나 더 이상 활성 상태가 아닐 수 있습니다", "1002",true),
    USER_IS_DEACTIVATED(HttpStatus.BAD_REQUEST, "이 사용자는 일시적으로 비활성화되었습니다. 이 사용자와 상호작용할 수 없습니다", "1003"), //1002와 거의 동일하게 사용
    USER_ALREADY_BLOCKED(HttpStatus.BAD_REQUEST, "차단된 사용자는 게시물 및 프로필에 액세스할 수 없습니다", "1015",true),



    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "올바른 값이 아닙니다.", "0002"),

    //2000
    TOKEN_EXPIRED_OR_NOT_AVAILABLE(HttpStatus.FORBIDDEN, "세션이 만료되었습니다. 다시 로그인해주세요.", "2002"),
    DEVICE_SESSION_EXPIRED(HttpStatus.FORBIDDEN, "세션이 만료되었습니다. 다시 로그인해주세요.", "2003", true),
    PERMISSION_DENIED(HttpStatus.FORBIDDEN, "세션이 만료되었습니다. 다시 로그인해주세요.", "2004", true),


    //3000


    //ALER
    //10000
    //sticker
    A_STICKER_FOR_THE_SPECIFIED_DATE_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 해당 날짜의 스티커가 존재합니다.", "10001"),
    NO_STICKER_AVAILABLE(HttpStatus.BAD_REQUEST, "해당 스티커가 삭제되었거나 사용할 수 없습니다.", "10002"), //만약, admin에서 삭제되엇는데 사용하려고 할경우
    NO_STICKER_TO_EDIT_FOR_DATE(HttpStatus.BAD_REQUEST, "해당 날짜에 수정할 수 있는 스티커가 없습니다.", "10003"),  //수정시
    DAYS_MISSING_STICKERS(HttpStatus.BAD_REQUEST, "아직 스티커를 채우지\n않은 날이 있어요.|스티커를 모두 채워야만\n응모가 가능해요.|스티커 채우러 가기", "10004"),
    ALREADY_APPLY_PRIZE(HttpStatus.BAD_REQUEST, "이미 응모한 상품은 수정이 불가능합니다.", "10005", true),

}

