package org.psy.demo.common

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class ImgUtil {
    companion object {
        var s3Url: String = ""

        fun getImgUrl(imgUrl: String): String {
            return s3Url + imgUrl
        }
    }

    @Value("\${data.s3Url}")
    fun setS3Url(value: String) {
        s3Url = value
    }
}
