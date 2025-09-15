package org.psy.demo.common.exception

import org.psy.demo.common.ResponseDataEntity
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.HandlerMethodValidationException
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.NoHandlerFoundException

private val logger = KotlinLogging.logger {}


@RestControllerAdvice
class GlobalExceptionHandler {
    //https://velog.io/@dhwlddjgmanf/%EA%BC%AC%EB%A6%AC%EB%B3%84-%EC%98%A4%EB%A5%98%EC%9D%BC%EC%A7%80-Exception-Handling%EC%9D%84-%EC%96%B4%EB%96%BB%EA%B2%8C-%ED%95%98%EB%A9%B4-%EB%8D%94-%EC%9E%98%ED%95%A0-%EC%88%98-%EC%9E%88%EC%9D%84%EA%B9%8C


    //https://velog.io/@tkppp-dev/SpringBoot-RestControllerAdvice%EB%A5%BC-%ED%86%B5%ED%95%9C-%EC%97%90%EB%9F%AC%EC%B2%98%EB%A6%AC%EB%A5%BC-%EB%A6%AC%ED%8C%A9%ED%86%A0%EB%A7%81-%ED%95%B4%EB%B3%B4%EC%9E%90-with-Kotlin
//ResponseEntity<ErrorResponse> 처럼 responseDAtaEntiy도 custom이 가능하지 않을까?
    /*@ExceptionHandler(value = [CustomException::class])
    fun handlingCustomException(ex: CustomException): ResponseEntity<ErrorResponse> {
        val errorCode: ErrorCode = ex.errorCode
        val errorDto = ErrorResponse(errorCode = errorCode.name, message = errorCode.message)
        return ResponseEntity(errorDto, errorCode.status)
    }*/

    @ExceptionHandler(CustomException::class)
    fun handlingCustomException(ex: CustomException): ResponseDataEntity<ErrorResponse> {
        val errorCode: ErrorCode = ex.errorCode
        logger.info { "error ${ex.message}" }

        val errorDto = when (errorCode) {
            ErrorCode.DAYS_MISSING_STICKERS -> {
                val (title, description, buttonTitle) = errorCode.message.split("|")
                ErrorResponse(
                    code = errorCode.code,
                    messages = "",
                    alertMessage = AlertMessage(title, description, buttonTitle)
                )
            }
            else -> ErrorResponse(code = errorCode.code, messages = errorCode.message, isShow = errorCode.isShow)
        }

        return ResponseDataEntity.error(
            status = errorCode.status,
            data = errorDto,
            log =  null
        )
    }

    //이거먼저실행이되어야..
    //request error
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseDataEntity<ErrorResponse> {
        logger.info { "error ${ex.message}" }

        val errorDto = ErrorResponse(
            code = "0002",
            messages = "유효한 값이 아닙니다.",
            error = ex.message ?: ""
        )

        return ResponseDataEntity.error(
            status = HttpStatus.BAD_REQUEST,
            data = errorDto,
            log =  null
        )
    }

    @ExceptionHandler(value = [MethodArgumentTypeMismatchException::class])
    fun handleMethodArgumentTypeMismatchException(ex: MethodArgumentTypeMismatchException): ResponseDataEntity<ErrorResponse> {
        val errorDto = ErrorResponse(code = "0002", messages = "유효한 값이 아닙니다.", error = ex.message ?: "")
        logger.info { "error ${ex.message}" }
        return ResponseDataEntity.error(
            status = HttpStatus.BAD_REQUEST,
            data = errorDto,
            log =  null
        )
    }
    // ./request error

    /**
     * postMapping @valid 유효성 검사 실패시 발생하는 exception
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleIllegalArgumentException(ex: MethodArgumentNotValidException): ResponseDataEntity<ErrorResponse> {

        var isShow = false
        if (ex.bindingResult.fieldError?.defaultMessage != null) {
            isShow = true
        }

        val errorDto =
            ErrorResponse(
                code = "0002", messages = ex.bindingResult.fieldError?.defaultMessage
                    ?: "유효한 값이 아닙니다.", error = "유효한 값이 아닙니다.", isShow
            )
        logger.info { "error ${ex.detailMessageArguments}" }
        return ResponseDataEntity.error(
            status = HttpStatus.BAD_REQUEST,
            data = errorDto,
            log =  null
        )
    }

    //validation check error
    @ExceptionHandler(value = [HandlerMethodValidationException::class])
    fun handleMethodValidationException(ex: HandlerMethodValidationException): ResponseDataEntity<ErrorResponse> {
        val errorDto = ErrorResponse(
            code = "0002",
            messages = ex.detailMessageArguments[0]?.toString() ?: "유효한 값이 아닙니다.",
            error = "유효한 값이 아닙니다.",
            false
        )
        logger.info { "error ${ex.message}" }
        ex.printStackTrace()
        ex.stackTrace.forEach {
            logger.info { "error $it" }
        }
        return ResponseDataEntity.error(
            status = HttpStatus.BAD_REQUEST,
            data = errorDto,
            log =  null
        )
    }

    /**
     * 임시방편 위의 예외 제외 전체 exception 일괄 template
     */
    @ExceptionHandler(value = [Exception::class])
    fun handleRuntimeException(ex: Exception): ResponseDataEntity<ErrorResponse> {
        val errorDto = ErrorResponse(code = "0002", messages = "알 수 없는 error", error = "" ?: "")
        logger.info { "error ${ex.message}" }
        ex.printStackTrace()
        ex.stackTrace.forEach {
            logger.info { "error $it" }
        }
        return ResponseDataEntity.error(
            status = HttpStatus.INTERNAL_SERVER_ERROR,
            data = errorDto,
            log =  null
        )
    }

    //필요한 파라미터 없는 경우
    @ExceptionHandler(value = [MissingServletRequestParameterException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMissingParams(ex: MissingServletRequestParameterException): ResponseDataEntity<ErrorResponse> {
        val parameterName = ex.parameterName
        val message = "The required parameter '$parameterName' is missing."

        val errorDto = ErrorResponse(code = "0002", messages = "유효한 값이 아닙니다.", error = message)

        return ResponseDataEntity.error(
            status = HttpStatus.BAD_REQUEST,
            data = errorDto,
            log =  null
        )
    }

    //Command시 필요한 파라미터 없는 경우
    @ExceptionHandler(value = [HttpMessageNotReadableException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMissingParams(ex: HttpMessageNotReadableException): ResponseDataEntity<ErrorResponse> {

        val errorDto = ErrorResponse(code = "0002", messages = "유효한 값이 아닙니다.", error = "잘못된 요청 형식입니다. 모든 필수 필드가 포함되어 있는지 확인해주세요.")

        return ResponseDataEntity.error(
            status = HttpStatus.BAD_REQUEST,
            data = errorDto,
            log =  null
        )
    }


    @ExceptionHandler(value = [NoHandlerFoundException::class])
    fun notFound(ex: Exception): ResponseDataEntity<ErrorResponse> {
        val errorDto = ErrorResponse(code = "0001", messages = "notfound", error = ex.message ?: "")
        logger.info { "error ${ex.message}" }
        return ResponseDataEntity.error(
            status = HttpStatus.NOT_FOUND,
            data = errorDto,
            log =  null
        )
    }


    //추후 ResponseEntity와 합칠 수 있나..?
    data class ErrorResponse(
        val messages: String = "", //front에 보여줄 코드
        val error: String = "", //통신 전용
        val code: String = "", //error코드
        val isShow: Boolean = false, //해당 alert front에서 show여부처리
        val alertMessage: AlertMessage?
    ) {
        constructor(code: String, messages: String, error: String) : this(
            messages = messages,
            error = error,
            code = code,
            alertMessage = null
        )

        constructor(code: String, messages: String) : this(
            messages = messages,
            error = "",
            code = code,
            alertMessage = null
        )

        constructor(code: String, messages: String, isShow: Boolean) : this(
            messages = messages,
            error = "",
            code = code,
            isShow,
            alertMessage = null
        )

        constructor(code: String, messages: String, error: String, isShow: Boolean) : this(
            messages = messages,
            error,
            code = code,
            isShow,
            alertMessage = null
        )

    }

    //이걸 T..?
    data class AlertMessage(
        val title: String = "",
        val description: String = "",
        val buttonTitle: String = ""
    )


}
