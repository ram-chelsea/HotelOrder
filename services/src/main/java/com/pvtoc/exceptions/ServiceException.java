package com.pvtoc.exceptions;

/**
 * Exception being thrown in case of HibernateException having been got during EntityService operations
 */
public class ServiceException extends Exception {
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}