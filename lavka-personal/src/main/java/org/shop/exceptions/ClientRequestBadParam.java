package org.shop.exceptions;

public class ClientRequestBadParam extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "Bad param request!";

    public ClientRequestBadParam() {
        super(DEFAULT_MESSAGE);
    }

    public ClientRequestBadParam(String message) {
        super(DEFAULT_MESSAGE + " "  + message);
    }

    public ClientRequestBadParam(String message, Throwable cause) {
        super(DEFAULT_MESSAGE + " " + message, cause);
    }
}
