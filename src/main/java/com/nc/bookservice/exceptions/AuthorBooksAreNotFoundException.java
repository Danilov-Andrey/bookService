package com.nc.bookservice.exceptions;

public class AuthorBooksAreNotFoundException extends RuntimeException {
    public AuthorBooksAreNotFoundException(String message){
        super(message);
    }
}
