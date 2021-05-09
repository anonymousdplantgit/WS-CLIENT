package com.ws.wsclient.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

public class TechnicalErrorException extends ResponseStatusException {
    public TechnicalErrorException() {
        this((String)null, (Throwable)null);
    }

    public TechnicalErrorException(@Nullable String reason) {
        this(reason, (Throwable)null);
    }

    public TechnicalErrorException(@Nullable String reason, @Nullable Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason, cause);
    }
}
