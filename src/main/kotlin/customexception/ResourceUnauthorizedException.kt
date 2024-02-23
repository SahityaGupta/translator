package com.twf.customexception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@Suppress("MagicNumber")
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
class ResourceUnauthorizedException : BusinessException {
    constructor(message: String?, throwable: Throwable) : super(
        401,
        HttpStatus.UNAUTHORIZED,
        "ResourceUnauthorized",
        message,
        throwable
    )

    constructor(message: String?) : super(401, HttpStatus.UNAUTHORIZED, "ResourceUnauthorized", message)

    constructor() : super(401, HttpStatus.UNAUTHORIZED, "ResourceUnauthorized")

    constructor(throwable: Throwable) : super(401, HttpStatus.UNAUTHORIZED, "ResourceUnauthorized", throwable)
}

