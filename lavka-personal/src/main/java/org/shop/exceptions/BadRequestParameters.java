package org.shop.exceptions;

public class BadRequestParameters extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "Bad request!";

    public BadRequestParameters() {
        super(DEFAULT_MESSAGE);
    }

    public BadRequestParameters(String message)
    {
        super(DEFAULT_MESSAGE + " " + message);
    }

    public BadRequestParameters(String message, Throwable cause)
    {
        super(DEFAULT_MESSAGE + " " + message, cause);
    }
}
