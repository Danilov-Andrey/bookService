package com.nc.bookservice.exceptions.publisher;

public class PublisherNotFoundException extends RuntimeException {
    public PublisherNotFoundException(String message){
        super(message);
    }
}
