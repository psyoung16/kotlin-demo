package org.psy.demo.common

import com.epages.restdocs.apispec.ParameterDescriptorWithType
import com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName
import com.epages.restdocs.apispec.SimpleType
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath

//paramter
sealed class ParamterFieldType(val type: SimpleType){
    data object STRING : ParamterFieldType(SimpleType.STRING)
    data object NUMBER : ParamterFieldType(SimpleType.NUMBER)
    data object BOOLEAN : ParamterFieldType(SimpleType.BOOLEAN)
//    data object OBJECT : ParamterFieldType(SimpleType.OBJECT)
//    data object ARRAY : ParamterFieldType(SimpleType.ARRAY)
// Invoke operator to return the type of ParamterFieldType
}
infix fun String.type(docsFieldType: ParamterFieldType): ParameterDescriptorWithType {
    return parameterWithName(this).type(docsFieldType.type)
}
infix fun ParameterDescriptorWithType.means(description: String): ParameterDescriptorWithType {
    return this.description(description)
}
infix fun ParameterDescriptorWithType.isOptional(isOptional: Boolean): ParameterDescriptorWithType {
    return if (isOptional) this.optional() else this
}
infix fun ParameterDescriptorWithType.default(defaultValue: Any): ParameterDescriptorWithType {
    return this.defaultValue(defaultValue)
}




//response
sealed class DocsFieldType(val type: JsonFieldType)
data object ARRAY : DocsFieldType(JsonFieldType.ARRAY)
data object BOOLEAN : DocsFieldType(JsonFieldType.BOOLEAN)
data object OBJECT : DocsFieldType(JsonFieldType.OBJECT)
data object STRING : DocsFieldType(JsonFieldType.STRING)
data object NUMBER : DocsFieldType(JsonFieldType.NUMBER)
data object NULL : DocsFieldType(JsonFieldType.NULL)

infix fun String.type(docsFieldType: DocsFieldType): FieldDescriptor {
    return fieldWithPath(this).type(docsFieldType.type)
}
infix fun FieldDescriptor.means(description: String): FieldDescriptor {
    return this.description(description)
}
infix fun FieldDescriptor.isOptional(isOptional: Boolean): FieldDescriptor {
    return if (isOptional) this.optional() else this
}


//참고 https://toss.tech/article/kotlin-dsl-restdocs

/*
infix fun String.type(docsFieldType: DocsFieldType): Field {
    val field = createField(this, docsFieldType.type)
    when (docsFieldType) {
        is DATE -> field formattedAs RestDocsUtils.DATE_FORMAT
        is DATETIME -> field formattedAs RestDocsUtils.DATETIME_FORMAT
        else -> {}
    }
    return field
}

infix fun <T : Enum<T>> String.type(enumFieldType: ENUM<T>): Field {
    val field = createField(this, JsonFieldType.STRING, false)
    field.format = EnumFormattingUtils.enumFormat(enumFieldType.enums)
    return field
}

private fun createField(value: String, type: JsonFieldType, optional: Boolean): Field {
    val descriptor = PayloadDocumentation.fieldWithPath(value)
        .type(type)
        .attributes(RestDocsUtils.emptySample(), RestDocsUtils.emptyFormat(), RestDocsUtils.emptyDefaultValue())
        .description("")

    if (optional) descriptor.optional()

    return Field(descriptor)
}*/
