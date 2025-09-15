package org.psy.demo.common

import java.time.LocalDate
import java.time.format.DateTimeFormatter
data class MonthYear(
    val _date: String,
){
    val year: Int
    val month: Int

    init {
        try {
            val parsedDate = LocalDate.parse("$_date-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            year = parsedDate.year
            month = parsedDate.monthValue
        }catch (e: Exception) {
            throw IllegalArgumentException("Invalid date format. Please use yyyy-MM")
        }
    }
}
