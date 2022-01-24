package org.shop.exceptions;

public class ClientOperationException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "Something went wrong!!";

    public ClientOperationException() {
        super(DEFAULT_MESSAGE);
    }

    public ClientOperationException(String message) {
        super(message);
    }

    public ClientOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
