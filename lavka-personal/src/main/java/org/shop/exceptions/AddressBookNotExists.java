package org.shop.exceptions;

public class AddressBookNotExists extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "Address not exists";

    public AddressBookNotExists() {
        super(DEFAULT_MESSAGE);
    }

    public AddressBookNotExists(String message) {
        super(message);
    }

    public AddressBookNotExists(String message, Throwable cause) {
        super(message, cause);
    }


}
