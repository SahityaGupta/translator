package com.twf.customexception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@Suppress("MagicNumber")
@ResponseStatus(value = HttpStatus.NOT_FOUND)
class ResourceNotFoundException : BusinessException {

    constructor(message: String?, throwable: Throwable) : super(
        404,
        HttpStatus.NOT_FOUND,
        "ResourceNotFound",
        message,
        throwable
    )

    constructor(message: String?) : super(404, HttpStatus.NOT_FOUND, "ResourceNotFound", message)

    constructor() : super(404, HttpStatus.NOT_FOUND, "ResourceNotFound")

    constructor(throwable: Throwable) : super(404, HttpStatus.NOT_FOUND, "ResourceNotFound", throwable)

}
