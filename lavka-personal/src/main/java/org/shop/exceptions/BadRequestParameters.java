package org.shop.exceptions;

public class BadRequestParameters extends RuntimeException{


    public BadRequestParameters() {
    }

    public BadRequestParameters(String message) {
        super(message);
    }

    public BadRequestParameters(String message, Throwable cause) {
        super(message, cause);
    }
}
