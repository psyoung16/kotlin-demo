package org.psy.demo.common

import com.fasterxml.jackson.annotation.JsonProperty
import kotlin.math.ceil

data class MetaData(
    @JsonProperty("previousPage")
    val previousPage: Int,
    @JsonProperty("currentPage")
    val currentPage: Int, // Immutable이므로 var 대신 val을 사용
    @JsonProperty("nextPage")
    val nextPage: Int,
    @JsonProperty("firstPage")
    val firstPage: Int = 1, // 기본 값을 직접 할당
    @JsonProperty("lastPage")
    val lastPage: Int,
    @JsonProperty("pageSize")
    val pageSize: Int,
    @JsonProperty("totalPages")
    val totalPages: Int,
    @JsonProperty("totalRecords")
    val totalRecords: Long
) {
    companion object {
        fun of(requestedPage: Int, pageSize: Int, totalRecords: Long): MetaData {
            var requestedPage2 = requestedPage
            if (requestedPage == 0){
                requestedPage2 = 1
            }

            val currentPage = requestedPage2 + 1 // currentPage를 직접 수정하지 않고 새로운 값 계산


            val totalPages = ceil(totalRecords.toDouble() / pageSize).toInt()
            val firstPage = 1
            val lastPage = totalPages
            val previousPage = if (currentPage > 1) currentPage - 1 else firstPage
            val nextPage = if (currentPage < totalPages) currentPage + 1 else lastPage

            return MetaData(
                previousPage,
                currentPage,
                nextPage,
                firstPage,
                lastPage,
                pageSize,
                totalPages,
                totalRecords
            )
        }
    }
}


// Assuming the Sort class needs to be converted as well
data class Sort(val property: String, val direction: Direction) {
    enum class Direction {
        ASC, DESC
    }
}
