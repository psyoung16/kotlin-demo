package org.psy.demo.common

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class ResponseDataEntity<T>(
        val data: T,
        val log: Any? = null,
        val status: HttpStatus = HttpStatus.OK,
        val success: Boolean = true
) : ResponseEntity<ResponseDataEntity.ResponseBody<T>>(
        ResponseBody(status.value(), success, data, log),
        status
) {
        data class ResponseBody<T>(
                val status: Int,
                val success: Boolean,
                val data: T,
                val log: Any?
        )

        constructor(data: T, log: Any? = null) : this(data, log, HttpStatus.OK, true)

        companion object {
                fun <T> error(status: HttpStatus, data: T, log: Any? = null): ResponseDataEntity<T> {
                        return ResponseDataEntity(data, log, status, false)
                }
        }
}
