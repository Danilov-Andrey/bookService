package com.nc.bookservice.exceptions;

public class BookIsNotFoundException extends RuntimeException {
    public BookIsNotFoundException(String message){
        super(message);
    }
}
