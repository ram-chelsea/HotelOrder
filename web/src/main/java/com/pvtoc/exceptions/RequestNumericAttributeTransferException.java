package com.pvtoc.exceptions;


public class RequestNumericAttributeTransferException extends Exception {
    public RequestNumericAttributeTransferException(String message) {
        super(message);
    }

    public RequestNumericAttributeTransferException(String message, Throwable cause) {
        super(message, cause);
    }
}
