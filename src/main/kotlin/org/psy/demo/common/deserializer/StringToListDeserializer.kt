package org.psy.demo.common.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer

class StringToListDeserializer : StdDeserializer<List<String>>(List::class.java) {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): List<String> {
        val value = p.valueAsString
        return value?.takeUnless { it.isBlank() }?.split(",")?.map(String::trim) ?: emptyList()
    }
}
