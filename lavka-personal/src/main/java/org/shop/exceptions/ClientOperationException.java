package org.shop.exceptions;

public class ClientOperationException extends RuntimeException{
    public ClientOperationException() {
    }

    public ClientOperationException(String message) {
        super(message);
    }
}
