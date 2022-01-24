package org.shop.exceptions;

public class ClientNotExists extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "Client not found";

    public ClientNotExists() {
        super(DEFAULT_MESSAGE);
    }

    public ClientNotExists(String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

    public ClientNotExists(String message, Throwable cause) {
        super(DEFAULT_MESSAGE + " " + message, cause);
    }
}
