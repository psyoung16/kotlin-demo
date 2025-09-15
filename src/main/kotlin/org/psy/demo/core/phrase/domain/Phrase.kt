package org.psy.demo.core.phrase.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class Phrase(

        val id: Int?,
        val phrase: String,
        val position: String,
        val boldPhrase: String

)

data class PhraseResponse (

        @JsonProperty("text")
        val text : String,
        @JsonProperty("weight")
        val weight : PhraseWeight,

){
}

enum class PhraseWeight {
    REGULAR, MEDIUM, BOLD, EXTRA_BOLD
}
