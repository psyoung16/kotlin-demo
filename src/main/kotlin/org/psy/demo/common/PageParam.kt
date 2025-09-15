package org.psy.demo.common

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

data class PageParam(
    var page: Int = 1, // 기본값을 1로 설정
    var size: Int = 10, // 기본값 10
    var sort: Sort = Sort.unsorted()
) {

    companion object {
        fun of(offset: Int?, size: Int?): PageParam {
            return PageParam(offset ?: 1, size ?: 20)
        }

        fun of(offset: Int?, size: Int?, sort: Sort): PageParam {
            return PageParam(offset ?: 1, size ?: 20, sort)
        }
    }

    //page query에서만 사용
    fun toPageRequest(): PageRequest {
        //여기서 조정을 해주는 수밖에 없나..?
        //들어오는 page 규칙은 1, 2, 3 ... 이라고 가정
//        this.offset = if (this.offset < 0) 0 else this.offset - 1

        var innerOffset  = this.page

        if (innerOffset > 0){
            innerOffset -= 1
        }
        return PageRequest.of(innerOffset, this.size, this.sort)
    }
    //page query에서만 사용
    /*fun toPageRequest(): PageRequest {
        //여기서 조정을 해주는 수밖에 없나..?
        //들어오는 page 규칙은 1, 2, 3 ... 이라고 가정
//        this.offset = if (this.offset < 0) 0 else this.offset - 1

        var innerOffset  = this.offset

        if (innerOffset > 0){
            innerOffset -= 1
        }
        return PageRequest.of(innerOffset, this.size, this.sort)
    }*/
}

