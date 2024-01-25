package com.lcwd.electronic.store.exceptions;

import lombok.Builder;

@Builder
public class Resourcenotfoundexception extends RuntimeException{
    public Resourcenotfoundexception() {
        super("resource not found exception");
    }

    public Resourcenotfoundexception(String message) {
        super(message);
    }
}
