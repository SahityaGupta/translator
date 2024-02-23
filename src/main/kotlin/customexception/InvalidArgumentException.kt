package com.twf.customexception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@Suppress("MagicNumber")
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class InvalidArgumentException : BusinessException {
    constructor(message: String?, throwable: Throwable) : super(
        400,
        HttpStatus.BAD_REQUEST,
        "InvalidArgumentException",
        message,
        throwable
    )

    constructor(message: String?) : super(400, HttpStatus.BAD_REQUEST, "InvalidArgumentException", message)

    constructor() : super(400, HttpStatus.BAD_GATEWAY, "InvalidArgumentException")

    constructor(throwable: Throwable) : super(400, HttpStatus.BAD_REQUEST, "InvalidArgumentException", throwable)
}
