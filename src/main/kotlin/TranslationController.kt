package com.twf

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("twf-flours")
class TranslationController(
    private val translationService: TranslationService
) {
    @PostMapping("translate", consumes = ["application/json"], produces = ["application/json"])
    fun translate(
        @RequestBody request: TranslationRequest
    ) = translationService.translateEnglishToFrench(request)

}
