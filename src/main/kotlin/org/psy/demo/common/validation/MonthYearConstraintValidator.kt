package org.psy.demo.common.validation//package org.psy.kotlinhexagonaldemo.common.validation
//
//import jakarta.validation.ConstraintValidator
//import jakarta.validation.ConstraintValidatorContext
//import java.time.LocalDate
//import java.time.format.DateTimeFormatter
//import java.time.format.DateTimeParseException
//
////안씀
//class MonthYearConstraintValidator : ConstraintValidator<ValidMonthYear, String> {
//    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
//        if (value == null) {
//            return true // null은 다른 어노테이션을 사용하여 검증합니다.
//        }
//        val formatter = DateTimeFormatter.ofPattern("yyyy-MM")
//        return try {
//            LocalDate.parse("$value-01", formatter)
//            true
//        } catch (e: DateTimeParseException) {
//            throw IllegalArgumentException("Invalid month year format. Required format: yyyy-MM")
//            false
//        }
//    }
//}
