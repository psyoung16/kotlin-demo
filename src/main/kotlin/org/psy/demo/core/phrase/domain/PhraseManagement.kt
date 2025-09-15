package org.psy.demo.core.phrase.domain

import java.util.*

data class PhraseManagement(
    val id: String?,
    val phrase: String,
    val position: String,
    val boldPhrase: String,
    val createdAt: Date?
){


}
