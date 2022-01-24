package org.shop.exceptions;


public class OrderContentNotExists extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "Element of Order dont exists";

    public OrderContentNotExists() {
        super(DEFAULT_MESSAGE);
    }

    public OrderContentNotExists(String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

    public OrderContentNotExists(String message, Throwable cause) {
        super(DEFAULT_MESSAGE + " " + message, cause);
    }
}
