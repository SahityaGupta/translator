package com.twf.customexception

import org.springframework.http.HttpStatus

open class BusinessException : RuntimeException {
    private var status: Int? = null
    private var code: HttpStatus? = null
    private var exceptionName: String? = null
    private var timestamp: Long? = null

    constructor(status: Int, code: HttpStatus, exceptionName: String, message: String?, throwable: Throwable) : super(
        message,
        throwable
    ) {
        this.status = status
        this.exceptionName = exceptionName
        this.code = code
    }

    constructor(status: Int, code: HttpStatus, exceptionName: String, message: String?) : super(message) {
        this.status = status
        this.exceptionName = exceptionName
        this.code = code
    }

    constructor(status: Int, code: HttpStatus, exceptionName: String) : super() {
        this.status = status
        this.exceptionName = exceptionName
        this.code = code
    }

    constructor() : super()

    constructor(status: Int, code: HttpStatus, exceptionName: String, throwable: Throwable) : super(throwable) {
        this.status = status
        this.exceptionName = exceptionName
        this.code = code
    }

    fun getStatus(): Int? {
        return status
    }

    fun getTimestamp(): Long? {
        timestamp = System.currentTimeMillis()
        return timestamp
    }

    fun getCode(): HttpStatus? {
        return code
    }

    fun getExceptionName(): String? {
        return exceptionName
    }
}
