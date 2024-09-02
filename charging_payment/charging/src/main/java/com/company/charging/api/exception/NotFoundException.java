package com.company.charging.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author: ASOU SAFARI
 * Date:8/31/24
 * Time:1:59 AM
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Value Not Found")
public class NotFoundException extends RuntimeException {
    public NotFoundException() {}
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}