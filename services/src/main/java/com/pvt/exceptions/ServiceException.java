package com.pvt.exceptions;

/**
 * Exception being thrown in case of SQLException or DaoException having been got during Service operations
 */
public class ServiceException extends Exception {
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}