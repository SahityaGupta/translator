package com.twf.customexception


class ExceptionResponse(
    private val status: Int?,
    private val message: String?,
    private val timestamp: Long?,
    private val error: String?,
    private val stackTrace: String?
) {

    constructor() : this(null, null, null, null, null)

    fun getStatus(): Int? {
        return status
    }

    fun getMessage(): String? {
        return message
    }

    fun getTimestamp(): Long? {
        return timestamp
    }

    fun getError(): String? {
        return error
    }

    fun getStackTrace(): String? {
        return stackTrace
    }
}
