package org.shop.exceptions;

public class OrderContentNotExists extends RuntimeException{

    public OrderContentNotExists() {
    }

    public OrderContentNotExists(String message) {
        super(message);
    }

    public OrderContentNotExists(String message, Throwable cause) {
        super(message, cause);
    }
}
