package com.twf

import com.fasterxml.jackson.databind.ObjectMapper
import com.twf.customexception.InvalidArgumentException
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class TranslationService {
    companion object {
        const val RAPID_API_KEY = "a26e646f1fmsh8304ec46e836af2p17ff10jsn998000c78c96"
        const val RAPID_API_HOST = "google-translate-v21.p.rapidapi.com"
        const val RAPID_API_URL = "https://google-translate-v21.p.rapidapi.com/translate"
        const val ENGLISH_DICTIONARY = "https://api.dictionaryapi.dev/api/v2/entries/en/"
    }

    fun translateEnglishToFrench(request: TranslationRequest): TranslationResponse {

        if (request.text.isEmpty()) {
            throw InvalidArgumentException("Text to translate cannot be empty.")
        }

        if (containsSpecialCharacter(request.text)) {
            throw InvalidArgumentException("Text to translate cannot contain special characters.")
        }

        var englishDigit = ""

        val listOfWord = request.text.split(" ")

        if (listOfWord.size == 1) {
            if (listOfWord[0].toIntOrNull() != null) {
                englishDigit = convertNumberToWords(listOfWord[0].toInt())
            }
        }

        if (!englishDigit.isNotEmpty()) {
            listOfWord.forEach {

                if (it.toIntOrNull() == null) {

                    val restTemplate = RestTemplate()

                    val uriBuilder = UriComponentsBuilder.fromUriString("${ENGLISH_DICTIONARY}/${it}")

                    val encodedEndpoint = uriBuilder.build().toUriString()

                    val headers = HttpHeaders()

                    headers.contentType = MediaType.APPLICATION_JSON
                    val entity = HttpEntity<Void>(headers)
                    try {
                        val response = restTemplate.exchange(encodedEndpoint, HttpMethod.GET, entity, Any::class.java)
                        if (!response.statusCode.is2xxSuccessful) {
                            throw InvalidArgumentException("Invalid English word $it")
                        }
                    } catch (e: Exception) {
                        throw InvalidArgumentException("Invalid English word $it")
                    }

                }
            }
        }

        val client = OkHttpClient()

        val mediaType = "application/json".toMediaTypeOrNull()
        val body =
            "{\n    \"text_to_translate\": \"${if (englishDigit.isNotEmpty()) englishDigit else request.text}\",\n    \"dest\": \"fr\"\n}".toRequestBody(
                mediaType
            )
        val request = Request.Builder()
            .url(RAPID_API_URL)
            .post(body)
            .addHeader("content-type", "application/json")
            .addHeader("X-RapidAPI-Key", RAPID_API_KEY)
            .addHeader("X-RapidAPI-Host", RAPID_API_HOST)
            .build()

        val response = client.newCall(request).execute()
        val objectMapper = ObjectMapper()
        val jsonNode = objectMapper.readTree(response.body!!.string())

        val translation = jsonNode["translation"].asText()

        return TranslationResponse(translation)
    }

    private fun convertNumberToWords(number: Int): String {
        if (number == 0) return "zero"

        val units = arrayOf("", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        val teens = arrayOf(
            "ten",
            "eleven",
            "twelve",
            "thirteen",
            "fourteen",
            "fifteen",
            "sixteen",
            "seventeen",
            "eighteen",
            "nineteen"
        )
        val tens = arrayOf("", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety")
        val bigNames = arrayOf(
            "",
            "thousand",
            "million",
            "billion",
            "trillion",
            "quadrillion",
            "quintillion",
            "sextillion",
            "septillion",
            "octillion",
            "nonillion",
            "decillion"
        )

        val segments = mutableListOf<String>()
        var remainingNumber = number

        // Divide the number into segments of three digits each
        while (remainingNumber > 0) {
            val segment = remainingNumber % 1000
            segments.add(convertSegmentToWords(segment, units, teens, tens))
            remainingNumber /= 1000
        }

        // Combine the segments with the appropriate big names
        val sb = StringBuilder()
        for (i in segments.indices.reversed()) {
            if (segments[i].isNotEmpty()) {
                if (sb.isNotEmpty()) {
                    sb.append(" ${bigNames[i]} ")
                }
                sb.append(segments[i])
            }
        }

        return sb.toString().trim()
    }

    private fun convertSegmentToWords(
        segment: Int,
        units: Array<String>,
        teens: Array<String>,
        tens: Array<String>
    ): String {
        val sb = StringBuilder()

        val hundred = segment / 100
        if (hundred > 0) {
            sb.append("${units[hundred]} hundred ")
        }

        val lastTwoDigits = segment % 100
        if (lastTwoDigits >= 10 && lastTwoDigits <= 19) {
            sb.append("${teens[lastTwoDigits - 10]}")
        } else {
            val tensDigit = lastTwoDigits / 10
            if (tensDigit > 0) {
                sb.append("${tens[tensDigit]} ")
            }
            val unitsDigit = lastTwoDigits % 10
            if (unitsDigit > 0) {
                sb.append("${units[unitsDigit]}")
            }
        }

        return sb.toString().trim()
    }

    private fun containsSpecialCharacter(input: String): Boolean {
        val regex = Regex("[^A-Za-z0-9 ]")
        return regex.containsMatchIn(input)
    }

}
