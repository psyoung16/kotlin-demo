package org.psy.demo.common.validation

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import java.text.Normalizer
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload

import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [CustomKoreanLengthValidator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class CustomKoreanLength(
    val max: Int,
    val message: String = "길이를 초과했습니다.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)


class CustomKoreanLengthValidator : ConstraintValidator<CustomKoreanLength, String> {
    private var max: Int = 0

    override fun initialize(constraintAnnotation: CustomKoreanLength) {
        max = constraintAnnotation.max
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        if (value == null) return true
        val normalized = Normalizer.normalize(value, Normalizer.Form.NFC)
        return normalized.length <= max
    }
}
