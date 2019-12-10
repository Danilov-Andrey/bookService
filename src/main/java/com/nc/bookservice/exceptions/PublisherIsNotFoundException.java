package com.nc.bookservice.exceptions;

public class PublisherIsNotFoundException extends RuntimeException {
    public PublisherIsNotFoundException(String message){
        super(message);
    }
}
