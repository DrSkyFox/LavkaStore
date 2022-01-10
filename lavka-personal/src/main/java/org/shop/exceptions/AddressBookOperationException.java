package org.shop.exceptions;

public class AddressBookOperationException extends RuntimeException{

    public AddressBookOperationException() {
    }

    public AddressBookOperationException(String message) {
        super(message);
    }

    public AddressBookOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
