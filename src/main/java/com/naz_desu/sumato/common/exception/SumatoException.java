package com.naz_desu.sumato.common.exception;

import org.slf4j.helpers.MessageFormatter;

public class SumatoException extends RuntimeException {

    public SumatoException(String message, Object... messageParams) {
        super(MessageFormatter.arrayFormat(message, messageParams).getMessage());
    }

    public SumatoException(String message, Throwable cause, Object... messageParams) {
        super(MessageFormatter.arrayFormat(message, messageParams).getMessage(), cause);
    }

    public SumatoException(Throwable cause) {
        super(cause);
    }

    public SumatoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... messageParams) {
        super(MessageFormatter.arrayFormat(message, messageParams).getMessage(), cause, enableSuppression, writableStackTrace);
    }

}
