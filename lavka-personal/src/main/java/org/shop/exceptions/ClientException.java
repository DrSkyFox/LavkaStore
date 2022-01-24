package org.shop.exceptions;

public class ClientException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "An exception has occurred!";

    public ClientException() {
        super(DEFAULT_MESSAGE);
    }

    public ClientException(String message)
    {
        super(DEFAULT_MESSAGE + " " + message);
    }

    public ClientException(String message, Throwable cause) {
        super(DEFAULT_MESSAGE + " " + message, cause);
    }

}
