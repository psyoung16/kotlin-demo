package org.psy.demo.common.validation

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [EnumValidator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class ValidEnum(
    val message: String = "Invalid value. This is not permitted.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Any>> = [],
    val enumClass: KClass<out Enum<*>> // Enum 클래스 타입을 받는 파라미터
)

class EnumValidator : ConstraintValidator<ValidEnum, String?> { // 여기에서 필드 타입을 String?로 지정합니다.
    private lateinit var enumValues: Array<String>

    override fun initialize(annotation: ValidEnum) {
        enumValues = annotation.enumClass.java.enumConstants.map { it.name }.toTypedArray()
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        return value == null || enumValues.contains(value) // null이거나 enum 값에 속하는지 확인
    }
}
