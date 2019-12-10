package com.nc.bookservice.exceptions;

public class CopiesAreNotFoundException extends RuntimeException {
    public CopiesAreNotFoundException(String message){
        super(message);
    }
}
