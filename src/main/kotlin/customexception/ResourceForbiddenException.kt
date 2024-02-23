package com.twf.customexception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@Suppress("MagicNumber")
@ResponseStatus(value = HttpStatus.FORBIDDEN)
class ResourceForbiddenException : BusinessException {
    constructor(message: String?, throwable: Throwable) : super(
        403,
        HttpStatus.FORBIDDEN,
        "ResourceForbidden",
        message,
        throwable
    )

    constructor(message: String?) : super(403, HttpStatus.FORBIDDEN, "ResourceForbidden", message)

    constructor() : super(403, HttpStatus.FORBIDDEN, "ResourceForbidden")

    constructor(throwable: Throwable) : super(403, HttpStatus.FORBIDDEN, "ResourceForbidden", throwable)
}
