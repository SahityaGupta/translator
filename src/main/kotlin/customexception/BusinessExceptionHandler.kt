package com.twf.customexception

import org.slf4j.Logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.slf4j.LoggerFactory

@ControllerAdvice
class BusinessExceptionHandler : ResponseEntityExceptionHandler() {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(BusinessException::class)
    fun customException(ex: BusinessException): ResponseEntity<ExceptionResponse> {
        val serviceName: String? = getServiceName(ex)
        val errorModel = ExceptionResponse(
            ex.getStatus()!!,
            ex.message,
            ex.getTimestamp(),
            ex.getExceptionName(),
            ex.stackTraceToString()
        )
        return ResponseEntity<ExceptionResponse>(errorModel, ex.getCode()!!)
    }

    private fun getServiceName(ex : BusinessException): String? {
        var service: String? = null
        if(ex.stackTraceToString().contains("userservice")){
            service = "user-service"
        }
        else if(ex.stackTraceToString().contains("chargerservice")){
            service = "charger-service"
        }
        return service
    }

}
