package com.slipenk.ordersapp.exceptions;

public class BadDataException extends RuntimeException {

    public BadDataException(String message) {
        super(message);
    }
}