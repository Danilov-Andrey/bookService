package com.nc.bookservice.exceptions;

public class AuthorIsNotFoundException extends RuntimeException {
    public AuthorIsNotFoundException(String message){
        super(message);
    }
}
