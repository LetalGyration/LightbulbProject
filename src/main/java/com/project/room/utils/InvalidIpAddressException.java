package com.project.room.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidIpAddressException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidIpAddressException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
