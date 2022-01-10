package org.shop.exceptions;

public class OrderNotExists extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Entity not found";

    public OrderNotExists() {
    }

    public OrderNotExists(String message) {
        super(message);
    }

    public OrderNotExists(String message, Throwable cause) {
        super(message, cause);
    }
}
