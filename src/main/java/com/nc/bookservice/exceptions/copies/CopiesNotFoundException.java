package com.nc.bookservice.exceptions.copies;

public class CopiesNotFoundException extends RuntimeException {
    public CopiesNotFoundException(String message){
        super(message);
    }
}
