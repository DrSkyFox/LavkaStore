package org.shop.exceptions;

public class AddressBookOperationException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "Something went wrong!";

    public AddressBookOperationException() {
        super(DEFAULT_MESSAGE);
    }

    public AddressBookOperationException(String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

    public AddressBookOperationException(String message, Throwable cause) {
        super(DEFAULT_MESSAGE + " " + message, cause);
    }
}
