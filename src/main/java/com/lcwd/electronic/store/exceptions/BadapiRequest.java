package com.lcwd.electronic.store.exceptions;

public class BadapiRequest extends RuntimeException{
    public BadapiRequest(String message) {
        super(message);
    }

    public BadapiRequest() {

        super("Bad Request with this extension");
    }
}
