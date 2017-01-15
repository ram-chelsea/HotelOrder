package com.pvt.exceptions;

/**
 * Exception being thrown in case of SQLException having been got during CRUD operations
 */
public class DaoException extends Exception {
    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

}
