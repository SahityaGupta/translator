package com.twf

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class TranslationRequest(
    val text: String
) {
    constructor() : this("")
}
